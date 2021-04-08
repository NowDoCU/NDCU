<template lang="">
   <div class="to-container">
      <div class="wrapper">
         <div class="content">
            현재 <span class="opt">{{ this.toastDist }}</span
            >을(를) 보고 있어요 <span style="font-size: 16pt">👀</span>
         </div>
         <div v-show="!isExplore" class="feature">
            <span @click="initRecommend"><i class="fas fa-sync-alt"></i></span>
            <span @click="closeRecommend"><i class="fas fa-times"></i></span>
         </div>
         <div v-show="isExplore" class="feature explore">
            <span @click="closeExplore"><i class="fas fa-times"></i></span>
         </div>
      </div>
   </div>
</template>
<script>
export default {
   props: {
      toastDist: String,
      toastType: Number,
      isExplore: Boolean,
   },
   methods: {
      // 추천 조건 초기화(전체화면으로 전체 결과 보여줌)
      initRecommend: function() {
         this.$emit('emit-initRecommend');
      },
      // 추천 닫기
      closeRecommend: function() {
         this.$emit('emit-closeRecommend');
      },
      closeExplore: function() {
         this.$emit('emit-closeExplore');
      },
   },
};
</script>
<style scoped lang="scss">
.to-container {
   box-shadow: 0px 3px 8px rgb(0 0 0 / 46%);
   padding: 6px 22px;

   .wrapper {
      display: flex;
      justify-content: space-between;
      align-items: center;

      height: 100%;

      .content {
         color: rgb(46, 46, 46);
         font-size: 12pt;
         font-weight: 600;

         .opt {
            position: relative;
            top: 4px;
            padding: 1px;
            display: inline-block;
            /* padding-top: 30px; */
            /* height: 24px; */
            max-width: 130px;
            /* background-color: red; */
            color: rgb(232, 57, 44);
            font-weight: 800;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
         }
      }

      .feature {
         font-size: 14pt;
         color: rgb(128, 128, 128);
         display: flex;
         align-items: center;

         span:nth-child(1) {
            margin-right: 10px;

            &:hover {
               color: #3399ff;
            }
         }

         span:nth-child(2) {
            font-size: 18pt;

            &:hover {
               color: #ff6633;
            }
         }

         &.explore {
            span {
               font-size: 18pt;

               &:hover {
                  color: #ff6633;
               }
            }
         }
      }
   }
}
</style>
