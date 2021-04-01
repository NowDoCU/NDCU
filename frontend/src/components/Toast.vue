<template lang="">
   <div class="to-container">
      현재 [ㅇㅇ동]을(를) 검색중이에요
      <button @click="getRecommended">리셋</button>
   </div>
</template>
<script>
export default {
   methods: {
      getRecommended: function() {
         this.$emit('input-complete', null);
      },
   },
};
</script>
<style scoped lang="scss">
.to-container {
   box-shadow: 0px 3px 8px rgb(0 0 0 / 46%);
}
</style>
