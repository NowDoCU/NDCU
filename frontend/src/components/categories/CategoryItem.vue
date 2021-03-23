<template lang="">
  <div @click="cateSelect" class="bt-wrapper">
    <div class="img-wrapper">
      <img :src="imgSrc" alt="">
    </div>
    <span class="cate-text">{{ category }}</span>
  </div>
</template>
<script>
export default {
  name: 'CategoryItem',
  props: {
    category: String,
    idx: Number
  },
  methods: {
    cateSelect: function () {
      this.$emit('category-select', this.category)
    }
  },
  computed: {
    imgSrc: function () {
      return require(`@/assets/image/categories/${this.idx}.png`)
    }
  }
}
</script>
<style scoped lang="scss">
.bt-wrapper {
  text-align: center;
  .img-wrapper {
    width: 50px;
    height: 50px;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .cate-text {
    font-size: 8pt;
  }

}
</style>