<template lang="">
   <div class="md-container">
      <div class="header">
         <i @click="closeCompo" class="fas fa-angle-double-right"></i>
      </div>
      <div class="region-container">
         <span class="name"> {{ regionName }} </span>
         <div class="info">
            <div class="score">
               추천지수 <span class="number"> {{ score }} </span> 점
               <i v-if="like" class="fas fa-star" @click="setLike"></i>
               <i v-else class="far fa-star" @click="setLike"></i>
            </div>
            <p class="description">* 본 추천지수는 'KNN 알고리즘'을 적용하여 도출된 점수입니다.</p>
            <div class="info-boxes">
               <div class="info-box">
                  <p>평균 매출</p>
                  <span> {{ sales }} </span>
               </div>
               <div class="info-box">
                  <p>월 임대료</p>
                  <span> {{ rent }} </span>
               </div>
            </div>
         </div>
         <DetailGraph :dataset="type" class="graph" />
         <DetailGraph :dataset="age" class="graph" />
         <LineGraph :dataset="ysales" class="graph" />
         <PieChart :dataset="week" class="graph" />
      </div>
   </div>
</template>

<script>
import DetailGraph from './DetailGraph';
import PieChart from './PieChart'
import LineGraph from './LineGraph'
import { findAllData } from '@/api/mapdetail.js'

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
   components: {
      DetailGraph,
      PieChart,
      LineGraph
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
      getDataAxios: function() {
         findAllData(
            '1001182',
            (res) => {
               console.log(res);
            },
            (error) => {
               console.log(error);
            }
         );
      },
      setLike: function() {
         this.like = !this.like;
      },
      closeCompo: function () {
         this.$emit('close-expended')
      },
   },
   created: function() {
      this.getData();
      this.getDataAxios();
   },
};
</script>
<style scoped lang="scss">
.md-container {
   width: 100%;
   height: 100%;
   display: flex;

   .header {
      position: absolute;
      left: 20px;
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

   .region-container {
      margin: 30px;
      padding: 20px;
      width: 80%;
      height: 90%;
      overflow-y: auto;

      .name {
         font-size: 20pt;
         margin: 20px;
      }

      .info {
         border: 1px solid grey;
         border-radius: 15px;
         padding: 15px;
         margin-top: 10px;

         .score {
            justify-content: flex;
            font-size: 15pt;
            margin-bottom: 10px;

            .number {
               background-color: black;
               color: greenyellow;
               font-size: 16pt;
               border-radius: 15px;
               padding: 4px 8px 4px 8px;
            }

            i {
               color: rgb(236, 201, 3);
            }
         }

         .description {
            font-size: 8pt;
            color: grey;
         }

         .info-box {
            margin: 10px 25px 0 25px;
            padding: 15px;
            background-color: rgba(255, 251, 31, 0.473);
            border-radius: 15px;
            display: inline-block;

            p {
               font-size: 10pt;
            }
         }
      }

      .graph {
         width: 300px;
         height: 300px;
         margin: 10px;
      }
   }
}
</style>
