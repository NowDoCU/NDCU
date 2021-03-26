<template>
  <div>
      <BookmarkListItem  :bookmarklist='bookmarklist' />
  </div>
</template>

<script>
import BookmarkListItem from './BookmarkListItem'
var dummyBookmark = [
    {
        regionName: '종로구 삼청동',
        score: 81,
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
        };
    },
    methods: {
        getBookmark: function() {
            this.bookmarklist = dummyBookmark
        }
    }
}
</script>

<style>

</style>