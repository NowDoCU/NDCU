<template lang="">
   <div class="container">
      <div class="detail-compo floating">
         <map-detail></map-detail>
         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div v-if="optionCompo||bookMarkCompo" class="menu-expand-compo floating">
        <option-input v-if="optionCompo"></option-input>
         <!-- 좌측 사이드 메뉴 위치 -->
         <!-- 내부에 컴포넌트 생성 후 배치 -->
      </div>
      <div class="sidemenu-compo floating">
         <side-menu></side-menu>
      </div>

      <vue-daum-map id="map" :appKey="appKey" :center.sync="center" :level.sync="level" :mapTypeId="mapTypeId" :libraries="libraries" @load="onLoad"> </vue-daum-map>
   </div>
</template>

<script>
import VueDaumMap from 'vue-daum-map';
import MapDetail from '@/components/MapDetail.vue';
import OptionInput from '@/components/OptionInput.vue'
import SideMenu from '@/components/SideMenu.vue';

export default {
   components: { VueDaumMap, MapDetail, OptionInput, SideMenu },
   data: () => ({
      appKey: 'b20bb90eb97f8724820808bd2047982e', //restAPI key
      center: { lat: '', lng: '' },
      level: 3,
      mapTypeId: VueDaumMap.MapTypeId.NORMAL,
      libraries: [],
      map: null,
      optionCompo: true,
      bookMarkCompo: false,
   }),
   created() {
      this.initCenter();
   },
   methods: {
      // 지도가 로드 완료되면 load 이벤트 발생
      onLoad(map) {
         // console.log(map);
         this.map = map;
      },
      initCenter() {
         // 중심위치 세팅
         this.center.lat = 37.5743822;
         this.center.lng = 126.9688505;
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

    width: 445px;
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
