<template>
  <div class="bookmark-container">
      <div class="header">
         <i @click="closeCompo" class="fas fa-angle-double-left"></i>
      </div>
      <div class="bookmark-name">📌 즐겨찾기</div>
      <div class="bookmarks">
        <BookmarkListItem  @delete-bm="onDeleteBm" @goDetail="goDetail" v-for="(bookmark, idx) in bookmarklist" :key="idx" :bookmark="bookmark" :idx="idx"/>
      </div>
  </div>
</template>

<script>
import BookmarkListItem from './BookmarkListItem'
var dummyBookmark = [
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
    },
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
    },
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
    },
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
    },
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
    },
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
    },
    {
        commercialCode: 1000681,
        commercialName: '시흥대로 84길',
        divisionName: '골목상권',
        dongName: '독산2동'
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
        },
        onDeleteBm: function (idx) {
            this.bookmarklist.splice(idx, 1)
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
    padding: 0 20px;
    display: flex;
    flex-direction: column;
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
        padding: 50px 0 20px 20px;
        font-size: 20pt;
        font-weight: 700;
    }
    .bookmarks {
        height: 83%;
        overflow-y: scroll;
    }
}

</style>