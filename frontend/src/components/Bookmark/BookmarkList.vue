<template>
  <div class="bookmark-container">
      <div class="header">
         <i @click="closeCompo" class="fas fa-angle-double-left"></i>
      </div>
      <div class="bookmark-name"> 즐겨찾기 </div>
      <BookmarkListItem  @goDetail="goDetail" v-for="(bookmark, idx) in bookmarklist" :key="idx" :bookmark="bookmark" />
  </div>
</template>

<script>
import BookmarkListItem from './BookmarkListItem'
var dummyBookmark = [
    {
        regionName: '마포구 서교동',
        score: 88,
        rank: [1, 4, 3, 2]
    },
    {
        regionName: '서대문구 창천동',
        score: 91,
        rank: [1, 5, 5, 3]
    },
    {
        regionName: '은평구 북가좌동',
        score: 81,
        rank: [3, 1, 2, 4]
    },
]

export default {
    name: 'BookmarkList',
    components: {
        BookmarkListItem
    },
    data: function() {
        return {
            bookmarklist: [],
            goDetailValue: false,
        };
    },
    methods: {
        getBookmark: function() {
            this.bookmarklist = dummyBookmark
        },
        closeCompo: function () {
            this.$emit('close-expended')
        },
        goDetail(value) {
            this.goDetailValue = value;
            this.$emit('goDetail', this.goDetailValue)
        }
    },
    created: function() {
        this.getBookmark()
    }
}
</script>

<style scoped lang="scss">
.bookmark-container {
    height: 100%;
    padding: 20px;

    .header {
        position: absolute;
        right: 20px;
        top: 20px;
        i {
            font-size: 24pt;
            color: rgb(148, 148, 148);
        }
        i:hover {
            cursor: pointer;
            color: #808080;
        }
    }
    .bookmark-name {
        margin-top: 50px;
        font-size: 20pt;
    }

    .bookmark-wrapper {
        margin-bottom: 10px;

        background-color: white;
        border-radius: 8px;
        border: 1px solid rgb(228, 228, 228);

        display: flex;
        flex-direction: column;
    }
}

</style>