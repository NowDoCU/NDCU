<template lang="">
   <div class="container">
      <transition name="slide-down">
         <toast
            v-show="toastShow"
            :isExplore="isExplore"
            :toastDist="toastDist"
            class="toast-pop-up floating"
            @emit-initRecommend="onInputComplete"
            @emit-closeRecommend="closeRecommend"
            @emit-closeExplore="closeExplore"
         ></toast>
      </transition>
      <transition name="collapse-right">
         <div v-show="detailCompo" class="detail-compo floating">
            <map-detail v-show="detailCompo" :isBookmark="isBookmark" :detailData="detailData" :loadStatus="loadStatus" @close-expended="onClickCloseDetail"></map-detail>
         </div>
      </transition>
      <transition name="collapse">
         <div v-show="optionCompo || bookMarkCompo" class="menu-expand-compo floating">
            <option-input v-show="optionCompo" @close-expended="onClickClose" @input-complete="onInputComplete"></option-input>
            <bookmark-list v-if="bookMarkCompo" @close-expended="onClickClose" @goDetail="goDetail"></bookmark-list>
         </div>
      </transition>
      <div class="sidemenu-compo floating">
         <side-menu @open-input-form="onClickInputBt" @open-bookmark="onClickBookmarkBt" @dialog-change="onDialogChange"></side-menu>
      </div>
      <vue-daum-map
         id="map"
         :appKey="appKey"
         :center.sync="center"
         :level.sync="level"
         :mapTypeId="mapTypeId"
         :libraries="libraries"
         @load="onLoad"
         @tilesloaded="onMapEvent('titlesloaded', $event)"
         @zoom_changed="onMapEvent('zoom_changed', $event)"
      >
      </vue-daum-map>
   </div>
</template>

<script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=b20bb90eb97f8724820808bd2047982e&libraries=services,clusterer"></script>

<script>
import VueDaumMap from 'vue-daum-map';
import MapDetail from '@/components/MapDetail/MapDetail.vue';
import BookmarkList from '@/components/Bookmark/BookmarkList.vue';
import OptionInput from '@/components/OptionInput.vue';
import SideMenu from '@/components/SideMenu.vue';
import Toast from '@/components/Toast.vue';

import { mapState } from 'vuex';
import { findDongData, findAllData, findAllGu } from '@/api/mapDetail.js';
import { getBookmarkList } from '@/api/bookmark';
import { coordsB2H } from '@/api/kakaoAPI.js';
import { getRecommendedCommercials } from '@/api/recommend.js';

import { jsonp } from 'vue-jsonp';

// 동코드 JSON 파일 import
import guCoords from '@/assets/data/gu_coords.json';
import dongCoords from '@/assets/data/dong_coords.json';

