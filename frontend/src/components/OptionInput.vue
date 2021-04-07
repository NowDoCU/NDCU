<template>
   <div class="oi-container">
      <div class="header">
         <i @click="closeCompo" class="fas fa-angle-double-left"></i>
      </div>
      <div class="content-wrapper">
         <div class="top-wrapper">
            <span>🍳 업종</span>
            <category class="category" @category-select="onCateSelect"></category>
         </div>
         <div class="middle-wrapper">
            <span class="md-subtitle">🌏 지역</span>
            <div :class="{ 'selected-ds': selectedDs.length }" v-if="selectedDs">
               <div class="ds-item" v-for="(ds, idx) in selectedDs" :key="idx">
                  <span class="ds-text">{{ ds }}</span>
                  <i @click="deleteDs(idx)" class="fas fa-times-circle del"></i>
               </div>
            </div>
            <div 
               class="search" 
               :class="[{'unsel': selectedDs.length == 0}, { 'selec': selectedDs.length }]"
               @keyup.up="selectValue('up')"
               @keyup.down="selectValue('down')"
               @keyup.right="selectValue('right')" 
               @keyup.left="selectValue('left')">
               <input 
                  id="districtInput" 
                  class="ds-input" 
                  type="text" 
                  @input="filter($event.target.value)" 
                  @keypress.enter="addDistrct($event.target.value)" 
                  @click="showUl=!showUl" 
                  autocomplete="off" 
               />
               <i v-show="showUl" @click="showUl=false" class="fas fa-times-circle cancle"></i>
               <ul tabindex="0" class="ds-list" v-if="showUl" @mouseover="removeValue">
                  <li
                     class="ds-items"
                     tabindex="-1"
                     v-for="(district, idx) in filterList"
                     :class="[{ unsel: selectedDs.indexOf(district) === -1 }, { sele: selectedDs.indexOf(district) !== -1 }]"
                     :key="idx"
                     @click="changeValue(district)"
                     @keyup.enter="selectValue('enter', district)"
                  >
                     <span>{{ district }}</span>
                  </li>
               </ul>
            </div>
         </div>
         <div class="bottom-wrapper">
            <span class="bottom-header">📋 추가정보</span>
            <div class="option-wrapper">
               <div class="deposit-wrapper">
                  <div class="b-header">
                     <span class="title">보증금</span>
                     <span class="won">{{ deposit }}만원 이하</span>
                  </div>
                  <input type="range" v-model="deposit" min="250" max="3500" value="55000" name="" class="range">
                  <div class="d-label">
                     <span class="l-span">250</span>
                     <span class="l-span">990</span>
                     <span class="l-span">1850</span>
                     <span class="l-span">2700</span>
                     <span class="l-span">3500</span>
                  </div>
               </div>
               <div class="rent-wrapper">
                  <div class="b-header">
                     <span class="title">월임대료(제곱미터 기준)</span>
                     <span class="won">{{ rent }}원 이하</span>
                  </div>
                  <input type="range" v-model="rent" min="15000" max="55000" value="55000" name="" class="range">
                  <div class="r-label">
                     <span class="l-span">15000</span>
                     <span class="l-span">25000</span>
                     <span class="l-span">35000</span>
                     <span class="l-span">45000</span>
                     <span class="l-span">55000</span>
                  </div>
               </div>
               <div class="client-wrapper">
                  <div class="c-header">대상고객</div>
                  <div class="bts">
                     <div class="l-bt" @click="selectClient('직장인')">
                        직장인
                     </div>
                     <div class="r-bt" @click="selectClient('주거인')">
                        주거인
                     </div>
                  </div>
               </div>
               <div class="age-wrapper">
                  <div class="a-header">대상 연령층</div>
                  <div class="a-checks">
                     <div class="checks etrans small">
                        <input type="checkbox" name="age" value="10" v-model="selectedAges" id="10age">
                        <label for="10age">10대</label>
                     </div>
                     <div class="checks etrans small">
                        <input type="checkbox" name="age" value="20" v-model="selectedAges" id="20age">
                        <label for="20age">20대</label>
                     </div>
                     <div class="checks etrans small">
                        <input type="checkbox" name="age" value="30" v-model="selectedAges" id="30age">
                        <label for="30age">30대</label>
                     </div>
                     <div class="checks etrans small">
                        <input type="checkbox" name="age" value="40" v-model="selectedAges" id="40age">
                        <label for="40age">40대</label>
                     </div>
                     <div class="checks etrans small">
                        <input type="checkbox" name="age" value="50" v-model="selectedAges" id="50age">
                        <label for="50age">50대</label>
                     </div>
                     <div class="checks etrans small">
                        <input type="checkbox" name="age" value="60" v-model="selectedAges" id="60age">
                        <label for="60age">60대</label>
                     </div>
                  </div>
               </div>
            </div>
         </div>

      </div>
      <div class="footer">
         <button @click="getRecommended" class="com-bt">추천 받아보기</button>
      </div>
   </div>
