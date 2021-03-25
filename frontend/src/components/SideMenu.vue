<template lang="">
   <div class="sm-container">
      <div class="top-wrapper">
         <div class="logo">
            <img src="@/assets/smc_logo.png" alt="" />
         </div>
         <div class="btn-search">
            <i class="fas fa-search-location"></i>
         </div>
         <div class="btn-bookmark">
            <i class="far fa-bookmark"></i>
         </div>
      </div>
      <div class="bottom-wrapper">
         로그인<br /><br />
         회원가입
      </div>
   </div>
</template>
<script>
export default {};
</script>
<style scoped lang="scss">
.sm-container {
   // background-color: red;
   width: 100%;
   height: 100%;

   display: flex;
   flex-direction: column;
   justify-content: space-between;

   .top-wrapper {
      width: 100px;
      height: 400px;

      margin-top: 30px;

      .logo {
         // background-color: red;
         width: 100px;
         height: 100px;

         display: flex;
         flex-direction: column;
         // 완전 중앙 정렬
         align-items: center;
         justify-content: center;

         margin-bottom: 20px;

         img {
            width: 60%;
         }
      }

      .btn-search,
      .btn-bookmark {
         width: 100px;
         height: 100px;
         background-color: white;

         text-align: center;
         cursor: pointer;

         i {
            color: rgb(89, 89, 89);
            line-height: 100px;
            vertical-align: middle;
            font-size: 30px;
         }

         &:hover {
            background-color: rgb(242, 242, 242);
         }
      }
   }

   .bottom-wrapper {
      bottom: 0;
      text-align: center;
      text-decoration: underline;
      color: gray;
      padding-bottom: 40px;
      font-size: 12px;
   }
}
</style>
