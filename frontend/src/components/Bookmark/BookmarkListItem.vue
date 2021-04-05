<template>
  <div class="bookmark-wrapper">
      <div @click="goDetail" class="left-wrapper">
          <span>{{ bookmark.commercial.divisionName }}</span>
      </div>
      <div @click="goDetail" class="middle-wrapper">
          <span class="dong-name">{{ bookmark.commercial.dongName }}</span>
          <span class="commercial-name">{{ bookmark.commercial.commercialName }}</span>
      </div>
      <div class="right-wrapper">
          <i @click="deleteBookmark" class="fas fa-trash-alt"></i>
      </div>
  </div>
</template>

<script>

export default {
    name: 'BookmarkListItem',
    data: function() {
        return {
            goDetailValue: false,
        }
    },
    props: {
        bookmark: Object,
        idx: Number,
    },
    methods: {
        deleteBookmark: function() {
            this.$emit('delete-bm', this.bookmark)
        },
        goDetail: function() {
            this.goDetailValue = true;
            // 정보도 가져가기 구현
            this.$emit('goDetail', this.idx);
        }
    }
}
</script>

<style scoped lang="scss">
.bookmark-wrapper {
    margin: 0 15px 25px 15px;
    height: 100px;
    box-shadow: 0px 4px 6px #23232363;;
    border-radius: 20px;
    // padding: 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-weight: 700;
    .left-wrapper {
        height: 100%;
        width: 30%;
        background-color: rgb(255, 187, 0);
        border-radius: 10px 0 0 10px;
        cursor: pointer;
        span {
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            height: 100%;
            // color: white;
            font-size: 11pt;
        }
    }
    .middle-wrapper {
        // padding: 20px 0;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 80%;
        text-align: center;
        cursor: pointer;
        .dong-name {
            font-size: 11pt;
            color: gray;
        }
        .commercial-name {
            margin-top: 10px;
            font-size: 15pt;
            font-weight: 500;
        }
    }
    .right-wrapper {
        padding: 20px;
        width: 3%;
        i {
            color: gray;
            cursor: pointer;
        }
        i:hover {
            color: rgb(83, 83, 83);
        }
    }
}
</style>