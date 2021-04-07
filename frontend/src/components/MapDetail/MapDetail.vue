<template lang="">
   <div class="md-container">
      <div class="top-wrapper">
         <div class="header">
            <div class="name"><i class="fas fa-store"></i> {{ this.detailData.commercialName }}</div>
            <div class="btnLike">
               <i v-if="isBookmark" class="fas fa-star" @click="bookmark"></i>
               <i v-else class="far fa-star" @click="bookmark"></i>
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
      </div>
      <div class="bottom-wrapper">
         <div class="content-detail">
            <div class="section">
               <p class="title">유동인구 변화추이</p>
               <LineGraph :isLoad="isLoad" :dataset="population" class="graph" />
            </div>
            <div class="section">
               <p class="title">연령대 분포</p>
               <span class="notes">(updated 2020-4분기)</span>
               <BarGraph :isLoad="isLoad" :dataset="age" class="graph" />
            </div>
            <div class="section">
               <p class="title">요일별 유동인구</p>
               <span class="notes">(updated 2020-4분기)</span>
               <BarGraph :isLoad="isLoad" :dataset="weekPop" class="graph" />
            </div>
            <div class="section">
               <p class="title">요일별 유동인구</p>
               <PieChart :dataset="week" class="graph" />
            </div>
            <div class="section">
               <p class="title">어쩌구 저쩌구</p>
               <!-- <LineGraph :dataset="type" class="graph" /> -->
            </div>
         </div>
      </div>
   </div>
</template>

<script>
import BarGraph from './BarGraph';
import PieChart from './PieChart';
import LineGraph from './LineGraph';
import { createBookmark, removeBookmark } from '@/api/bookmark';
import { mapState } from 'vuex';

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
      isBookmark: Boolean,
      loadStatus: Number,
   },
   components: {
      BarGraph,
      PieChart,
      LineGraph,
   },
   data: function() {
      return {
         regionName: '',
         score: '',
         sales: '',
         rent: '',

         isLoad: false,

         // 하단 그래프에 내리는 데이터셋
         population: {}, // 유동인구 변화 추이
         age: {}, // 연령대 분포
         weekPop: {}, // 요일별 유동인구
         type: {},
         week: {},
      };
   },
   watch: {
      loadStatus: function() {
         if (this.loadStatus == 0) {
            // 초기화
            console.log('- 데이터베이스와 로드값을 초기화합니다');
            this.isLoad = false; // 로드 상태 0으로 초기화
            this.initDataset(); // 데이터셋 초기화
         }
      },
      detailData: function() {
         if (this.detailData.commercialName !== undefined) {
            // 전달 받은 값 있을 경우
            this.convertData2Graph();
         }
      },
   },
   computed: {
      ...mapState(['isLogin']),
   },
   created() {
      this.initDataset();
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

      initDataset() {
         this.population = {
            name: '유동 인구 수',
            category: ['19-1분기', '19-2분기', '19-3분기', '19-4분기', '20-1분기', '20-2분기', '20-3분기', '20-4분기'],
            value: [0, 0, 0, 0, 0, 0, 0, 0],
         };
         this.age = {
            name: '유동 인구 수',
            category: ['10대', '20대', '30대', '40대', '50대', '60대 이상'],
            value: [0, 0, 0, 0, 0, 0],
         };
         this.weekPop = {
            name: '유동 인구 수',
            category: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
            value: [0, 0, 0, 0, 0, 0, 0],
         };
         this.type = {
            name: '업종별 비율',
            category: ['한식음식점', '양식음식점', '일식음식점', '분식전문점', '패스트푸드점', '호프/간이주점', '커피/음료', '베이커리'],
            value: [22, 30, 11, 27, 4, 3, 2, 1],
         };
         this.week = {
            name: '주중/주말 매출',
            category: ['주중', '주말'],
            value: [45, 55],
         };
      },

      // detailData를 하단 그래프에 전달하도록 값을 변환함
      convertData2Graph() {
         this.makePopulation(); // 유동인구 변화추이
         this.makeAge(); // 연령대 분포
         this.makeWeekPop();

         // 데이터 로드 완료시 true 변경
         this.isLoad = true;
      },

      // 유동인구 변화 추이 그래프 생성
      makePopulation() {
         var result = [];
         var target = this.detailData.estimatedPopulationList;

         if (target && target.length > 0) {
            for (let idx = 0; idx < this.population.category.length; idx++) {
               result.push(target[idx].allPopulation);
            }
         }
         this.population.value = result;
      },

      // 연령대 분포
      makeAge() {
         var target = this.detailData.estimatedPopulationList[0];
         var result = [target.age10, target.age20, target.age30, target.age40, target.age50, target.age60];

         this.age.value = result;
      },

      // 연령대 분포
      makeWeekPop() {
         var target = this.detailData.estimatedPopulationList[0];
         var result = [target.monPopulation, target.tuePopulation, target.wedPopulation, target.thuPopulation, target.friPopulation, target.satPopulation, target.sunPopulation];

         this.weekPop.value = result;
      },

      bookmark: function() {
         // 로그인되어 있는지 확인
         if (!this.isLogin) {
            alert('로그인이 필요합니다.');
            return;
         }

         // 북마크가 안되어 있는 경우 북마크 생성
         if (!this.isBookmark) {
            let commercial = { commercialCode: this.detailData.commercialCode };
            createBookmark(
               commercial,
               () => {
                  alert('북마크 추가');
               },
               (err) => {
                  console.log(err);
               }
            );
         } else {
            // 북마크가 되어 있는 경우 북마크 삭제
            removeBookmark(
               this.detailData.commercialCode,
               () => {},
               (err) => {
                  console.log(err);
               }
            );
         }

         this.isBookmark = !this.isBookmark;
      },

      closeCompo: function() {
         this.isLoad = false;
         this.initDataset();

         this.$emit('close-expended');
      },
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

   .top-wrapper {
      height: 400px;
      box-shadow: 9px 9px 20px #56565629;
      border-radius: 0px 0px 20px 20px;
      padding: 25px;

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
   }
   .bottom-wrapper {
      width: 100%;
      height: 100%;
      overflow-y: auto;
      padding: 25px;

      .content-detail {
         display: flex;
         flex-direction: column;
         align-items: center;

         .section {
            margin-top: 40px;
            width: 100%;

            p.title {
               /* display: inline-block; */
               font-size: 16pt;
               font-weight: 500;
            }

            span.notes {
               /* float: right; */
               font-size: 9pt;
               color: gray;
            }

            .graph {
               height: 380px;
               width: 380px;
               margin: 0 auto;
               margin-top: 25px;
            }
         }
      }
   }
}
</style>
