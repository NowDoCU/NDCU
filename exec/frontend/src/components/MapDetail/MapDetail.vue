<template lang="">
   <div class="md-container"> <div class="top-wrapper"> <div class="header"> <div class="name"><i class="fas fa-store"></i> {{ commercialName }}</div> <div class="btnLike"> <i v-if="isBookmark"
   class="fas fa-star" @click="bookmark"></i> <i v-else class="far fa-star" @click="bookmark"></i> </div> <i @click="closeCompo" class="fas fa-times btn-close"></i> </div> <div class="header__info">
   <span class="title"> 상권코드<span class="value">{{ commercialCode }}</span> </span> <span class="title">| </span> <span class="title"> 상권구분<span class="value">{{ divisionName }}</span> </span>
   </div> <!-- 기본 정보 섹션 --> <div class="basic-info-section"> <p class="info-line"> <i class="fas fa-calendar-week"></i> 이 상권은 <span class="value">{{ busyWeek }}</span >에 가장 사람이 붐벼요
   </p> <p class="info-line"> <i class="far fa-clock"></i> 이 상권은 <span class="value">{{ busyTime }}</span >에 유동 인구가 많아요 </p> <p class="info-line"> <i class="fas fa-venus-mars"></i> 이
   상권은 <span class="value">{{ busyGender }}</span >분들이 더 자주 찾아요 </p> </div> </div> <div class="bottom-wrapper"> <div class="content-detail"> <div class="section store"> <p
   class="title">상가 임대료 시세</p> <span class="notes">10평 기준(33m²)</span> <div v-show="isRentalNull" class="nullInfo">상권 임대료 정보가 없습니다 :-(</div> <div :class="{ nullRental:
   isRentalNull }" class="wrapper"> <div class="left-box"> <p class="second">2층</p> <p class="first">1층</p> <p class="under">지하</p> </div> <div class="mid-box"> <p class="label">활성화 지역</p> <p
   class="second">{{ rentalPrice.activate.second | currency }}원</p> <p class="first">{{ rentalPrice.activate.first | currency }}원</p> <p class="under">{{
      rentalPrice.activate.under | currency
   }}원</p> </div> <div class="right-box"> <p class="label">비활성화 지역</p> <p class="second">{{ rentalPrice.deactivate.second | currency }}원</p> <p class="first">{{
      rentalPrice.deactivate.first | currency
   }}원</p> <p class="under">{{ rentalPrice.deactivate.under | currency }}원</p> </div> <img src="@/assets/image/detail/house_fee.png" alt="" /> <p class="notes-footer"> <span>*</span> 임대료는 10평을
   기준으로 계산된 금액입니다. <br /> <span>**</span> 활성화지역은 대로변에서 100~200m이내에서 매출액,주요상권등 다양한 변수들로 복합적으로 계산해서 시스템에서 자동산출되어 구분되는 영역 </p> </div>
   </div> <div class="section"> <p class="title">집객시설 분포</p> <table class="table facilities"> <tbody> <tr> <td> <img class="emoji" src="@/assets/image/detail/emoji/graduation-cap_1f393.png" />
   <p>교육시설</p> </td> <td> <img class="emoji" src="@/assets/image/detail/emoji/squinting-face-with-tongue_1f61d.png" /> <p>문화시설</p> </td> <td> <img class="emoji"
   src="@/assets/image/detail/emoji/house-with-garden_1f3e1.png" /> <p>숙박시설</p> </td> <td> <img class="emoji" src="@/assets/image/detail/emoji/metro_1f687.png" /> <p>지하철</p> </td> <td> <img
   class="emoji" src="@/assets/image/detail/emoji/bus_1f68c.png" /> <p>버스</p> </td> </tr> <tr> <td>{{ facilities.school }}</td> <td>{{ facilities.entertainment }}</td> <td>{{
      facilities.accommodation
   }}</td> <td>{{ facilities.rail }}</td> <td>{{ facilities.bus }}</td> </tr> </tbody> </table> <p class="notes-footer"> <span>*</span> 교육시설 = 상권 내 유치원/초/중/고/대학교 <br /> <span>**</span>
   문화시설 = 상권 내 극장, 백화점<br /> <span>***</span> 지하철 = 상권 내 극장, 지하철역, 철도역<br /> <span>****</span> 버스 = 상권 내 버스 정거장, 버스터미널<br /> </p> </div> <div class="section">
   <p class="title">📊 유동인구 변화추이</p> <LineGraph :isLoad="isLoad" :dataset="population" class="graph" /> </div> <div class="section"> <p class="title">📊 연령대 분포</p> <span
   class="notes">(updated 2020-4분기)</span> <BarGraph :isLoad="isLoad" :dataset="age" class="graph" /> </div> <div class="section"> <p class="title">📊 업종별 평균 매출액</p> <span
   class="notes">(updated 2020-4분기)</span> <BarGraph :isLoad="isLoad" :maxSales="maxSales" :dataset="storeSales" class="graph" /> <p class="notes-footer"><span>*</span> 매출액은 카드사 데이터로
   도출한 매출추정액입니다. <br /></p> </div> <div class="section"> <p class="title">🏘 점포수</p> <table class="table storeCnt"> <tbody> <tr> <td class="cate">한식</td> <td class="cate">중식</td> <td
   class="cate">일식</td> <td class="cate">양식</td> <td class="cate">분식</td> </tr> <tr> <td>{{ storeCnt.CS100001 }}</td> <td>{{ storeCnt.CS100002 }}</td> <td>{{ storeCnt.CS100003 }}</td> <td>{{
      storeCnt.CS100004
   }}</td> <td>{{ storeCnt.CS100008 }}</td> </tr> <tr> <td class="cate">패스트푸드</td> <td class="cate">치킨</td> <td class="cate">호프, 주점</td> <td class="cate">커피, 음료</td> <td
   class="cate">제과점</td> </tr> <tr> <td>{{ storeCnt.CS100006 }}</td> <td>{{ storeCnt.CS100007 }}</td> <td>{{ storeCnt.CS100009 }}</td> <td>{{ storeCnt.CS100010 }}</td> <td>{{
      storeCnt.CS100005
   }}</td> </tr> </tbody> </table> </div> </div> </div> </div>
