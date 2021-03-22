<template>
   <div id="app">
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

#app {
   background-color: wheat;
   width: 100%;
   height: 100vh;
}
</style>
