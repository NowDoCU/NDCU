<template lang="">
   <div class="to-container">
      <div class="wrapper">
         <div class="content">
            현재 <span class="opt">{{ this.toastDist }}</span
            >을(를) 검색중이에요 <span style="font-size: 18pt">🐶</span>
         </div>
         <div class="feature">
            <span @click="initRecommend"><i class="fas fa-sync-alt"></i></span>
            <span @click="closeRecommend"><i class="fas fa-times"></i></span>
         </div>
      </div>
   </div>
</template>
<script>
export default {
   props: {
      toastDist: String,
   },
   data: () => ({
      option: '사당동',
   }),
   methods: {
      // 추천 조건 초기화(전체화면으로 전체 결과 보여줌)
      initRecommend: function() {
         this.$emit('emit-initRecommend');
      },
      // 추천 닫기
      closeRecommend: function() {
         this.$emit('emit-closeRecommend');
      },
   },
};
</script>
<style scoped lang="scss">
.to-container {
   box-shadow: 0px 3px 8px rgb(0 0 0 / 46%);
   padding: 10px 30px;

   .wrapper {
      display: flex;
      justify-content: space-between;
      align-items: center;

      height: 100%;

      .content {
         color: rgb(46, 46, 46);
         font-size: 14pt;
         font-weight: 600;

         .opt {
            color: rgb(232, 57, 44);
            font-weight: 800;
         }
      }

      .feature {
         font-size: 14pt;
         color: rgb(128, 128, 128);
         display: flex;
         align-items: center;

         span:nth-child(1) {
            margin-right: 20px;

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
      }
   }
}
</style>
