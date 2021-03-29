<template lang="">
   <div class="container">
      <div class="detail-compo floating">
         <map-detail></map-detail>

         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div v-show="optionCompo || bookMarkCompo" class="menu-expand-compo floating">
         <option-input v-show="optionCompo" @close-expended="onClickClose" @input-complete="onInputComplete"></option-input>
         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
         <BookmarkList />
      </div>
      <div class="sidemenu-compo floating">
         <button @click="this.setRecommendMarker">TEST</button>
         <side-menu @open-input-form="onClickInputBt"></side-menu>
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

<script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=5cc03bac0d3510a482068b50dd6e3612&libraries=services,clusterer"></script>
<script>
import VueDaumMap from 'vue-daum-map';
import MapDetail from '@/components/MapDetail/MapDetail.vue';
import BookmarkList from '@/components/Bookmark/BookmarkList.vue';
import OptionInput from '@/components/OptionInput.vue';
import SideMenu from '@/components/SideMenu.vue';
import axios from 'axios';

// 동코드 JSON 파일 import
import guCoords from '@/assets/data/gu_coords.json';
import dongCoords from '@/assets/data/dong_coords.json';

export default {
   components: { VueDaumMap, MapDetail, OptionInput, SideMenu, BookmarkList },
   data: () => ({
      appKey: 'b20bb90eb97f8724820808bd2047982e', //restAPI key
      center: { lat: '', lng: '' },
      level: 6,
      mapTypeId: VueDaumMap.MapTypeId.NORMAL,
      libraries: [],
      mapObject: null,

      optionCompo: true,
      bookMarkCompo: false,

      // 지도 위에 표시되는 마커 등.. 의 추가 정보 표시
      markers: [],
      customOverlays: [],
      circles: [],
      polygons: [],

      // 구별 경계 등에 대한 정보
      gu_Boundarys: [],
      gu_Overlays: [],
      dong_Overlays: [],

      // 테스트를 위한 샘플 코드
      sample: [
         {
            name: '교대역_1',
            score: 55,
            x: '201023', //상권영역.csv
            y: '443482',
         },
         {
            name: '천호대로129길',
            score: 81,
            x: '208103',
            y: '450391',
         },
         {
            name: '공덕시장',
            score: 90,
            x: '195838',
            y: '449448',
         },
      ],

      // 동별로 마커를 찍기 위한 json파일 import
      guCoords: guCoords,
      dongCoords: dongCoords,
   }),
   created() {
      this.initCenter();
   },

   methods: {
      // sidemenu의 옵션입력 버튼 눌렀을 때
      onClickInputBt: function() {
         this.optionCompo = true;
      },
      //expended compo 닫기 버튼 눌렀을 때
      onClickClose: function() {
         this.optionCompo = false;
      },
      //추천조건 입력 완료했을 때(상권추천)
      onInputComplete: function() {
         this.optionCompo = false;
         // 추천 결과 요청 추가해야함
      },
      // 지도가 로드 완료되면 load 이벤트 발생
      onLoad(map) {
         console.log('onLoad');
         this.mapObject = map;

         // 시작 되자마자 폴리곤 셋팅
         // var bounds = this.mapObject.getBounds();
         var bounds = {
            ha: 126.72845050681946,
            qa: 37.437210868999315,
            oa: 127.17018320519693,
            pa: 37.7014997773775,
         };
         this.getPolygon('gu', bounds);

         this.setDongMarker('make');
      },

      initCenter() {
         console.log('initCenter');
         // 중심위치 세팅
         this.center.lat = 37.5642135; // 위도
         this.center.lng = 127.0016985; // 경로
         this.level = 7;
      },

      onMapEvent(event) {
         // console.log('onMapEvent : ' + event + ' / level : ' + this.level);
         var bounds = this.mapObject.getBounds();

         if (this.level >= 7) {
            this.setGuMarker('make');
            // this.setDongMarker('del');
         } else if (this.level <= 6) {
            this.setGuMarker('del');
            // this.getPolygon('dong', bounds);
         }
      },

      // =========================================
      //             구/동 별 구획을 표시
      //   추천 받지 않는 상황에서 지도에 경계 구역을 표시
      // =========================================

      // 구별로 마커 표시
      setGuMarker(type) {
         if (type == 'make') {
            for (var idx in this.guCoords) {
               var position = new kakao.maps.LatLng(this.guCoords[idx].lat, this.guCoords[idx].lng);

               var content = `<div class="cell">
                                 ${this.guCoords[idx].sig_kor_nm}
                              </div>`;

               // 커스텀 오버레이를 생성합니다
               var guOverlay = new kakao.maps.CustomOverlay({
                  map: this.mapObject,
                  position: position,
                  content: content,
               });

               // dongOverlay.setMap(this.mapObject);
               this.gu_Overlays.push(guOverlay);
            }
         } else if (type == 'del' && this.gu_Overlays.length != 0) {
            for (const key in this.gu_Overlays) {
               this.gu_Overlays[key].setMap(null);
            }
            this.gu_Overlays = [];
         }
      },

      // 동별로 마커 표시
      setDongMarker(type) {
         if (type == 'make') {
            var clusterer = new kakao.maps.MarkerClusterer({
               map: this.mapObject, // 마커들을 클러스터로 관리하고 표시할 지도 객체
               averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
               minLevel: 3, // 클러스터 할 최소 지도 레벨
               disableClickZoom: true, // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
            });

            this.dongCoords.forEach((item) => {
               var position = new kakao.maps.LatLng(item.lat, item.lng);

               var content = `<div class="cell">
                              ${item.emd_nm}
                           </div>`;

               // 커스텀 오버레이를 생성합니다
               var dongOverlay = new kakao.maps.CustomOverlay({
                  map: this.mapObject,
                  position: position,
                  content: content,
               });

               dongOverlay.setMap(this.mapObject);
               this.dong_Overlays.push(dongOverlay);
            });

            clusterer.addMarkers(this.dong_Overlays);

            kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
               // 현재 지도 레벨에서 1레벨 확대한 레벨
               var level = cluster._map.getLevel() - 1;

               // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
               cluster._map.setLevel(level, { anchor: cluster.getCenter() });
            });
         } else if (type == 'del' && this.dong_Overlays.length != 0) {
            for (const key in this.dong_Overlays) {
               this.dong_Overlays[key].setMap(null);
            }
            this.dong_Overlays = [];
         }
      },

      // 구, 동 등의 경계 정보를 초기화함
      removeBoundarys() {
         for (const key in this.gu_Boundarys) {
            this.gu_Boundarys[key].setMap(null);
         }
         this.gu_Boundarys = [];
      },

      // 구, 동을 구분하는 폴리곤 정보를 요청함
      getPolygon(type, bounds) {
         var key = '13C339D4-B453-3C5E-A6A1-CCA6792A2D6B'; // 공간정보 오픈플랫폼
         var domain = 'http://localhost:8080';
         var crs = 'EPSG:4326'; // 반환되는 좌표(WGS84)
         var geo = `BOX(${bounds.ha},${bounds.qa},${bounds.oa},${bounds.pa})`; //minx, miny, maxx, maxy로 검색 범위 설정
         var filter = 'full_nm:like:서울'; // 11740이 서울시 마지막 행정동코드(강동구)
         var data;

         // 구 정보를 반환해서 폴리곤에 표시
         if (type == 'gu') {
            data = 'LT_C_ADSIGG_INFO'; // 시군구 조회
         } else if (type == 'dong') {
            data = 'LT_C_ADEMD_INFO'; // 읍면동 조회
         }

         axios
            .get(`http://api.vworld.kr/req/data?request=GetFeature&data=${data}&key=${key}&format=json&domain=${domain}&crs=${crs}&geomFilter=${geo}&attrFilter=${filter}&size=100`)
            .then((response) => {
               var local = response.data.response.result.featureCollection.features;

               for (const key in local) {
                  var polygonArr = local[key].geometry.coordinates[0][0];
                  var polygonPath = [];
                  var korName = local[key].properties.sig_kor_nm;

                  // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
                  for (let i = 0; i < polygonArr.length; i++) {
                     polygonPath.push(new kakao.maps.LatLng(polygonArr[i][1], polygonArr[i][0]));
                  }

                  this.setPolygonBoundary(polygonPath, korName);
               }
            })
            .catch((err) => {
               console.log('ERROR : ' + err);
            });
      },

      // 지도에 시, 동 경계 및 이름을 표시
      setPolygonBoundary(polygonPath, korName) {
         var polygon = new kakao.maps.Polygon({
            path: polygonPath, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 2, // 선의 두께입니다
            strokeColor: '#212121', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일입니다
            fillColor: '#fff', // 채우기 색깔입니다
            fillOpacity: 0, // 채우기 불투명도 입니다
         });

         // 지도에 구 정보 다각형을 표시합니다
         polygon.setMap(this.mapObject);
         this.gu_Boundarys.push(polygon);

         // var gu_position = new kakao.maps.LatLng(this.dongCoords[idx].lat, this.dongCoords[idx].lng);
         // var gu_content = `<div class="cell">
         //                      ${korName}
         //                   </div>`;

         // // 커스텀 오버레이를 생성합니다
         // var guOverlay = new kakao.maps.CustomOverlay({
         //    map: this.mapObject,
         //    position: gu_position,
         //    content: gu_content,
         // });

         // guOverlay.setMap(this.mapObject);
         // this.gu_Overlays.push(guOverlay);
      },

      // =========================================
      //               상권 관련 함수들
      //  추천 받은 상권들에 대해, 커스텀 마커 + 폴리곤 반환
      // =========================================

      //추천 상권 마커 표시
      setRecommendMarker() {
         var imageSrc = require('/src/assets/image/categories/map/marker/marker.png'), // 마커이미지의 주소입니다
            imageSize = new kakao.maps.Size(40, 40), // 마커이미지의 크기입니다
            imageOption = { offset: new kakao.maps.Point(19, 40) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

         // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
         var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

         // 기존 마커 정보를 삭제
         this.removeLayers();

         // 개별 마커 변수 생성
         var marker;

         for (var idx in this.sample) {
            var district = this.sample[idx];
            var position = new this.coordsChange(district);

            // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            var content = `<div class="customoverlay">
                  <span class="markerInfo">
                     <p class="name"><i class="fas fa-map-marker-alt"></i> ${district.name} <br><br>
                        <span class="score">${district.score}</span>점
                     </p>
                  </span>
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
            marker = new kakao.maps.Marker({
               map: this.mapObject,
               position: position,
               image: markerImage, // 마커이미지 설정
               // clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
               // title: apt.no,
            });

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(this.mapObject);
            this.markers.push(marker);

            customOverlay.setMap(this.mapObject);
            this.customOverlays.push(customOverlay);
         }
      },

      //오픈 API를 통해 폴리곤 정보를 불러옴
      getPolygonDistrict(position) {
         var key = '13C339D4-B453-3C5E-A6A1-CCA6792A2D6B'; // 공간정보 오픈플랫폼
         var domain = 'http://localhost:8080';
         var crs = 'EPSG:4326'; // 반환되는 좌표(WGS84)
         var geo = `POINT(${position.La} ${position.Ma})`;
         var data = 'LT_C_DGMAINBIZ'; // 상권 폴리곤 출력
         // console.log(position.La, position.Ma);

         axios
            .get(`http://api.vworld.kr/req/data?request=GetFeature&data=${data}&key=${key}&format=json&domain=${domain}&crs=${crs}&geomFilter=${geo}`)
            .then((response) => {
               // console.log(response.data.response);
               if (response.data.response.status == 'OK') {
                  // 상권 영역이 있으면, 폴리곤 생성
                  // 응답 데이터에서 polygon 정보 찾기
                  var polygonArr = response.data.response.result.featureCollection.features[0].geometry.coordinates[0][0];
                  var polygonPath = [];

                  // 해당 데이터를 polygonPath에서 원하는 방식으로 맵핑
                  for (let i = 0; i < polygonArr.length; i++) {
                     polygonPath.push(new kakao.maps.LatLng(polygonArr[i][1], polygonArr[i][0]));
                  }

                  this.setPolygonDistrict(polygonPath);
               } else {
                  // 상권 영역 없으면, 원형
                  this.setCircleDistrict(position);
               }
            })
            .catch((err) => {
               console.log('ERROR : ' + err);
            });
      },

      //상권 영역이 없는 경우, 원형으로 표시
      setCircleDistrict(position) {
         // 지도에 표시할 원을 생성합니다
         var circle = new kakao.maps.Circle({
            center: new kakao.maps.LatLng(position.Ma, position.La), // 원의 중심좌표 입니다
            radius: 100, // 미터 단위의 원의 반지름입니다
            strokeWeight: 3, // 선의 두께입니다
            strokeColor: '#212121', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일 입니다
            fillColor: '#CFE7FF', // 채우기 색깔입니다
            fillOpacity: 0.6, // 채우기 불투명도 입니다
         });

         // 지도에 원을 표시합니다
         circle.setMap(this.mapObject);
         this.circles.push(circle);
      },

      //상권 영역이 존재하는 경우, 폴리곤 생성
      setPolygonDistrict(polygonPath) {
         // 지도에 표시할 다각형을 생성합니다
         var polygon = new kakao.maps.Polygon({
            path: polygonPath, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 3, // 선의 두께입니다
            strokeColor: '#212121', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일입니다
            fillColor: '#A2FF99', // 채우기 색깔입니다
            fillOpacity: 0.6, // 채우기 불투명도 입니다
         });

         // 지도에 다각형을 표시합니다
         polygon.setMap(this.mapObject);
         this.polygons.push(polygon);
      },

      //레이어(마커, 커스텀오버레이, 폴리곤 등) 삭제
      removeLayers() {
         for (var i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
            this.customOverlays[i].setMap(null);
         }

         // 원, 폴리곤 초기화. 갯수가 상이하므로, 마커 갯수(총 표시) 만큼 돌리면서, i값이 사이즈별로 판별
         for (var i = 0; i < this.markers.length; i++) {
            if (i < this.circles.length) this.circles[i].setMap(null);
            if (i < this.polygons.length) this.polygons[i].setMap(null);
         }

         // 배열 초기화
         this.markers = [];
         this.customOverlays = [];
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

      // 폴리곤을 통해 중심 좌표 구하기(centroid 알고리즘)
      centroid(polygonPath) {
         var i, j, len, p1, p2, f, area, x, y;

         area = x = y = 0;
         for (i = 0, len = point.length, j = len - 1; i < len; j = i++) {
            p1 = point[i];
            p2 = point[j];
            f = p1[1] * p2[0] - p2[1] * p1[0];
            x += (p1[0] + p2[0]) * f;
            y += (p1[1] + p2[1]) * f;
            area += f * 3;
         }

         return new kakao.maps.LatLng(x / area, y / area);
      },
   },
};
</script>
<style scoed lang="scss">
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

      width: 100px;
      height: 100%;

      border-radius: 0px 30px 30px 0px;
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

   .detail-compo {
      display: none; // 임시
      top: 2%;
      right: 1%;
      background-color: white;
      box-shadow: 0px 9px 20px 0px #56565629;

      width: 500px;
      height: 96%;

      border-radius: 15px;
   }

   // 메인 지도
   #map {
      width: 100%;
      height: 100%;
      float: right; // 크기가 줄면 좌측부터 붙어서, 우측으로 이동
   }
}
</style>