</template>
<script>
import Category from '@/components/Categories/Category.vue';

export default {
   name: 'OptionInput',
   components: {
      Category,
   },
   data: function() {
      return {
         test: false,
         selectedCate: '',
         selectedClient: '',
         showUl: false,
         deposit: 3441,
         rent: 55000,
         query: '', // 지역 검색어
         selectedDs: [], // 선택된 지역
         selectedAges: [],
         districts: [
            '종로구',
            '중구',
            '용산구',
            '성동구',
            '광진구',
            '동대문구',
            '중랑구',
            '성북구',
            '강북구',
            '도봉구',
            '노원구',
            '은평구',
            '서대문구',
            '마포구',
            '양천구',
            '강서구',
            '구로구',
            '금천구',
            '영등포구',
            '동작구',
            '관악구',
            '서초구',
            '강남구',
            '송파구',
            '강동구',
         ],
         filterList: [
            '종로구',
            '중구',
            '용산구',
            '성동구',
            '광진구',
            '동대문구',
            '중랑구',
            '성북구',
            '강북구',
            '도봉구',
            '노원구',
            '은평구',
            '서대문구',
            '마포구',
            '양천구',
            '강서구',
            '구로구',
            '금천구',
            '영등포구',
            '동작구',
            '관악구',
            '서초구',
            '강남구',
            '송파구',
            '강동구',
         ], // 자동완성으로 걸러진 결과 (초기리스트는 모든 구)
      };
   },
   methods: {
      // expeneded compo 닫기
      closeCompo: function () {
         this.$emit('close-expended')
      },
      // 업종 선택 완료햇을 때
      onCateSelect: function(category) {
         this.selectedCate = category
      },
      // 자동완성 결과에서 하나 선택 했을 때
      changeValue(district) {
         this.addDistrct(district); // 선택된 지역 목록에 추가
         document.querySelector('.ds-list').classList.remove('key') // ul에 포커싱된 항목 없다는 것 표시
         document.querySelector('.ds-input').focus()
         this.filterList = this.districts
      },
      addDistrct: function(district) {
         const isSel = this.selectedDs.indexOf(district);
         if (isSel >= 0) { // 이미 선택된 지역이면
            alert('이미 선택된 지역입니다.');
         } else {
            if (this.districts.indexOf(district) != -1) { // 입력된 값이 유효한 지역구 이름이면(검색창에서 지역이름 아닌 것 입력한 경우나 오타났을 경우 차단)
               if (this.selectedDs.length < 3) { // 선택된 지역목록이 최대목록이 아니면
                  this.selectedDs.push(district); // 추가
                  document.querySelector('.ds-input').value = null;
                  if (this.selectedDs.length === 3) {
                     this.showUl = false
                  } 
               } else {
                  this.showUl = false
                  alert('지역은 최대 3개까지 선택가능합니다.');
               }
            } else {
               document.querySelector('.ds-input').value = null;
               alert('올바른 지역명이 아닙니다.');
            }
         }
      },
      // 선택한 지역 제거
      deleteDs: function(idx) {
         this.selectedDs.splice(idx, 1);
      },
      // 검색 결과에서 마우스 작동일 때
      removeValue: function() {
         if (document.querySelector('.ds-list').classList.contains('key')) {
            document.querySelector('.ds-list').classList.remove('key');
            document.querySelector('.ds-list li.sel').classList.remove('sel');
         }
      },
      // search내에서 키보드 상하좌우 컨트롤
      selectValue: function(keycode, ds) {
         if (this.filterList && this.showUl) {
            const hasClass = document.querySelector('.ds-list').classList.contains('key');
            if (keycode === 'down') {
               if (!hasClass) {
                  // 선택된 항목이 없으면
                  const thisEl = document.querySelectorAll('.ds-list li')[0];
                  document.querySelector('.ds-list').classList.add('key'); // 선택된 항목이 있음을 표시
                  thisEl.classList.add('sel'); //첫번째 항목 선택된 것 표시
                  thisEl.focus(); // 첫번쨰 항목으로 포커싱
               } else {
                  // 이미 선택된 항목이 있으면
                  const lastEl = document.querySelector('.ds-list li:last-child');
                  const thisEl = document.querySelector('.ds-list li.sel');
                  if (this.filterList.indexOf(thisEl.innerText) < 20 && this.filterList.length > 5) { // 선택된 항목이 맨 밑줄 항목이 아니면 (ul에서)
                     const lowerEl = thisEl.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling.nextElementSibling; //밑의 항목(index 5차이남)
                     if (!lastEl.classList.contains('sel') && lowerEl) {
                        // 현재 선택된 항목이 마지막항목이 아니면
                        thisEl.classList.remove('sel'); // 현재 선택된 항목 선택 제거
                        lowerEl.classList.add('sel'); // 선택됐다는 클래스(스타일링) 적용 
                        lowerEl.focus(); //다음 항목 선택
                     }
                  }
               }
            } else if (keycode === 'right' && hasClass) {
               // 이미 선택된 항목이 있으면
               const lastEl = document.querySelector('.ds-list li:last-child');
               const thisEl = document.querySelector('.ds-list li.sel');
               const nextEl = thisEl.nextElementSibling;
               if (!lastEl.classList.contains('sel')) {
                  // 현재 선택된 항목이 마지막항목이 아니면
                  thisEl.classList.remove('sel'); // 현재 선택된 항목 선택 제거
                  nextEl.classList.add('sel'); //다음 항목 선택
                  nextEl.focus();
               }
            } else if (keycode === 'left' && hasClass) {
               const firstEl = document.querySelectorAll('.ds-list li')[0];
               const thisEl = document.querySelector('.ds-list li.sel');
               const prevEl = thisEl.previousElementSibling;
               if (!firstEl.classList.contains('sel')) {
                  //현재 선택된 항목이 첫번쨰 항목이 아니면
                  thisEl.classList.remove('sel');
                  prevEl.classList.add('sel');
                  prevEl.focus();
               }
            } else if (keycode === 'up' && hasClass) {
               const firstEl = document.querySelectorAll('.ds-list li')[0];
               const thisEl = document.querySelector('.ds-list li.sel');
               if (this.filterList.indexOf(thisEl.innerText) >= 5 && this.filterList.length > 5) { // 선택된 항목이 맨 윗줄 항목이 아니면 (ul에서)
                  const upperEl = thisEl.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling.previousElementSibling;
                  if (!firstEl.classList.contains('sel') && upperEl) {
                     //현재 선택된 항목이 첫번쨰 항목이 아니면
                     thisEl.classList.remove('sel');
                     upperEl.classList.add('sel');
                     upperEl.focus();
                  } 
               } else if (firstEl === thisEl) { // 맨 윗줄 항목이면
                  thisEl.classList.remove('sel'); 
                  document.querySelector('.ds-list').classList.remove('key')
                  document.querySelector('.ds-input').focus(); // input 으로 포커싱
               }
            } else if (keycode === 'enter' && hasClass) {
               this.changeValue(ds);
            }
         }
      },
      // 자동완성 위한 필터
      filter: function(q) {
         const reg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|\s]/.test(q);
         if (reg === false) {
            this.filterList = this.districts.filter((el) => {
               return el.match(q);
            });
         }
      },
      // 조건 입력 완료했을 때
      getRecommended: function() {
         if (this.selectedCate && this.selectedDs) {
            const options = {
               category: this.selectedCate,
               districts: this.selectedDs,
            };
            this.$emit('input-complete', options);
         } else {
            alert('필수 조건을 입력해주세요. (업종, 지역)')
         }
      },
      // slide bar 함수
      callbackRange1: function (val) {
         this.dSlider.rangeValue = val
      },
      callbackRange2: function (val) {
         this.rSlider.rangeValue = val
      },
      // 대상고객 선택
      selectClient: function (cl) {
         if (cl==='직장인') {
            this.selectedClient = cl
            document.querySelector('.r-bt').classList.remove('sel-cl')
            document.querySelector('.l-bt').classList.add('sel-cl')
         } else {
            this.selectedClient = cl
            document.querySelector('.l-bt').classList.remove('sel-cl')
            document.querySelector('.r-bt').classList.add('sel-cl')
         }
      }
   },
};
</script>
<style scoped lang="scss">
.oi-container {
   height: 100%;
   padding: 0 15px;
   display: flex;
   flex-direction: column;
   justify-content: space-between;
   align-items: center;
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
   
   .content-wrapper {
      overflow-y: scroll;
      overflow-x: hidden;
      margin-top: 60px;
      margin-bottom: 30px;
      padding: 0 15px;
      height: 80%;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      .top-wrapper {
         height: 200px;
         width: 100%;
         span {
            font-size: 15pt;
            font-weight: 900;
            margin: 0 10px;
         }
         .category {
            margin-top: 15px;
         }
      }
      .middle-wrapper {
         margin-top: 15px;
         height: 120px;
         width: 100%;
         .md-subtitle {
            font-size: 15pt;
            font-weight: 900;
            margin: 0 10px;
         }
         .selected-ds {
            margin: 5px;
            display: flex;
            justify-content: center;
            animation: 0.5s ease-out 0s 1 collapse;
            @keyframes collapse {
               0% {
                  height: 10px;
                  display: none;
               }
               100% {
                  height: 30px;
               }
            }
            .ds-item {
               display: flex;
               justify-content: space-around;
               align-items: center;
               background-color: #ff6633;
               margin: 0 5px;
               height: 10px;
               padding: 10px 12px;
               border-radius: 20px;
               color: white;
               text-align: center;
               .ds-text {
                  font-size: 9pt;
                  font-weight: 100;
               }
               .del {
                  font-size: 8pt;
                  margin-left: 6px;
               }
               .del:hover {
                  cursor: pointer;
               }
               animation: 1s ease-out 0s 1 opacity-control;
               @keyframes opacity-control {
                  0% {
                     opacity: 0;
                  }
                  100% {
                     opacity: 1;
                  }
               }
            }
         }
         .search {
            display: flex;
            flex-direction: column;
            align-items: center;
            i {
               z-index: 150;
               position: relative;
               top: -32px;
               right: -175px;
               cursor: pointer;
            }
            .ds-input {
               padding: 5px 20px;
               z-index: 100;
               width: 350px;
               height: 35px;
               border:none;
               border-radius: 20px;
               background-color: #e7e7e7;
            }
            .ds-input:focus {
               outline: none;
            }
            .ds-list {
               z-index: 50;
               float: inherit;
               margin-top: -50px;
               border-radius: 20px;
               width: 370px;
               background-color: white;
               box-shadow: 0px 9px 20px 0px #56565629;
               padding: 50px 10px 10px 10px;
               display: flex;
               justify-content: center;
               flex-wrap: wrap;
               .ds-items {
                  font-size: 10pt;
                  margin: 3px;
                  padding: 10px 5px;
                  text-align: center;
                  border-radius: 20px;
                  // color: white;
                  width: 58px;
                  height: 10px;
               }
               .ds-items:focus {
                  outline: none;
               }
               .unsel {
                  background-color: #e7e7e7;
               }
               .unsel:hover {
                  cursor: pointer;
                  background-color: #ff6633;
                  color:white
               }
               .sel {
                  background-color: #ff6633;
                  color:white
               }
               .sele {
                  background-color: #ff6633;
                  color:white
               }
            }
            .ds-list:focus {
               outline: none;
            }
         }
         .unsel {
            margin-top:30px;
         }
         .selec {
            margin-top: 15px;
         }
      }
      .bottom-wrapper {
         height: 250px;
         width: 100%;
         display: flex;
         flex-direction: column;
         justify-content: space-between;
         .bottom-header {
            font-size: 15pt;
            font-weight: 900;
            margin: 0 10px;
         }
         .option-wrapper {
            margin-top: 20px;
            width: 100%;
            height: 100%;
            .deposit-wrapper {
               padding: 15px;
               .b-header {
                  display: flex;
                  justify-content: space-between;
                  font-weight: 600;
                  .title {
                     font-size: 12pt
                  }
                  .won {
                     font-size: 10pt
                  }
               }
               .range {
                  margin-top: 20px;
                  -webkit-appearance: none;
                  width: 100%;
                  height: 10px;
                  border-radius: 5px;
                  background: #ff6633;
                  outline: none;
                  opacity: 1;
               }
                              
               .range::-webkit-slider-thumb {
                  -webkit-appearance: none;
                  appearance: none;
                  width: 40px;
                  height: 40px;
                  border: 0;
                  border-radius: 50%;
                  background-image: url('../assets/image/dollar.png');
                  background-size: contain;
                  background-position: center center;
                  background-repeat: no-repeat;
                  cursor: pointer;
               }
               .range::-moz-range-thumb {
                  width: 40px;
                  height: 40px;
                  border: 0;
                  border-radius: 50%;
                  background-image: url('../assets/image/dollar.png');
                  background-size: contain;
                  background-position: center center;
                  background-repeat: no-repeat;
                  cursor: pointer;
               }

               .d-label {
                  padding: 10px 0;
                  widows: 100%;
                  display: flex;
                  justify-content: space-between;
                  .l-span {
                     font-size: 10pt;
                     font-weight: 200;
                  }
               }
            }
            .rent-wrapper {
               padding: 15px;
               .b-header {
                  display: flex;
                  justify-content: space-between;
                  font-weight: 600;
                  .title {
                     font-size: 12pt
                  }
                  .won {
                     font-size: 10pt
                  }
               }
               .range {
                  margin-top: 20px;
                  -webkit-appearance: none;
                  width: 100%;
                  height: 10px;
                  border-radius: 5px;
                  background: #ff6633;
                  outline: none;
                  opacity: 1;
               }          
               .range::-webkit-slider-thumb {
                  -webkit-appearance: none;
                  appearance: none;
                  width: 40px;
                  height: 40px;
                  border: 0;
                  border-radius: 50%;
                  background-image: url('../assets/image/dollar.png');
                  background-size: contain;
                  background-position: center center;
                  background-repeat: no-repeat;
                  cursor: pointer;
               }
               .range::-moz-range-thumb {
                  width: 40px;
                  height: 40px;
                  border: 0;
                  border-radius: 50%;
                  background-image: url('../assets/image/dollar.png');
                  background-size: contain;
                  background-position: center center;
                  background-repeat: no-repeat;
                  cursor: pointer;
               }

               .r-label {
                  padding: 10px 0;
                  widows: 100%;
                  display: flex;
                  justify-content: space-between;
                  .l-span {
                     font-size: 10pt;
                     font-weight: 200;
                  }
               }
            }
            .client-wrapper {
               padding: 0 20px;
               .c-header {
                  font-weight: 600;
                  font-size: 12pt;
               }
               .bts {
                  margin-top: 20px;
                  display: flex;
                  justify-content: space-around;
                  align-items: center;
                  .l-bt {
                     cursor: pointer;
                     border-radius: 20px;
                     width: 150px;
                     background-color: #e7e7e7;
                     // color: white;
                     height: 70px;
                     display: flex;
                     justify-content: center;
                     align-items: center;
                     box-shadow: 4px 4px 4px #56565629;
                  }
                  .r-bt {
                     cursor: pointer;
                     border-radius: 20px;
                     width: 150px;
                     background-color: #e7e7e7;
                     // color: white;
                     height: 70px;
                     display: flex;
                     justify-content: center;
                     align-items: center;
                     box-shadow: 4px 4px 4px #56565629
                  }
                  .sel-cl {
                     background-color: #ffcc00;
                  }

               }
            }
            .age-wrapper {
               padding: 20px 20px;
               .a-header {
                  font-weight: 600;
                  font-size: 12pt;
               }
               .a-checks {
                  margin-top: 20px;
                  display: flex;
                  align-items: center;
                  justify-content: space-between;
                  // checkbox styling
                  .checks {position: relative;}

                  .checks input[type="checkbox"] {  /* 실제 체크박스는 화면에서 숨김 */
                     position: absolute;
                     width: 1px;
                     height: 1px;
                     padding: 0;
                     margin: -1px;
                     overflow: hidden;
                     clip:rect(0,0,0,0);
                     border: 0
                  }
                  .checks input[type="checkbox"] + label {
                     display: inline-block;
                     position: relative;
                     cursor: pointer;
                     -webkit-user-select: none;
                     -moz-user-select: none;
                     -ms-user-select: none;
                  }
                  .checks input[type="checkbox"] + label:before {  /* 가짜 체크박스 */
                     content: ' ';
                     display: inline-block;
                     width: 21px;  /* 체크박스의 너비를 지정 */
                     height: 21px;  /* 체크박스의 높이를 지정 */
                     line-height: 21px; /* 세로정렬을 위해 높이값과 일치 */
                     margin: -2px 8 0 0;
                     text-align: center; 
                     vertical-align: middle;
                     background: #fafafa;
                     border: 1px solid #cacece;
                     border-radius : 3px;
                     box-shadow: 0px 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05);
                  }
                  .checks input[type="checkbox"] + label:active:before,
                  .checks input[type="checkbox"]:checked + label:active:before {
                     box-shadow: 0 1px 2px rgba(0,0,0,0.05), inset 0px 1px 3px rgba(0,0,0,0.1);
                  }

                  .checks input[type="checkbox"]:checked + label:before {  /* 체크박스를 체크했을때 */ 
                     content: '\2714';  /* 체크표시 유니코드 사용 */
                     color: #99a1a7;
                     text-shadow: 1px 1px #fff;
                     background: #e9ecee;
                     border-color: #adb8c0;
                     box-shadow: 0px 1px 2px rgba(0,0,0,0.05), inset 0px -15px 10px -12px rgba(0,0,0,0.05), inset 15px 10px -12px rgba(255,255,255,0.1);
                  }

                  .checks.small input[type="checkbox"] + label {
                     font-size: 15px;
                  }

                  .checks.small input[type="checkbox"] + label:before {
                     width: 15px;
                     height: 15px;
                     line-height: 15px;
                     font-size: 11px;
                  }

                  .checks.etrans input[type="checkbox"] + label {
                     padding-left: 23px;
                  }
                  .checks.etrans input[type="checkbox"] + label:before {
                     position: absolute;
                     left: 0;
                     top: 0;
                     margin-top: 0;
                     opacity: .6;
                     box-shadow: none;
                     border-color: #ff6633;
                     -webkit-transition: all .12s, border-color .08s;
                     transition: all .12s, border-color .08s;
                  }

                  .checks.etrans input[type="checkbox"]:checked + label:before {
                     position: absolute;
                     content: "";
                     width: 10px;
                     top: -5px;
                     left: 5px;
                     border-radius: 0;
                     opacity:1; 
                     background: transparent;
                     border-color:transparent #ff6633 #ff6633 transparent;
                     border-top-color:transparent;
                     border-left-color:transparent;
                     -ms-transform:rotate(45deg);
                     -webkit-transform:rotate(45deg);
                     transform:rotate(45deg);
                  }

                  .no-csstransforms .checks.etrans input[type="checkbox"]:checked + label:before {
                  /*content:"\2713";*/
                     content: "\2714";
                     top: 0;
                     left: 0;
                     width: 21px;
                     line-height: 21px;
                     color: #ff6633;
                     text-align: center;
                     border: 1px solid #ff6633;
                  }
               }
            }
         }
      }

   }
   // .content-wrapper::-webkit-scrollbar-track-piece:end {
   //    background: transparent;
   //    margin-bottom: 10px;
   // }
   // .content-wrapper::-webkit-scrollbar-track-piece:start {
   //    background: transparent;
   //    margin-top: 10px;
   // }
   .footer {
      height: 10%;
      margin-bottom: 25px;
      .com-bt {
         color: rgb(57, 104, 235);
         font-weight: 700;
         padding: 5px;
         width: 150px;
         height: 50px;
         box-shadow: 5px 5px 0 rgb(57, 104, 235);
         border-radius: 30px;
         border: rgb(57, 104, 235) solid 2px;
         // background: linear-gradient(to bottom right, #7A9FFF, #1D3CAA);
         background: white;
      }
      .com-bt:hover {
         margin-top: 5px;
         margin-left: 5px;
         cursor: pointer;
         // background: linear-gradient(to bottom right,#1D3CAA,  #7A9FFF);
         // background: rgb(45, 83, 186);
         box-shadow: none;
      }
      .com-bt:focus {
         outline: none;
      }
   }
}
.handler {
   width: 30px;
   height: 30px;
}
</style>
