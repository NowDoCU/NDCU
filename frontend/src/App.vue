<template>
   <div id="app">
      <div class="screenProtect">
         <div class="first-l">화면의 크기가 너무 작아요 <img src="@/assets/image/detail/emoji/crying_face.png" alt="" /></div>
         <img class="snapshot" src="@/assets/image/map/snapshot.png" alt="snapshot" />
         <div class="second-l">
            원활한 상권 추천 및 분석을 이용하기 위해 화면 크기를 키워주세요.<br />
            본 서비스는 데스크탑 환경에 최적화 되어 있습니다 :)
         </div>
         <img class="logo" src="@/assets/image/NDCU_logo_eng.png" alt="" />
      </div>
      <!-- 추후 라우터뷰로 변환 -->
      <map-search></map-search>
   </div>
</template>

<script>
import MapSearch from './views/MapSearch.vue';

export default {
   name: 'App',
   components: {
      MapSearch,
   },
};
</script>

<style lang="scss">
@import 'src/css/common.scss';
@import 'src/css/global.scss';

#app {
   background-color: wheat;
   width: 100%;
   height: 100vh;
   -ms-user-select: none;
   -moz-user-select: -moz-none;
   -webkit-user-select: none;
   -khtml-user-select: none;
   user-select: none;

   // 스크롤바
   ::-webkit-scrollbar {
      width: 6px;
   }
   ::-webkit-scrollbar-thumb {
      background-color: rgb(157, 157, 157);
      border-radius: 30px;
   }
   ::-webkit-scrollbar-track {
      background-color: transparent;
      border-radius: 20px;
   }
}

.screenProtect {
   display: none;
   overflow: hidden;
   position: relative;
   top: 0;
   left: 0;
   width: 100vw;
   height: 100vh;
   background-color: #3399ff;
   justify-content: center;
   flex-direction: column;
   align-items: center;
   color: white;

   img {
      display: inline;
      width: 5vw;
   }

   img.snapshot {
      width: 60vw;
      box-shadow: 0px 7px 20px 4px rgb(0 0 0 / 30%);
      margin-bottom: 6vw;
   }

   .first-l {
      margin-bottom: 6vw;
      font-size: 4.5vw;
      font-weight: 600;
   }
   .second-l {
      display: inline-block;
      text-align: center;
      font-size: 3vw;
      font-weight: 500;
      line-height: 4vw;
   }

   img.logo {
      margin-top: 4vw;
      width: 6vw;
   }
}
@media (max-width: 1000px) {
   .screenProtect {
      display: flex;
      z-index: 10000;
   }
}
</style>
