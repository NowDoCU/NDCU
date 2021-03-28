<template lang="">
   <div class="container">
      <div v-show="detailCompo" class="detail-compo floating">
         <map-detail></map-detail>

         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div v-show="optionCompo || bookMarkCompo" class="menu-expand-compo floating">
         <option-input 
            v-show="optionCompo" 
            @close-expended="onClickClose"
            @input-complete="onInputComplete"
         ></option-input>
         <bookmark-list
            v-show="bookMarkCompo"
            @close-expended="onClickClose"
         ></bookmark-list>

         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div class="sidemenu-compo floating">
         <!-- <button @click="this.setRecommendMarker">TEST</button> -->
         <side-menu @open-input-form="onClickInputBt" @open-bookmark="onClickBookmarkBt"></side-menu>
      </div>

      <vue-daum-map id="map" :appKey="appKey" :center.sync="center" :level.sync="level" :mapTypeId="mapTypeId" :libraries="libraries" @load="onLoad"> </vue-daum-map>
   </div>
</template>

<script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=5cc03bac0d3510a482068b50dd6e3612"></script>
<script>
import VueDaumMap from 'vue-daum-map';
import MapDetail from '@/components/MapDetail/MapDetail.vue';
import BookmarkList from '@/components/Bookmark/BookmarkList.vue'
import OptionInput from '@/components/OptionInput.vue';
import SideMenu from '@/components/SideMenu.vue';
import axios from 'axios';

export default {
   components: { VueDaumMap, MapDetail, OptionInput, SideMenu, BookmarkList },
   data: () => ({
      appKey: 'b20bb90eb97f8724820808bd2047982e', //restAPI key
      center: { lat: '', lng: '' },
      level: 6,
      mapTypeId: VueDaumMap.MapTypeId.NORMAL,
      libraries: [],
      mapObject: null,

      detailCompo: false,
      optionCompo: true,
      bookMarkCompo: false,

      // 지도 위에 표시되는 마커 등.. 의 추가 정보 표시
      markers: [],
      customOverlays: [],
      circles: [],
      polygons: [],

      // 테스트를 위한 샘플 코드
      sample: [
         {
            name: '교대역_1',
            score: 55,
            x: '201023',
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
   }),
   created() {
      this.initCenter();
   },
   methods: {
      // sidemenu의 옵션입력 버튼 눌렀을 때
      onClickInputBt: function () {
         this.optionCompo = !this.optionCompo
         this.bookMarkCompo = false
      },
      onClickBookmarkBt: function () {
         this.bookMarkCompo = !this.bookMarkCompo
         this.optionCompo = false
      },
      //expended compo 닫기 버튼 눌렀을 때
      onClickClose: function () {
         this.optionCompo = false
         this.bookMarkCompo = false
      },
      //추천조건 입력 완료했을 때(상권추천) 
      onInputComplete: function () {
         this.optionCompo = false
         // 추천 결과 요청 추가해야함

         // 검색 결과 조회
         this.detailCompo = true
      },
      // 지도가 로드 완료되면 load 이벤트 발생
      onLoad(map) {
         // console.log(map);
         this.mapObject = map;
      },
      initCenter() {
         // 중심위치 세팅
         this.center.lat = 37.567538;
         this.center.lng = 127.004528;
      },

      //좌표 변환
      coordsChange(district) {
         // 상권 데이터 = EPSG 5181 / 카카오맵 = WGS84 사용
         // 변환 과정 : EPSG -> wcong(kakao.map.Coords) * 2.5 -> toLatLng()

         var coords = new kakao.maps.Coords(district.x * 2.5, district.y * 2.5);
         // console.log('WGS84 변환', coords.toLatLng());
         return coords.toLatLng();
      },

      //추천 상권 마커 표시
      setRecommendMarker() {
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
                                  <p class="name">${district.name}</p>
                                  <p class="score">${district.score}</p>
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

            this.getPolygonAPI(position);

            // 마커를 생성합니다
            marker = new kakao.maps.Marker({
               map: this.mapObject,
               position: position,
               // image: markerImage, // 마커이미지 설정
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

      //오픈 API를 통해 폴리곤 정보를 불러옴
      getPolygonAPI(position) {
         var key = '13C339D4-B453-3C5E-A6A1-CCA6792A2D6B'; // 공간정보 오픈플랫폼
         var domain = 'http://localhost:8080';
         // console.log(position.La, position.Ma);

         axios
            .get(`http://api.vworld.kr/req/data?request=GetFeature&data=LT_C_DGMAINBIZ&key=${key}&format=json&domain=${domain}&crs=EPSG:4326&geomFilter=POINT(${position.La} ${position.Ma})`)
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
            strokeColor: '#75B8FA', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'dashed', // 선의 스타일 입니다
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
            strokeColor: '#39DE2A', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일입니다
            fillColor: '#A2FF99', // 채우기 색깔입니다
            fillOpacity: 0.6, // 채우기 불투명도 입니다
         });

         // 지도에 다각형을 표시합니다
         polygon.setMap(this.mapObject);
         this.polygons.push(polygon);
      },
   },
};
</script>
<style scoped lang="scss">
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
      // display: none; // 임시
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
