<template lang="">
   <div class="container">
      <div class="detail-compo floating">
         <map-detail></map-detail>
         <button @click="this.setRecommendMarker">TEST</button>
         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div class="menu-expand-compo floating">
         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div class="sidemenu-compo floating">
         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <vue-daum-map id="map" :appKey="appKey" :center.sync="center" :level.sync="level" :mapTypeId="mapTypeId" :libraries="libraries" @load="onLoad"> </vue-daum-map>
   </div>
</template>

<script type="text/javascript" src="http://dapi.kakao.com/v2/maps/sdk.js?appkey=5cc03bac0d3510a482068b50dd6e3612"></script>
<script>
import VueDaumMap from 'vue-daum-map';
import MapDetail from '@/components/MapDetail.vue';

export default {
   components: { VueDaumMap, MapDetail },
   data: () => ({
      appKey: 'b20bb90eb97f8724820808bd2047982e', //restAPI key
      center: { lat: '', lng: '' },
      level: 7,
      mapTypeId: VueDaumMap.MapTypeId.NORMAL,
      libraries: [],
      mapObject: null,

      // 마커 정보를 저장
      markers: [],
      customOverlays: [],

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
         console.log('WGS84 변환', coords.toLatLng());
         return coords.toLatLng();
      },

      //추천 상권 마커 표시
      setRecommendMarker() {
         // 기존 마커 정보를 삭제
         this.removeMarkers();

         // 개별 마커 변수 생성
         var marker;

         for (var idx in this.sample) {
            var district = this.sample[idx];
            var position = new this.coordsChange(district);

            // 커스텀 오버레이에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            var content = `<div class="customoverlay">
                                <span class="markerInfo">
                                  <p>${district.name}</p>
                                  <p>${district.score}</p>
                                </span>
                            </div>`;

            // 커스텀 오버레이를 생성합니다
            var customOverlay = new kakao.maps.CustomOverlay({
               map: this.mapObject,
               position: position,
               content: content,
               xAnchor: 0.55,
               yAnchor: 1,
            });

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

      //마커 + 커스텀 오버레이 삭제
      removeMarkers() {
         for (var i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
            this.customOverlays[i].setMap(null);
         }
         this.markers = [];
         this.customOverlays = [];
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

   .detail-compo {
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