export default {
   components: { VueDaumMap, MapDetail, OptionInput, SideMenu, BookmarkList, Toast },
   data: () => ({
      appKey: 'b20bb90eb97f8724820808bd2047982e', //JavaScript key
      center: { lat: '', lng: '' },
      level: 6,
      mapTypeId: VueDaumMap.MapTypeId.NORMAL,
      libraries: [],
      mapObject: null,

      optionCompo: false,
      bookMarkCompo: false,

      // 상권의 상제 정보를 전달하기 위한 변수들
      detailData: new Object(),
      loadStatus: 0, //Detail 컴포넌트의 상태 관리
      isBookmark: false,
      detailCompo: false,

      // 토스트 메뉴를 표시하기 위한
      toastShow: false,
      toastDist: null, // 토스트 메뉴에게 건내줄 상권명

      // [추천 상권]
      recommendResult: null,
      markers: [],
      customOverlays: [],
      circles: [],
      polygons: [],

      // [기본 구획]
      gu_Boundarys: [], // 폴리곤으로 나눠진 경계
      gu_Overlays: [],

      dong_Overlays: [],
      dong_Markers: [],
      dong_Boundarys: [], // 선택한 동의 폴리곤
      clusterer: null,

      dongInnerDistricts: [], // 동을 클릭한 후 나오는 상권들
      dongInnerOverlays: [], // 위의 상권을 기록하는 커스텀 오버레이
      dongInnerMarkers: [], // 위의 상권을 기록하는 커스텀 오버레이
      isExplore: false,

      // 동별로 마커를 찍기 위한 json파일 import
      guCoords: guCoords,
      dongCoords: dongCoords,
      bookmarkList: [],
   }),
   created() {
      console.log('에러잡기', 1);
      this.initCenter();
   },

   watch: {
      level: function() {
         // 조건 만족 시 1회만 생성 -> 성능..
         if (this.mapObject != null && this.recommendResult == null) {
            if (this.level >= 6 && this.gu_Overlays.length == 0) {
               console.log('# 레벨 6 이상 => 동 삭제, 구 생성');
               // 구 생성, 동 삭제
               this.makeGuBoundary();
               this.removeDongLayer('total');
               this.removeDongLayer('polygon');
               this.removeDongLayer('innerDong');

               this.closeExplore();
            } else if (this.level <= 5 && this.dong_Overlays.length == 0) {
               console.log('# 레벨 5 아래 => 구 삭제, 동 생성');
               // 구 삭제, 동 생성
               this.removeGuLayer();
            }
         } else {
            console.log('# 토스트메뉴 On');
            // this.makeRecommendMarkerClick();
         }
      },
   },

   methods: {
      // 모달 생성시 블러 적용
      onDialogChange: function(dialog) {
         if (dialog === true) {
            document.querySelector('.container').classList.add('blur-display');
         } else {
            document.querySelector('.container').classList.remove('blur-display');
         }
      },

      // sidemenu의 옵션입력 버튼 눌렀을 때
      onClickInputBt: function() {
         this.optionCompo = !this.optionCompo;
         this.bookMarkCompo = false;
      },
      onClickBookmarkBt: function() {
         this.bookMarkCompo = !this.bookMarkCompo;
         this.optionCompo = false;
      },
      //expended compo 닫기 버튼 눌렀을 때
      onClickClose: function() {
         this.optionCompo = false;
         this.bookMarkCompo = false;
      },
      onClickCloseDetail: function() {
         this.detailCompo = false;
         this.detailData = new Object();
      },

      //추천조건 입력 완료되어 버튼 클릭시 (상권추천)
      onInputComplete: function(options) {
         console.log(options);

         this.optionCompo = false;
         this.detailCompo = false;
         this.toastShow = false;
         this.isExplore = false;
         this.toastDist = '';

         getRecommendedCommercials(
            options,
            (success) => {
               console.log(success)
            },
            (err) => {
               console.log(err)
            }
         )

         // options 데이터를 추천 API 요청 -> 상권명 / 추천 지수 / 상권영역 내 x,y 좌표 받음
         var result = this.apiRecommend(options);
         this.recommendResult = JSON.stringify(result);
         // console.log(result);

         this.removeDongLayer('total');
         this.removeDongLayer('polygon');
         this.removeDongLayer('innerDong');
         this.removeGuLayer();
         this.removeRcommendLayers();

         this.initCenter();

         // 추천 받은 상권들을 마커로 표시
         this.setRecommendMarker();
      },

      /* ==========================
               토스트 메뉴 관련
         =========================
      */
      // 토스트 메뉴에서 추천 결과 삭제
      closeRecommend() {
         this.toastShow = false;
         this.optionCompo = false;
         this.bookMarkCompo = false;

         this.detailCompo = false;
         this.loadStatus = 0;
         this.detailData = new Object();

         this.initCenter();

         this.removeRcommendLayers();

         this.toastDist = null;
         this.recommendResult = null;
      },

      // 토스트 메뉴에서 탐색 결과 삭제
      closeExplore() {
         this.optionCompo = false;
         this.bookMarkCompo = false;

         this.detailCompo = false;
         this.loadStatus = 0;
         this.detailData = new Object();

         this.toastShow = false;
         this.isExplore = false;
         this.toastDist = null;

         this.removeDongLayer('polygon');
         this.removeDongLayer('innerDong');
         this.removePolygonDistrict();
      },

      goDetail(value) {
         this.detailCompo = true;
         this.bookMarkCompo = false;
      },

      // 지도가 로드 완료되면 load 이벤트 발생
      onLoad(map) {
         console.log('# 맵이 로딩됨 => 바로 구 셋팅');
         this.mapObject = map;

         // 시작 되자마자 구 셋팅
         this.removeGuLayer();
         this.makeGuBoundary();
      },

      initCenter() {
         console.log('initCenter');
         // 중심위치 세팅
         this.center.lat = 37.5642135; // 위도
         this.center.lng = 127.0016985; // 경로
         this.level = 8;
      },

      // 줌 이벤트에 걸면, level값 반영이 즉각으로 안이루어짐
      onMapEvent(event) {
         // console.log('onMapEvent : ', event);
         var bounds = this.mapObject.getBounds();

         // console.log(this.option);

         if (this.recommendResult == null && this.level <= 5) {
            console.log('# 드래그 + 레벨 5 아래 => 범위에 따라 동 랜더링');
            this.removeDongLayer('total');
            this.makeDongMarker(bounds);
         } else if (this.recommendResult != null) {
            console.log('# 상권 추천 재랜더링');
            this.removeRcommendLayers();
            this.setRecommendMarker();
         }
         // console.log(this.dong_Overlays.length);
      },

      // =========================================
      //       구 별로 폴리곤 표시 및 이름 표시
      // =========================================
      //  guCoords -> 커스텀 오버레이 생성
      //           -> 전체 bounds 에서 폴리곤 생성 (+폴리곤 이벤트)

      // 구-1) 구별 폴리곤 이름을 만듦
      makeGuBoundary() {
         console.log('[make] 구 전체 폴리곤, 이름 생성');

         findAllGu(
            (success) => {
               success.data.forEach((item) => {
                  var position = new kakao.maps.LatLng(item.lat, item.lng);

                  var content = `<div class="gu">
                              ${item.sig_kor_nm}
                           </div>`;

                  // 커스텀 오버레이를 생성합니다
                  var guOverlay = new kakao.maps.CustomOverlay({
                     map: this.mapObject,
                     position: position,
                     content: content,
                  });

                  this.gu_Overlays.push(guOverlay);

                  var polygonPath = [];

                  // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
                  for (let i = 0; i < item.coordinates.length; i++) {
                     polygonPath.push(new kakao.maps.LatLng(item.coordinates[i][1], item.coordinates[i][0]));
                  }

                  this.makeGuPolygon(polygonPath, item.sig_kor_nm, position);
               });
            },
            (fail) => {
               console.log('ERR_findAllGu : ', fail);
            }
         );
      },

      // 구-3) API에서 받은 폴리곤 데이터로 실제 구별 폴리곤 생성
      makeGuPolygon(polygonPath, name, center) {
         var polygon = new kakao.maps.Polygon({
            map: this.mapObject, // 마커들을 클러스터로 관리하고 표시할 지도 객체
            path: polygonPath, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 2, // 선의 두께입니다
            strokeColor: 'rgb(14, 122, 255)', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid', // 선의 스타일입니다
            fillColor: '#fff', // 채우기 색깔입니다
            fillOpacity: 0.7, // 채우기 불투명도 입니다
         });

         // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다
         kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
            polygon.setOptions({ fillColor: '#09f' });
         });

         // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
         kakao.maps.event.addListener(polygon, 'mouseout', function() {
            polygon.setOptions({ fillColor: '#fff' });
         });

         kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
            // console.log(name, ' -> ', mouseEvent.latLng);
            polygon.f.setCenter(center);
            polygon.f.setLevel(5, { anchor: center, animate: true });
         });

         // 지도에 구 정보 다각형을 표시합니다
         polygon.setMap(this.mapObject);
         this.gu_Boundarys.push(polygon);
      },

      // 구-0) 구별 이름과 폴리곤 정보를 삭제
      removeGuLayer() {
         console.log('[remove] 구 전체 레이어 삭제');

         for (const key in this.gu_Overlays) {
            this.gu_Overlays[key].setMap(null);
         }

         for (const key in this.gu_Boundarys) {
            this.gu_Boundarys[key].setMap(null);
         }

         this.gu_Overlays = [];
         this.gu_Boundarys = [];
      },

      // =========================================
      //     동 별로 동이름, 이미지 마커, 클러스터 생성
      // =========================================
      //  dongCoords -> 커스텀 오버레이 생성   -> 마커 클러스터
      //             -> 이미지 마커 (+이벤트)

      // 동-1) 동별로 마커 표시
      // 클러스터 -> dongCoords 순환 (마커 생성 및 이벤트)
      makeDongMarker(bounds) {
         console.log('[make] 동 마커, 클러스터 생성');

         // 사용할 클러스터 미리 생성
         var clusterer = new kakao.maps.MarkerClusterer({
            map: this.mapObject, // 마커들을 클러스터로 관리하고 표시할 지도 객체
            averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
            minLevel: 4, // 클러스터 할 최소 지도 레벨
            disableClickZoom: true, // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
            minClusterSize: 4, // 클러스터링 할 최소 마커 수
            texts: ['보통', '밀집', '혼잡'],
            calculator: [6, 12, 20], // 클러스터의 크기 구분 값, 각 사이값마다 설정된 text나 style이 적용된다
            styles: [
               {
                  width: '32px',
                  height: '32px',
                  background: 'rgba(127, 225, 150, .8)',
                  borderRadius: '4px',
                  color: '#000',
                  textAlign: 'center',
                  fontWeight: 'bold',
                  lineHeight: '33px',
                  border: '1px solid #292929',
               },
               {
                  width: '38px',
                  height: '38px',
                  background: 'rgba(255, 238, 52, .8)',
                  borderRadius: '4px',
                  color: '#000',
                  textAlign: 'center',
                  fontWeight: 'bold',
                  lineHeight: '39x',
                  border: '1px solid #292929',
               },
               {
                  width: '45px',
                  height: '45px',
                  background: 'rgba(255, 52, 52, .8)',
                  borderRadius: '4px',
                  color: '#000',
                  textAlign: 'center',
                  fontWeight: 'bold',
                  lineHeight: '46px',
                  border: '1px solid #292929',
               },
            ],
         });

         this.dongCoords.forEach((item) => {
            var imageSrc = require('/src/assets/image/map/marker/dong_marker1.png'), // 마커이미지의 주소입니다
               imageSize = new kakao.maps.Size(35, 35), // 마커이미지의 크기입니다
               imageOption = { offset: new kakao.maps.Point(12, -15) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

            // JSON 파일에서 현재 bounds값을 기준으로 랜더링 (성능 개선)
            if (bounds.qa <= item.lat && item.lat <= bounds.pa && bounds.ha <= item.lng && item.lng <= bounds.oa) {
               var position = new kakao.maps.LatLng(item.lat, item.lng);

               var content = `<div class="dong">
                                    <i class="fas fa-map-pin"></i> ${item.emd_nm}
                                 </div>`;

               // 커스텀 오버레이를 생성합니다
               var dongOverlay = new kakao.maps.CustomOverlay({
                  map: this.mapObject,
                  position: position,
                  content: content,
               });

               var marker = new kakao.maps.Marker({
                  map: this.mapObject,
                  position: position,
                  image: markerImage, // 마커이미지 설정
                  clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
               });

               marker.emd_cd = item.emd_cd;
               marker.position = position;

               dongOverlay.setMap(this.mapObject);
               this.dong_Overlays.push(dongOverlay);

               marker.setMap(this.mapObject);
               this.dong_Markers.push(marker);
            }
         });

         // 이미지 마커와 오버레이를 함께 클러스터링화
         clusterer.addMarkers(this.dong_Overlays);
         clusterer.addMarkers(this.dong_Markers);

         // 클러스터 클릭시 확대
         kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
            var level = cluster._map.getLevel() - 1;
            cluster._map.setLevel(level, { anchor: cluster.getCenter(), animate: true });
         });

         this.clusterer = clusterer; // 삭제를 위한 클러스터 할당

         // 생성된 동 이미지 마커에 클릭 이벤트 생성
         this.makeDongMarkerClick();
      }, //동-1) makeDongMarker END

      // 동-2) 마커 이미지에 클릭 이벤트
      makeDongMarkerClick() {
         this.dong_Markers.forEach((item, i) => {
            kakao.maps.event.addListener(item, 'click', () => {
               this.apiDongPolygon(item.emd_cd, item.position);
            });
         });
      },

      // 동-3) 행정동 코드를 기준으로 선택한 마커 폴리곤 생성 및 중앙 이동
      apiDongPolygon(emd_cd, center) {
         var key = '13C339D4-B453-3C5E-A6A1-CCA6792A2D6B'; // 공간정보 오픈플랫폼
         var domain = 'http://localhost:8080';
         var crs = 'EPSG:4326'; // 반환되는 좌표(WGS84)
         var data = 'LT_C_ADEMD_INFO'; // 읍면동 조회
         var geo = '';
         var filter = `emd_cd:=:${emd_cd}`;

         jsonp(`http://api.vworld.kr/req/data?request=GetFeature&data=${data}&key=${key}&format=json&domain=${domain}&crs=${crs}&attrFilter=${filter}&size=100&geomFilter=${geo}`).then((response) => {
            var local = response.response.result.featureCollection.features;

            for (const key in local) {
               var polygonArr = local[key].geometry.coordinates[0][0];
               var polygonPath = [];
               var korName = local[key].properties.sig_kor_nm;

               // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
               for (let i = 0; i < polygonArr.length; i++) {
                  polygonPath.push(new kakao.maps.LatLng(polygonArr[i][1], polygonArr[i][0]));
               }

               this.removeDongLayer('polygon');
               this.makeDongPolygon(polygonPath);

               this.mapObject.setCenter(center);
               this.mapObject.setLevel(3, { animate: true });

               this.removeDongLayer('innerDong');

               // 메소드 체이싱
               new Promise((resolve) => {
                  resolve(this.convertCoordsBtoH(center));
               })
                  .then((result) => {
                     // console.log(4);
                     // console.log('Promise result(H_CODE) => ', result);
                     return this.apiDongDistrict(result);
                  })
                  .then((result) => {
                     // console.log(5, result);
                     this.dongInnerDistricts = result;
                  })
                  .then(() => {
                     // console.log(6, this.dongInnerDistricts);
                     this.setDongInnerMarker();
                  });

               // var H_code = this.convertCoordsBtoH(center);
            }
         });

         // axios
         //    .get(`http://api.vworld.kr/req/data?request=GetFeature&data=${data}&key=${key}&format=json&domain=${domain}&crs=${crs}&attrFilter=${filter}&size=100&geomFilter=${geo}`)
         //    .then((response) => {
         //       var local = response.data.response.result.featureCollection.features;

         //       for (const key in local) {
         //          var polygonArr = local[key].geometry.coordinates[0][0];
         //          var polygonPath = [];
         //          var korName = local[key].properties.sig_kor_nm;

         //          // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
         //          for (let i = 0; i < polygonArr.length; i++) {
         //             polygonPath.push(new kakao.maps.LatLng(polygonArr[i][1], polygonArr[i][0]));
         //          }

         //          console.log('===========================');

         //          this.removeDongLayer('polygon');
         //          this.makeDongPolygon(polygonPath);

         //          console.log(1);

         //          this.mapObject.setCenter(center);
         //          this.mapObject.setLevel(3, { animate: true });

         //          console.log(2);

         //          this.removeDongLayer('innerDong');

         //          console.log(3);

         //          // 메소드 체이싱
         //          new Promise((resolve) => {
         //             resolve(this.convertCoordsBtoH(center));
         //          })
         //             .then((result) => {
         //                console.log(4);
         //                console.log('Promise result(H_CODE) => ', result);
         //                return this.apiDongDistrict(result);
         //             })
         //             .then((result) => {
         //                console.log(5, result);
         //                this.dongInnerDistricts = result;
         //             })
         //             .then(() => {
         //                console.log(6, this.dongInnerDistricts);
         //                this.setDongInnerMarker();
         //             });

         //          // var H_code = this.convertCoordsBtoH(center);
         //       }
         //    })
         //    .catch((err) => {
         //       console.log('ERROR : ' + err);
         //    });
      },

      // 동-4) API에서 받은 동 폴리곤을 생성
      makeDongPolygon(polygonPath) {
         console.log('[make] 선택한 동의 세부 폴리곤 표시');

         var polygon = new kakao.maps.Polygon({
            path: polygonPath, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: '#000', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'shortdashdot', // 선의 스타일입니다
            fillColor: '#ffcc00', // 채우기 색깔입니다
            fillOpacity: 0.3, // 채우기 불투명도 입니다
         });

         // 지도에 구 정보 다각형을 표시합니다
         polygon.setMap(this.mapObject);
         this.dong_Boundarys.push(polygon);
      },

      convertCoordsBtoH(center) {
         console.log('#### 코드 변환 실행');

         var B_code;
         var H_code;

         return new Promise((resolve, reject) => {
            coordsB2H(
               center,
               (success) => {
                  console.log('# 법정동 코드 -> 행정동 코드로 변환');
                  B_code = success.data.documents[0].code.substring(0, 8);
                  H_code = success.data.documents[1].code.substring(0, 8);
                  resolve(H_code);
               },
               (fail) => {
                  console.log('ERR_DONG : ', fail);
               }
            );
         });
      },

      // 동-5) 선택한 마커를 기준으로, 해당 동에 포함된 상권을 출력해줌
      // makeDongInnerMarker(H_code) {
      //    console.log('# 동을 선택한 뒤, 해당 동에 속한 상권들을 마커로 찍음');

      //    this.dongInnerDistricts = this.apiDongDistrict(H_code);
      //    // this.setDongInnerMarker();
      // },

      // 동-5-1) 동코드를 기준으로 해당 동에 있는 상권들의 결과를 가져옴
      apiDongDistrict(H_code) {
         console.log('# 행정동 코드로 서버에 데이터 받아오기!', H_code);

         return new Promise((resolve, reject) => {
            findDongData(
               H_code,
               (success) => {
                  console.log('findDongData', success.data);
                  resolve(success.data);
               },
               (fail) => {
                  console.log('ERR_findDongData : ', fail);
               }
            );
         });
      },

      // 동-5-2) 상권들의 결과에 마커와 상권명을 찍기
      setDongInnerMarker() {
         var imageSrc = require('/src/assets/image/map/marker/marker.png'), // 마커이미지의 주소입니다
            imageSize = new kakao.maps.Size(40, 40), // 마커이미지의 크기입니다
            imageOption = { offset: new kakao.maps.Point(19, 40) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

         // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
         var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

         this.dongInnerDistricts.commercialList.forEach((district) => {
            var position = new this.coordsChange(district);

            // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            var content = `<div class="dongInner">
                                    <i class="fas fa-store"></i> ${district.commercialName}
                                 </div>`;

            // 커스텀 오버레이를 생성합니다
            var dongInnerOverlay = new kakao.maps.CustomOverlay({
               map: this.mapObject,
               position: position,
               content: content,
            });

            this.removePolygonDistrict();
            this.getPolygonDistrict(position);

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
               map: this.mapObject,
               position: position,
               image: markerImage, // 마커이미지 설정
               clickable: true,
            });

            marker.district = district;
            marker.position = position;

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(this.mapObject);
            this.dongInnerMarkers.push(marker);

            dongInnerOverlay.setMap(this.mapObject);
            this.dongInnerOverlays.push(dongInnerOverlay);
         });

         // 추천 상권 마커에 이벤트 등록 (츄파츕스)
         this.makeDongInnerMarkerClick();
      },

      // 동-5-3)
      makeDongInnerMarkerClick() {
         console.log('# 동 세부 마커별 클릭 이벤트 생성');
         this.dongInnerMarkers.forEach((item) => {
            // kakao.maps.event.removeListener(item, 'click', this.setEventRecommendClick);
            kakao.maps.event.addListener(item, 'click', () => {
               this.setInnerMarkerClick(item);
            });
         });
      },

      setInnerMarkerClick(item) {
         // 서버로 해당 상권 코드 보내서, 상권 상세 정보를 받아옴 (일단 공동으로 하기 위해 상권번호만 넘기는 식)

         this.mapObject.setLevel(4, { animate: true });
         this.mapObject.setCenter(item.position);

         // console.log('=====================================');

         // 검색 결과 조회
         new Promise((resolve) => {
            // 초기 값 셋팅
            this.loadStatus = 0; // 디테일 컴포넌트 로드가 안된 초기 상태로 셋팅
            this.detailData = new Object(); // 디테일 컴포넌트에 새로운 결과값을 주기위해 초기 값 셋팅
            resolve(this.getDistrictDetail(item.district.commercialCode));
         }).then((result) => {
            this.loadStatus = 1;
            this.detailCompo = true;
            this.detailData = result;
            this.setIsBookmark();
            // console.log('#out getDistrictDetail', typeof this.detailData);
         });

         // 토스트 메뉴
         this.toastShow = true;
         this.toastDist = item.district.commercialName;
         this.isExplore = true;
      },

      // 해당 회원이 북마크한 상권인지 아닌 지 확인
      setIsBookmark() {
         if (!this.isLogin) {
            return;
         }

         /** 북마크 리스트 가져오기 */
         getBookmarkList(
            (res) => {
               this.bookmarkList = res.data;
               this.isBookmark = false;
               for (var i = 0; i < this.bookmarkList.length; ++i) {
                  if (this.bookmarkList[i].commercial.commercialCode == this.detailData.commercialCode) {
                     this.isBookmark = true;
                     break;
                  }
               }
            },
            (err) => {
               console.log(err);
            }
         );
      },

      // 동-0) 입력값에 따라 정보를 삭제함
      removeDongLayer(type) {
         if (type == 'total') {
            console.log('[remove] 동 폴리곤 제외 삭제');

            for (const key in this.dong_Overlays) {
               this.dong_Overlays[key].setMap(null);
            }
            for (const key in this.dong_Markers) {
               this.dong_Markers[key].setMap(null);
            }

            // 축소만 한 경우, 클러스터가 삭제되지 않으므로 이경우도 삭제
            if (this.clusterer != null) this.clusterer.clear();

            this.dong_Overlays = [];
            this.dong_Markers = [];
         } else if (type == 'polygon') {
            console.log('[remove] 동 폴리곤 삭제');
            for (const key in this.dong_Boundarys) {
               this.dong_Boundarys[key].setMap(null);
            }
            this.dong_Boundarys = [];
         } else if (type == 'innerDong') {
            console.log('[remove] 동 안의 상권 삭제');
            for (const key in this.dongInnerMarkers) {
               this.dongInnerMarkers[key].setMap(null);
               this.dongInnerOverlays[key].setMap(null);
            }
            this.dongInnerMarkers = [];
            this.dongInnerOverlays = [];
         }
      },

      // =========================================
      //                 추천 상권
      //  추천 받은 상권들에 대해, 커스텀 마커 + 폴리곤 반환
      // =========================================

      // 추천) 추천 결과 받기
      apiRecommend(option) {
         // 테스트를 위한 샘플 코드
         var sample = [
            {
               commercialCode: '1000466',
               divisionCode: 'A',
               divisionName: '골목상권',
               commercialName: '동교로17길',
               x: 192533,
               y: 450801,
               score: 88,
               sigunguCode: '11440',
               dongCode: '11440660',
               estimatedPopulationList: null,
               estimatedSalesList: null,
               storeRentalPrice: null,
            },
            {
               commercialCode: '1000466',
               divisionCode: 'A',
               divisionName: '전통시장',
               commercialName: '공덕시장',
               x: 195838,
               y: 449448,
               score: 90,
               sigunguCode: '11440',
               dongCode: '11440660',
               estimatedPopulationList: null,
               estimatedSalesList: null,
               storeRentalPrice: null,
            },
            {
               commercialCode: '1000466',
               divisionCode: 'A',
               divisionName: '골목상권',
               commercialName: '천호대로129길',
               x: 208103,
               y: 450391,
               score: 81,
               sigunguCode: '11440',
               dongCode: '11440660',
               estimatedPopulationList: null,
               estimatedSalesList: null,
               storeRentalPrice: null,
            },
         ];

         return sample;
      },

      // 추천-1) 추천받은 상권들의 마커 정보 입력
      setRecommendMarker() {
         var imageSrc = require('/src/assets/image/map/marker/tap-marker.png'), // 마커이미지의 주소입니다
            imageSize = new kakao.maps.Size(158, 253), // 마커이미지의 크기입니다
            imageOption = { offset: new kakao.maps.Point(85, 250) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

         // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
         var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

         // console.log('# 추천 마커를 찍습니다. (JAON)', this.recommendResult);

         var recommendObj = JSON.parse(this.recommendResult);
         // console.log('# 추천 마커를 찍습니다. (obj)', recommendObj);

         recommendObj.forEach((district) => {
            var position = new this.coordsChange(district);

            // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            var content = `<div class="customoverlay2">
                  <div class="circle">${district.score}<span>점</span></div>
                  <div class="division">${district.divisionName}</div>
                  <div class="name"><i class="fas fa-map-marker-alt"></i> ${district.commercialName}</div>
               </div>`;

            // 커스텀 오버레이를 생성합니다
            var customOverlay = new kakao.maps.CustomOverlay({
               map: this.mapObject,
               position: position,
               content: content,
               xAnchor: 0.49,
               yAnchor: 0.8,
            });

            // console.log(customOverlay);

            this.getPolygonDistrict(position);

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
               map: this.mapObject,
               position: position,
               image: markerImage, // 마커이미지 설정
               clickable: true,
            });

            marker.district = district;
            marker.position = position;

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(this.mapObject);
            this.markers.push(marker);

            customOverlay.setMap(this.mapObject);
            this.customOverlays.push(customOverlay);
         });

         // 추천 상권 마커에 이벤트 등록 (츄파츕스)
         this.makeRecommendMarkerClick();
      },

      // 추천-2) 추천 마커 이미지에 클릭 이벤트
      makeRecommendMarkerClick() {
         console.log('# 마커별 클릭 이벤트 생성');
         this.markers.forEach((item) => {
            // kakao.maps.event.removeListener(item, 'click', this.setEventRecommendClick);
            kakao.maps.event.addListener(item, 'click', () => {
               this.setEventRecommendClick(item);
            });
         });
      },

      // 추천-2-1) 추천 상권 클릭 이벤트 발생 시, 순서에 따라 이벤트 처리 및 결과 재표시
      setEventRecommendClick(item) {
         // 서버로 해당 상권 코드 보내서, 상권 상세 정보를 받아옴 (일단 공동으로 하기 위해 상권번호만 넘기는 식)
         console.log('setEventRecommendClick');
         this.mapObject.setLevel(4, { animate: true });
         this.mapObject.setCenter(item.position);

         // 검색 결과 조회
         new Promise((resolve) => {
            // 초기 값 셋팅
            this.loadStatus = 0; // 디테일 컴포넌트 로드가 안된 초기 상태로 셋팅
            this.detailData = new Object(); // 디테일 컴포넌트에 새로운 결과값을 주기위해 초기 값 셋팅
            resolve(this.getDistrictDetail(item.district.commercialCode));
         }).then((result) => {
            this.loadStatus = 1;
            this.detailData = result;
            this.detailCompo = true;
            this.setIsBookmark();
            console.log('#out getDistrictDetail', this.detailData);
         });

         // 토스트 메뉴
         this.toastShow = true;
         this.toastDist = item.district.commercialName;
      },

      //서버로 선택한 상권의 상세 정보를 요청함
      getDistrictDetail(commercialCode) {
         return new Promise((resolve, reject) => {
            findAllData(
               commercialCode,
               (success) => {
                  // console.log('#in getDistrictDetail', success.data);
                  resolve(success.data);
               },
               (fail) => {
                  console.log('ERR_findAllData : ', fail);
               }
            );
         });
      },

      // 추천-3) 오픈 API를 통해 폴리곤 정보를 불러옴
      getPolygonDistrict(position) {
         var key = '13C339D4-B453-3C5E-A6A1-CCA6792A2D6B'; // 공간정보 오픈플랫폼
         var domain = 'http://localhost:8080';
         var crs = 'EPSG:4326'; // 반환되는 좌표(WGS84)
         var geo = `POINT(${position.La} ${position.Ma})`;
         var data = 'LT_C_DGMAINBIZ'; // 상권 폴리곤 출력
         // console.log(position.La, position.Ma);

         jsonp(`http://api.vworld.kr/req/data?request=GetFeature&data=${data}&key=${key}&format=json&domain=${domain}&crs=${crs}&geomFilter=${geo}`).then((response) => {
            if (response.response.status == 'OK') {
               // 상권 영역이 있으면, 폴리곤 생성
               // 응답 데이터에서 polygon 정보 찾기
               var polygonArr = response.response.result.featureCollection.features[0].geometry.coordinates[0][0];
               var polygonPath = [];

               // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
               for (let i = 0; i < polygonArr.length; i++) {
                  polygonPath.push(new kakao.maps.LatLng(polygonArr[i][1], polygonArr[i][0]));
               }

               this.makePolygonDistrict(polygonPath);
            } else {
               // 상권 영역 없으면, 원형
               this.makeCircleDistrict(position);
            }
         });

         // axios
         //    .get(`http://api.vworld.kr/req/data?request=GetFeature&data=${data}&key=${key}&format=json&domain=${domain}&crs=${crs}&geomFilter=${geo}`)
         //    .then((response) => {
         //       // console.log(response.data.response);
         //       if (response.data.response.status == 'OK') {
         //          // 상권 영역이 있으면, 폴리곤 생성
         //          // 응답 데이터에서 polygon 정보 찾기
         //          var polygonArr = response.data.response.result.featureCollection.features[0].geometry.coordinates[0][0];
         //          var polygonPath = [];

         //          // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
         //          for (let i = 0; i < polygonArr.length; i++) {
         //             polygonPath.push(new kakao.maps.LatLng(polygonArr[i][1], polygonArr[i][0]));
         //          }

         //          this.makePolygonDistrict(polygonPath);
         //       } else {
         //          // 상권 영역 없으면, 원형
         //          this.makeCircleDistrict(position);
         //       }
         //    })
         //    .catch((err) => {
         //       console.log('ERROR : ' + err);
         //    });
      },

      // 추천-3-1) 상권 영역이 없는 경우, 원형으로 표시
      makeCircleDistrict(position) {
         // 지도에 표시할 원을 생성합니다
         var circle = new kakao.maps.Circle({
            center: new kakao.maps.LatLng(position.Ma, position.La), // 원의 중심좌표 입니다
            radius: 70, // 미터 단위의 원의 반지름입니다
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: '#212121', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일 입니다
            fillColor: '#ff6633', // 채우기 색깔입니다
            fillOpacity: 0.3, // 채우기 불투명도 입니다
         });

         // 지도에 원을 표시합니다
         circle.setMap(this.mapObject);
         this.circles.push(circle);
      },

      // 추천-3-2) 상권 영역이 존재하는 경우, 폴리곤 생성
      makePolygonDistrict(polygonPath) {
         // 지도에 표시할 다각형을 생성합니다
         var polygon = new kakao.maps.Polygon({
            path: polygonPath, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 1, // 선의 두께입니다
            strokeColor: '#212121', // 선의 색깔입니다
            strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일입니다
            fillColor: '#ff6633', // 채우기 색깔입니다
            fillOpacity: 0.3, // 채우기 불투명도 입니다
         });

         // 지도에 다각형을 표시합니다
         polygon.setMap(this.mapObject);
         this.polygons.push(polygon);
      },

      // 추천-0) 레이어(마커, 커스텀오버레이, 폴리곤 등) 삭제
      removeRcommendLayers() {
         console.log('# 추천 마커, 상권, 폴리곤 모두 초기화');
         for (var i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
            this.customOverlays[i].setMap(null);
         }

         this.removePolygonDistrict();

         // 배열 초기화
         this.markers = [];
         this.customOverlays = [];

         this.circles = [];
         this.polygons = [];
      },

      removePolygonDistrict() {
         // 원, 폴리곤 초기화. 갯수가 상이하므로, 마커 갯수(총 표시) 만큼 돌리면서, i값이 사이즈별로 판별
         for (var i = 0; i < this.circles.length; i++) {
            this.circles[i].setMap(null);
         }
         for (var i = 0; i < this.polygons.length; i++) {
            this.polygons[i].setMap(null);
         }
         this.circles = [];
         this.polygons = [];
      },

      // =========================================
      //        메소드 호출에 따른 부가 기능 함수들
      // =========================================

      // 좌표 변환 (상권 데이터 x,y -> 카카오맵)
      coordsChange(district) {
         // 상권 데이터 = EPSG 5181 / 카카오맵 = WGS84 사용
         // 변환 과정 : EPSG -> wcong(kakao.map.Coords) * 2.5 -> toLatLng()

         var coords = new kakao.maps.Coords(district.x * 2.5, district.y * 2.5);
         // console.log('WGS84 변환', coords.toLatLng());
         return coords.toLatLng();
      },
   },
   computed: {
      ...mapState(['isLogin']),
   },
};
</script>
<style scoped lang="scss">
// 로그인, 회원가임 모달 눌렸을 때 배경 블러처리
.blur-display > *:not(.sidemenu-compo) {
   pointer-events: none;
   filter: blur(5px);
   animation: 0.3s ease-out 0s 1 blur;
   @keyframes blur {
      0% {
         filter: blur(0);
      }
      100% {
         filter: blur(5px);
      }
   }
}

