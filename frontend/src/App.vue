<template>
   <div id="app">
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
   user-select:none;
}
</style>
