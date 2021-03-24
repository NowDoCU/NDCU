<template>
  <div class="cate-wrapper">
    <div
      v-for="(category, idx) in categories"
      :key="idx"
      class="button-wrapper"
    >
      <categoryItem @category-select="onCateSelect" :category="category" :idx="idx"></categoryItem>
    </div>
  </div>
</template>
<script>
import CategoryItem from './CategoryItem.vue'

export default {
  name: 'Category',
  components: {
    CategoryItem
  },
  data: function () {
    return {
      categories: [
        '한식음식점',
        '중식음식점',
        '일식음식점',
        '양식음식점',
        '분식전문점',
        '패스트푸드점',
        '치킨전문점',
        '호프-간이주점',
        '커피-음료',
        '제과점'
      ]
    }
  },
  methods: {
    onCateSelect: function (category) {
      this.$emit('category-select', category)
    }
  }

}
</script>
<style scoped lang="scss">
.cate-wrapper {
  width: 100%;
  padding: 3px;
  display: flex;
  justify-content: center;
  align-self: center;
  flex-wrap: wrap;
  .button-wrapper {
    width: 65px;
    height: 65px;
    background-color: rgba(204, 203, 203, 0.782);
    display: flex;
    justify-content: center;
    margin: 5px;
    padding: 5px;
    border-radius: 10px;
  }
  .button-wrapper:hover {
    background-color: rgba(181, 181, 181, 0.782);
    cursor: pointer;
  }
}
</style>