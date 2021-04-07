<template>
  <div class="bookmark-container">
      <div class="header">
         <i @click="closeCompo" class="fas fa-angle-double-left"></i>
      </div>
      <div class="bookmark-name">즐겨찾기</div>
      <div class="bookmarks">
        <BookmarkListItem  @delete-bm="onDeleteBm(idx, bookmark)" @goDetail="goDetail" v-for="(bookmark, idx) in bookmarkList" :key="idx" :bookmark="bookmark" :idx="idx"/>
      </div>
  </div>
</template>

<script>
import BookmarkListItem from './BookmarkListItem'
import { mapState } from 'vuex';
import { getBookmarkList, removeBookmark } from '@/api/bookmark';

export default {
    name: 'BookmarkList',
    components: {
        BookmarkListItem
    },
    data: function() {
        return {
            bookmarkList: [],
            goDetailValue: false,
        };
    },
    computed: {
      ...mapState(['userInfo']),
    },
    methods: {

        /** 북마크 리스트 가져오기 */
        getBookmark: function() {            
            getBookmarkList(
                (res) => {
                    this.bookmarkList = res.data;
                }, 
                (err) => {
                    console.log(err);
                }
            )
        },
        closeCompo: function () {
            this.$emit('close-expended')
        },
        goDetail(value) {
            this.goDetailValue = value;
            this.$emit('goDetail', this.goDetailValue)
        },

        /** 북마크 삭제하기 */
        onDeleteBm: function (idx, bookmark) {
            removeBookmark(
                bookmark.commercial.commercialCode,
                () => {
                    this.bookmarkList.splice(idx, 1);
                },
                (err) => {
                    console.log(err);
                }
            )            
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
    padding: 0 15px;
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
        padding: 50px 0 0 15px;
        font-size: 20pt;
        font-weight: 700;
    }
    .bookmarks {
        margin-top: 35px;
        height: 83%;
        overflow-y: scroll;
    }
}

</style>