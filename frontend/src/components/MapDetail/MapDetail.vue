<template lang="">
   <div class="md-container">
      <div class="header">
         <div class="name"><i class="fas fa-store"></i> {{ this.detailData.commercialName }}</div>
         <div class="btnLike">
            <i v-if="like" class="fas fa-star" @click="setLike"></i>
            <i v-else class="far fa-star" @click="setLike"></i>
         </div>
         <i @click="closeCompo" class="fas fa-times btn-close"></i>
      </div>
      <!-- <div class="content-recommend">
         <div class="score">
            추천지수 <span class="number"> {{ score }} </span> 점
         </div>
         <p class="description">* 본 추천지수는 'KNN 알고리즘'을 적용하여 도출된 점수입니다.</p>
      </div> -->
      <div class="content-common">
         <div class="info-basic">
            <span class="title">
               상권코드<span class="value">{{ this.detailData.commercialCode }}</span>
            </span>
            <span class="title">
               상권구분<span class="value">{{ this.detailData.divisionName }}</span>
            </span>
         </div>
         <div class="info-special">
            <div class="box recommend">
               <div class="title">추천지수</div>
               <div class="value">97</div>
            </div>
            <div class="box rent">
               <div class="title">추정 매출</div>
               <div class="value">2000</div>
            </div>
            <div class="box sales">
               <div class="title">평균 임대료</div>
               <div class="value">4000</div>
            </div>
         </div>
      </div>
      <div class="content-detail">
         <div class="section">
            <p class="title">업종별 매출 추이</p>
            <DetailGraph :dataset="type" class="graph" />
         </div>
         <div class="section">
            <p class="title">업종별 어쩌구 추이</p>
            <DetailGraph :dataset="age" class="graph" />
         </div>
         <div class="section">
            <p class="title">어쩌구 저쩌구</p>
            <LineGraph :dataset="ysales" class="graph" />
         </div>
         <div class="section">
            <p class="title">성별 선호도</p>
            <PieChart :dataset="week" class="graph" />
         </div>
      </div>
   </div>
</template>

<script>
import DetailGraph from './DetailGraph';
import PieChart from './PieChart';
import LineGraph from './LineGraph';

var mapdata = {
   name: '마포구 서교동',
   score: '88',
   sales: '1억2844만',
   rent: '2000만',
   type: [22, 30, 11, 27, 4, 3, 2, 1],
   age: [28, 22, 35, 8, 6, 1],
   ysales: [54, 62, 49],
   week: [45, 55],
};

export default {
   name: 'MapDetail',
   props: {
      detailData: Object,
   },
   components: {
      DetailGraph,
      PieChart,
      LineGraph,
   },
   data: function() {
      return {
         regionName: '',
         score: '',
         sales: '',
         rent: '',
         type: {
            name: '업종별 비율',
            category: ['한식음식점', '양식음식점', '일식음식점', '분식전문점', '패스트푸드점', '호프/간이주점', '커피/음료', '베이커리'],
            value: '',
         },
         age: {
            name: '연령대별 비율',
            category: ['10대', '20대', '30대', '40대', '50대', '60대 이상'],
            value: '',
         },
         ysales: {
            name: '연도별 매출',
            category: ['2018년', '2019년', '2020년'],
            value: '',
         },
         week: {
            name: '주중/주말 매출',
            category: ['주중', '주말'],
            value: '',
         },
         like: false,
      };
   },
   methods: {
      getData: function() {
         this.regionName = mapdata.name;
         this.score = mapdata.score;
         this.sales = mapdata.sales;
         this.rent = mapdata.rent;
         this.type.value = mapdata.type;
         this.age.value = mapdata.age;
         this.ysales.value = mapdata.ysales;
         this.week.value = mapdata.week;
      },
      setLike: function() {
         this.like = !this.like;
      },
      closeCompo: function() {
         this.$emit('close-expended');
      },
   },
   created: function() {
      this.getData();
   },
};
</script>
<style scoped lang="scss">
* {
   /* border: 1px dashed green; */
   box-sizing: border-box;
}

.md-container {
   width: 100%;
   height: 100%;
   display: flex;
   flex-direction: column;
   padding: 25px 25px;

   .header {
      /* background-color: red; */
      height: 50px;

      .name {
         display: inline-block;
         font-size: 22pt;
         font-weight: 600;

         i {
            font-size: 14pt;
         }
      }

      .btnLike {
         display: inline-block;
         margin-left: 5px;
         color: rgb(236, 201, 3);
         font-size: 18px;
      }

      .btn-close {
         float: right;

         font-size: 18pt;
         color: rgb(148, 148, 148);
         cursor: pointer;

         &:hover {
            color: #ff6633;
         }
      }
   }

   .content-common {
      display: flex;
      flex-direction: column;
      margin-top: 15px;

      .info-basic {
         width: 100%;
         height: 40px;

         font-size: 10pt;
         color: rgb(63, 63, 63);
         /* text-align: center; */
         padding: 0px 20px;
         line-height: 40px;

         background-color: rgb(230, 230, 230);
         border: 1px solid rgb(203, 203, 203);

         .title {
            margin-right: 10px;
            font-weight: 600;
         }

         .value {
            margin-left: 3px;
            color: #ff6633;
         }
      }

      .info-special {
         margin: 15px 0px;
         display: flex;

         /* 공통 속성 */
         .box {
            $radiusVal: 15px;

            width: 100%;
            height: 100px;
            border-radius: $radiusVal;
            background-color: rgb(255, 244, 226);
            /* box-shadow: 0px 3px 8px rgb(0 0 0 / 46%); */
            margin: 0px 10px;

            .title {
               height: 30px;
               background-color: rgba(29, 29, 29, 0.119);
               border-radius: $radiusVal $radiusVal 0px 0px;

               font-size: 11pt;
               font-weight: 600;
               text-align: center;
               line-height: 30px;
            }

            .value {
               font-size: 26pt;
               font-weight: 600;
               text-align: center;
               line-height: 60px;
            }
         }

         .recommend {
            background-color: #ffcc00;
         }
      }
   }

   .content-detail {
      display: flex;
      flex-direction: column;
      align-items: center;

      width: 100%;
      height: 100%;
      overflow-y: auto;

      .section {
         margin-top: 20px;
         width: 100%;

         p.title {
            font-size: 16pt;
            font-weight: 500;
         }

         .graph {
            height: 300px;
            width: 300px;
            margin: 0 auto;
            margin-top: 20px;
         }
      }
   }
}
</style>