</template>

<script>
import BarGraph from './BarGraph';
import LineGraph from './LineGraph';
import { createBookmark, removeBookmark } from '@/api/bookmark';
import { mapState } from 'vuex';

export default {
   name: 'MapDetail',
   props: {
      detailData: Object,
      isBookmark: Boolean,
      loadStatus: Number,
   },
   components: {
      BarGraph,
      LineGraph,
   },
   data: function () {
      return {
         commercialName: '',
         commercialCode: '',
         divisionName: '',

         busyWeek: '-',
         busyTime: '-',
         busyGender: '-',

         facilities: {
            school: 0,
            entertainment: 0,
            accommodation: 0,
            rail: 0,
            bus: 0,
         },

         storeCnt: {
            CS100001: 0,
            CS100002: 0,
            CS100003: 0,
            CS100004: 0,
            CS100005: 0, // 제과점
            CS100006: 0, // 패스트푸드
            CS100007: 0, // 치킨
            CS100008: 0, // 분식
            CS100009: 0, // 호프 주점
            CS100010: 0,
         },

         isRentalNull: true,
         rentalPrice: {
            activate: {
               second: '-',
               first: '-',
               under: '-',
            },
            deactivate: {
               second: '-',
               first: '-',
               under: '-',
            },
         },

         isLoad: false,

         // 하단 그래프에 내리는 데이터셋
         population: {}, // 유동인구 변화 추이
         age: {}, // 연령대 분포
         storeSales: {}, // 업종별 매출액
         type: {},
         week: {},

         maxAge: '',
         maxSales: 0,
      };
   },
   watch: {
      loadStatus: function () {
         if (this.loadStatus == 0) {
            // 초기화
            // console.log('- 데이터베이스와 로드값을 초기화합니다');
            this.isLoad = false; // 로드 상태 0으로 초기화
            this.initDataset(); // 데이터셋 초기화
         }
      },
      detailData: function () {
         if (this.detailData.commercialName !== undefined) {
            // 전달 받은 값 있을 경우
            this.dataInsert();
            this.convertData2Graph();
            this.insertRentData();
            this.insertFacilitiesData();
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
         this.storeSales = {
            name: '평균 매출액(원)',
            category: ['한식', '중식', '일식', '양식', '분식', '패스트푸드', '치킨', '호프', '커피', '제과'],
            value: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
         };
         this.type = {
            name: '업종별 비율',
            category: ['한식음식점', '양식음식점', '일식음식점', '분식전문점', '패스트푸드점', '호프/간이주점', '커피/음료', '베이커리'],
            value: [22, 30, 11, 27, 4, 3, 2, 1],
         };
      },

      dataInsert() {
         this.commercialName = this.detailData.commercialName;
         this.commercialCode = this.detailData.commercialCode;
         this.divisionName = this.detailData.divisionName;

         var MAX_IDX = 99;
         var MAX = 0;

         // 가장 붐비는 요일 찾기
         var week = ['monPopulation', 'tuePopulation', 'wedPopulation', 'thuPopulation', 'firPopulation', 'satPopulation', 'sunPopulation'];
         var result = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'];
         if (this.detailData.estimatedPopulationList === undefined) {
            return;
         }

         for (const idx in week) {
            var now = eval(`this.detailData.estimatedPopulationList[0].${week[idx]}`);

            if (MAX <= now) {
               MAX = now;
               MAX_IDX = idx;
            }
         }

         if (MAX_IDX !== 99) {
            this.busyWeek = result[MAX_IDX].concat('(', convertPop(MAX), '만명)');
         }

         // 가장 붐비는 시간 찾기
         var time = ['time1', 'time2', 'time3', 'time4', 'time5', 'time6'];
         var resultTime = ['심야시간', '오전시간', '점심시간', '오후시간', '저녁시간', '밤시간'];
         MAX_IDX = 99;
         MAX = 0;

         for (const idx in time) {
            var now2 = eval(`this.detailData.estimatedPopulationList[0].${time[idx]}`);

            if (MAX <= now2) {
               MAX = now2;
               MAX_IDX = idx;
            }
         }

         if (MAX_IDX !== 99) {
            this.busyTime = resultTime[MAX_IDX];
         }

         //성별
         var male = this.detailData.estimatedPopulationList[0].malePopulation;
         var female = this.detailData.estimatedPopulationList[0].femalePopulation;

         if (male >= female) this.busyGender = '남성'.concat('(', convertPop(male), '만명)');
         else this.busyGender = '여성'.concat('(', convertPop(female), '만명)');

         // 값이 있는 경우에만 점포수 객체에 할당
         for (const idx in this.detailData.estimatedSalesList) {
            var code = this.detailData.estimatedSalesList[idx].industryCode;
            eval(`this.storeCnt.${code} = this.detailData.estimatedSalesList[${idx}].storesCount`);
         }

         // 000만명으로 변환
         function convertPop(target) {
            return target.toString().substr(0, target.toString().length - 4);
         }
      },

      insertFacilitiesData() {
         if (this.facilities == null) {
            return;
         }

         this.facilities.school = this.detailData.facilities.school;
         this.facilities.entertainment = this.detailData.facilities.entertainment;
         this.facilities.str = this.detailData.facilities.accommodation;
         this.facilities.rail = this.detailData.facilities.rail;
         this.facilities.bus = this.detailData.facilities.bus;
      },

      insertRentData() {
         this.isRentalNull = true;
         if (this.detailData.storeRentalPrice == null) {
            return;
         }

         var area = 33;

         // 임대료 정보 매핑
         this.isRentalNull = false;
         this.rentalPrice.activate.second = this.detailData.storeRentalPrice.activateSecondRent * area;
         this.rentalPrice.activate.first = this.detailData.storeRentalPrice.activateFirstRent * area;
         this.rentalPrice.activate.under = this.detailData.storeRentalPrice.activateUnderRent * area;
         this.rentalPrice.deactivate.second = this.detailData.storeRentalPrice.deactivateSecondRent * area;
         this.rentalPrice.deactivate.first = this.detailData.storeRentalPrice.deactivateFirstRent * area;
         this.rentalPrice.deactivate.under = this.detailData.storeRentalPrice.deactivateUnderRent * area;
      },

      // ============================
      //          그래프 생성
      // ============================

      // detailData를 하단 그래프에 전달하도록 값을 변환함
      convertData2Graph() {
         this.makePopulation(); // 유동인구 변화추이
         this.makeAge(); // 연령대 분포
         this.makeStoreSales(); // 업종별 매출액

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

      getMax(arr) {
         this.maxSales = 0;
         for (var i = 0; i < arr.length; ++i) {
            if (this.maxSales < arr[i]) {
               this.maxSales = arr[i];
            }
         }
      },

      // 업종별 매출액
      makeStoreSales() {
         var seq = ['CS100001', 'CS100002', 'CS100003', 'CS100004', 'CS100008', 'CS100006', 'CS100007', 'CS100009', 'CS100010', 'CS100005'];
         var result = [];

         here: for (const key in seq) {
            // console.log(seq[key]);

            // 해당 코드값이 있는지 확인
            for (const idx in this.detailData.estimatedSalesList) {
               var code = this.detailData.estimatedSalesList[idx].industryCode;

               var sale = this.detailData.estimatedSalesList[idx].salesMonth;
               var cnt = this.detailData.estimatedSalesList[idx].storesCount;

               if (cnt != 0 && seq[key] === code) {
                  result.push(Math.floor(eval(`${sale}/${cnt}`)));
                  continue here;
               }
            }

            // 없는 경우
            result.push(0);
         }
         this.getMax(result);

         this.storeSales.value = result;
      },

      // ====================================

      bookmark: function () {
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
               () => {},
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

      closeCompo: function () {
         this.isLoad = false;
         this.initDataset();

         this.$emit('close-expended');
      },
   },
   filters: {
      currency: function (value) {
         var num = new Number(value);
         return num.toFixed(0).replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, '$1,');
      },
   },
};
</script>
<style scoped lang="scss">
* {
   /* border: 1px dashed green; */
   box-sizing: border-box;
}

.emoji {
   width: 25px;
}

.notes-footer {
   margin-top: 10px;
   margin-left: 40px;
   width: 350px;

   font-size: 7pt;
   line-height: 12px;
   color: rgb(165, 165, 165);

   span {
      color: rgb(223, 15, 15);
   }
}

.md-container {
   width: 100%;
   height: 100%;
   display: flex;
   flex-direction: column;

   .top-wrapper {
      height: 200px;
      box-shadow: 9px 9px 20px #56565629;
      border-radius: 0px 0px 35px 35px;
      padding: 25px;

      .header {
         /* background-color: red; */
         height: 40px;

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
      .header__info {
         width: 100%;
         height: 25px;

         font-size: 9pt;
         color: rgb(85, 85, 85);
         padding-left: 5px;
         /* text-align: center; */

         .title {
            margin-right: 10px;
            font-weight: 600;
         }

         .value {
            margin-left: 3px;
            color: #ff6633;
         }
      }

      .basic-info-section {
         background-color: rgb(247, 247, 247);
         border: 1px solid rgb(193, 193, 193);

         padding: 8px 20px;
         line-height: 20px;

         font-weight: 600;
         font-size: 10pt;
         color: rgb(41, 41, 41);

         .info-line {
            i {
               font-size: 9pt;
            }

            span.value {
               margin-right: 2px;
               color: #ff3c00;
            }
         }
      }

      .content-common {
         display: flex;
         flex-direction: column;
         margin-top: 15px;

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
      padding-top: 0px;

      .content-detail {
         display: flex;
         flex-direction: column;
         align-items: center;

         .section {
            margin-top: 50px;
            width: 100%;
         }

         .section table {
            border: 0px solid #dedede;
            width: 90%;
            text-align: center;

            margin: 0px auto;
            margin-top: 20px;

            border: solid #ccc 1px;
            border-radius: 6px;

            td,
            th {
               border: 1px solid #d8d8d8;
               padding: 8px 0px;
            }

            td {
               width: 50px;

               p {
                  margin-top: 4px;
                  font-size: 6pt;
                  color: rgb(108, 108, 108);
               }
            }

            tr:nth-child(even) {
               background: #f7f7f7;
            }

            &.storeCnt {
               .cate {
                  font-size: 9pt;
                  font-weight: 600;
                  color: rgb(65, 65, 65);
               }
               tr > td {
                  font-size: 9pt;
               }
            }
         }

         p.title {
            /* display: inline-block; */
            font-size: 14pt;
            font-weight: 600;
            margin-left: 10px;
         }

         span.notes {
            /* float: right; */
            font-size: 9pt;
            color: gray;
            margin-left: 10px;
         }

         .graph {
            height: 380px;
            width: 380px;
            margin: 0 auto;
            margin-top: 25px;
         }

         /* 상권의 임대료 정보만 오버라이트 */
         .section.store {
            height: 200px;
            margin-bottom: 70px;
            /* background-color: red; */

            .nullInfo {
               display: inline-block;
               background-color: white;
               border: 1px solid black;
               height: 40px;
               width: 240px;
               font-size: 12pt;
               text-align: center;
               font-weight: 600;
               line-height: 40px;
               color: rgb(50, 50, 50);
               position: relative;
               top: 130px;
               left: 10px;
               z-index: 10;
            }

            .wrapper {
               &.nullRental {
                  filter: blur(2px);
               }

               position: relative;
               height: inherit;
               width: 100%;
               /* background-color: pink; */

               .left-box,
               .mid-box,
               .right-box {
                  display: inline-flex;
                  flex-direction: column;
                  align-items: center;
                  position: relative;

                  font-size: 10pt;
                  z-index: 2;

                  p {
                     background-color: rgb(222, 222, 222);
                     padding: 5px 10px;
                     border-radius: 20px;
                     font-weight: 600;
                  }

                  .under {
                     margin-top: 27px;
                  }

                  .first {
                     margin-top: 22px;
                  }

                  .label {
                     color: rgb(150, 150, 150);
                     margin-bottom: 10px;
                     background: none;
                     /* border-radius: 0px; */
                     /* border-bottom: 2px solid rgb(114, 15, 15); */
                  }
               }

               .left-box {
                  bottom: -69px;
                  left: 20px;
                  p {
                     background: none;
                  }
               }

               .mid-box {
                  bottom: -34px;
                  left: 55px;
                  p {
                     background-color: #ffcc00;
                  }
               }

               .right-box {
                  bottom: -34px;
                  left: 190px;
                  p {
                     background-color: #b8f2ff;
                  }
               }

               img {
                  position: absolute;
                  bottom: 0px;
                  left: 20px;
                  width: 90%;
                  height: 100%;
                  /* object-fit: cover; */
                  /* object-position: 20px -40px; */
               }

               .notes-footer {
                  margin: 0px;
                  width: 300px;

                  position: absolute;
                  bottom: -50px;
                  left: 70px;
               }
            }
         }
      }
   }
}
</style>