.container {
   background-color: white;
   width: 100%;
   height: 100%;

   .floating {
      position: absolute;
      z-index: 10;
   }

   // 좌측에 고정되어 떠다니는 사이드메뉴
   .sidemenu-compo {
      background-color: white;
      box-shadow: -1px 0px 20px #23232363;
      width: 100px;
      height: 100%;

      border-radius: 0px 30px 30px 0px;
   }
   .collapse-enter-active,
   .collapse-leave-active {
      transition: all 0.2s ease;
   }
   .collapse-enter,
   .collapse-leave-to {
      transform: translateX(-500px);
   }
   .menu-expand-compo {
      background-color: white;

      width: 500px;
      height: 100%;
      left: 0px;

      padding-left: 100px;
      z-index: 8;

      border-radius: 0px 30px 30px 0px;
   }

   .toast-pop-up {
      background-color: rgb(255, 255, 255);

      width: 380px;
      height: 40px;

      top: 40px;
      left: 50%;

      transform: translateX(-400px);
      /* transform: translate(-50%, -50%);  원본 위치 */

      border-radius: 30px;
      z-index: 30;
   }

   // detail-compo slide in 기능
   .collapse-right-enter-active,
   .collapse-right-leave-active {
      transition: all 0.2s ease;
   }
   .collapse-right-enter,
   .collapse-right-leave-to {
      transform: translateX(1000px);
   }

   // 토스트 팝업 위에서 떨어지는 기능
   .slide-down-enter-active {
      transition: all 0.7s ease;
   }

   .slide-down-leave-active {
      transition: all 0.1s;
   }

   .slide-down-enter,
   .slide-down-leave-to {
      transform: translateX(-400px) translateY(-200px);
   }

   .detail-compo {
      top: 2%;
      right: 1%;
      background-color: white;
      box-shadow: 0px 9px 20px 0px #56565629;

      width: 500px;
      height: 96%;

      border-radius: 15px;
      overflow: hidden;
   }

   // 메인 지도
   #map {
      width: 100%;
      height: 100%;
      float: right; // 크기가 줄면 좌측부터 붙어서, 우측으로 이동
   }
}
</style>
