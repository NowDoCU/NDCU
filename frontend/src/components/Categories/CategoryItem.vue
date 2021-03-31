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
  data: function () {
    return {
      isSelected: false
    }
  },
  methods: {
    cateSelect: function () {
      this.isSelected = !this.isSelected
      const itemData = {
        isSel: this.isSelected,
        cate: this.category,
        idx: this.idx
      }
      this.$emit('category-select', itemData)
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
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  
  .img-wrapper {
    width: 40px;
    height: 40px;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .cate-text {
    font-size: 6pt;
  }

}
</style>