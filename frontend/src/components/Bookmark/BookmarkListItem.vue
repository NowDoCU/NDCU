<template>
  <div class="bookmark-wrapper">
      <div class="bookmark-region">
          <div class="bookmark-region-name"> {{ bookmark.regionName }} </div>
          <span> 추천지수 {{ bookmark.score }}점 </span>
      </div>
      <div class="rank-like-container">
          <div class="bookmark-region-rank">
            <p> 20-40대 비율 {{ bookmark.rank[0] }}위 </p>
            <p> 아파트 단지 수 {{ bookmark.rank[1] }}위 </p>
            <p> 직장 인구 수 {{ bookmark.rank[2] }}위 </p>
            <p> 소득 수준 {{ bookmark.rank[3] }}위 </p>
          </div>
          <i class="fas fa-star" @click="deleteLike"></i>
      </div>
  </div>
</template>

<script>
export default {
    name: 'BookmarkListItem',
    props: {
        bookmark: Object,
    },
    methods: {
        deleteLike: function() {
            var deletelike = confirm('즐겨찾기에서 삭제하시겠습니까?');
            if (deletelike) {
                console.log('삭제됐습니다.')
            }
        }
    }
}
</script>

<style scoped lang="scss">
.bookmark-wrapper {
    margin: 20px;
    padding: 20px;

    .bookmark-region {
        margin-bottom: 10px;

        .bookmark-region-name {
            font-size: 16pt;
            display: inline;
        }

        span {
            font-size: 13pt;
            color: gray;
        }
    }

    .rank-like-container {
        display: flex;
        justify-content: space-around;

        .bookmark-region-rank {
            display: flex;
            flex-direction: column;
            align-items: center;

            font-size: 13pt;
            margin: 10px;
        }

        i {
            color: rgb(236, 201, 3);

            margin-top: 30px;
        }
    }
}

</style>