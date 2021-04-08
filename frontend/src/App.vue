<template>
   <div id="app">
      <div class="screenProtect">
         <img src="@/assets/image/crying_face.png/" alt="">
         <div class="first-l">화면 크기를 키워주세요.</div>
         <div class="second-l">본 사이트는 데스크탑 환경에 <br> 최적화되어 있습니다.</div>
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
      width: 20vw;
   }
   .first-l {
      margin: 50px 0;
      font-size: 6vw;
      font-weight: 800; 
   }
   .second-l {
      display: inline-block ;
      text-align: center;
      font-size: 4vw;
      font-weight: 600;

   }
}
@media (max-width:1000px) { 
   .screenProtect{
      display: flex;
      z-index: 10000;
   }
 }
</style>
