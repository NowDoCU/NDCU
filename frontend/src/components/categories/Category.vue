<template>
  <div class="cate-wrapper">
    <div
      v-for="(category, idx) in categories"
      :key="idx"
      class="button-wrapper"
      :class="[{ 'unsel' : !category.sel }, { 'sel' : category.sel }]"
    >
      <categoryItem @category-select="onCateSelect" :category="category.name" :idx="idx"></categoryItem>
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
      selectedCate: '',
      categories: [
        { name: '한식음식점', sel: false}, // sel >> 선택된 업종 스타일링 적용 위해
        { name: '중식음식점', sel: false},
        { name: '일식음식점', sel: false},
        { name: '양식음식점', sel: false},
        { name: '분식전문점', sel: false},
        { name: '패스트푸드점', sel: false},
        { name: '치킨전문점', sel: false},
        { name: '호프-간이주점', sel: false},
        { name: '커피-음료', sel: false},
        { name: '제과점', sel: false},
      ]
    }
  },
  methods: {
    onCateSelect: function (category) {
      if (this.selectedCate !== category.cate) {
        if(this.selectedCate === '') {
          this.selectedCate = category.cate
          this.categories[category.idx].sel = true
          this.$emit('category-select', category)
        } else {
          alert('업종은 한 개만 선택할 수 있습니다.')
        } 
      } else {
        this.selectedCate = ''
        this.categories[category.idx].sel = false
        this.$emit('category-select', category)
      }
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
    display: flex;
    justify-content: center;
    margin: 5px;
    padding: 5px;
    border-radius: 10px;
  }
  .unsel {
    background-color: #e7e7e7;
  }
  .unsel:hover {
    background-color: #cccccc;
    cursor: pointer;
  }
  .sel {
    background-color: #cccccc;
  }
  .sel:hover {
    cursor: pointer;
  }
}
</style>