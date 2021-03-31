<template>
   <div class="oi-container">
      <div class="header">
         <i @click="closeCompo" class="fas fa-angle-double-left"></i>
      </div>
      <div class="content-wrapper">
         <div class="top-wrapper">
            <span>업종</span>
            <category class="category" @category-select="onCateSelect"></category>
         </div>
         <div class="middle-wrapper">
            <span class="md-subtitle">지역</span>
            <div :class="{ 'selected-ds': selectedDs.length }" v-if="showDsList">
               <div class="ds-item" v-for="(ds, idx) in selectedDs" :key="idx">
                  <span class="ds-text">{{ ds }}</span>
                  <i @click="deleteDs(idx)" class="fas fa-times-circle del"></i>
               </div>
            </div>
            <div 
               class="search" 
               :class="[{'unsel': selectedDs.length == 0}, { 'selec': selectedDs.length }]"
               @keyup.right="selectValue('right')" @keyup.left="selectValue('left')">
               <input id="districtInput" class="ds-input" type="text" @input="filter($event.target.value)" @keypress.enter="addDistrct($event.target.value)" @click="[showUl=!showUl, showDsList=true]" autocomplete="off" />
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
            <span>추가정보</span>
            <div class="option-wrapper"></div>
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
         selectedCate: '',
         showDsList: false, // 선택된 지역 목록
         showUl: false,
         isActive: false, // 지역 input 자동완성
         query: '', // 지역 검색어
         selectedDs: [], // 선택된 지역
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
         ], // 자동완성으로 걸러진 결과
      };
   },
   methods: {
      // expeneded compo 닫기
      closeCompo: function () {
         this.$emit('close-expended')
      },
      // 업종 선택 완료햇을 때
      onCateSelect: function(category) {
         if (category.cate !== this.selectedCate) {
            if (this.selectedCate === '') { // 현재 선택된 업종 없으면
               this.selectedCate = category.cate; // 업종 선택 처리
            }
         } else if (category.cate === this.selectedCate) { // 선택된 업종과 원래 선택 되어 있던 업종이 같으면
            this.selectedCate = '' // 선택 취소
         }
      },
      // 자동완성 결과에서 하나 선택 했을 때
      changeValue(district) {
         this.addDistrct(district); // 선택된 지역 목록에 추가
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
      selectValue: function(keycode, ds) {
         if (this.isActive === true) {
            const hasClass = document.querySelector('.ds-list').classList.contains('key');
            if (keycode === 'right') {
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
                  const nextEl = thisEl.nextElementSibling;
                  if (!lastEl.classList.contains('sel')) {
                     // 현재 선택된 항목이 마지막항목이 아니면
                     thisEl.classList.remove('sel'); // 현재 선택된 항목 선택 제거
                     nextEl.classList.add('sel'); //다음 항목 선택
                     nextEl.focus();
                  }
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
               } else {
                  document.querySelector('.ds-input').focus();
               }
            } else if (keycode === 'enter' && hasClass) {
               this.changeValue(ds);
            }
         }
      },
      // 검색결과 창에 모든 지역 보여주기
      getAllDs: function() {
         this.showDsList = true;
         this.filterList = this.districts;
      },
      // 자동완성 위한 필터
      filter: function(q) {
         const reg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|\s]/.test(q);
         if (reg === false) {
            this.isActive = true;
            this.filterList = this.districts.filter((el) => {
               return el.match(q);
            });
         } else {
            this.isActive = false;
         }
      },
      // 조건 입력 완료했을 때
      getRecommended: function() {
         const options = {
            category: this.selectedCate,
            districts: this.selectedDs,
         };
         this.$emit('input-complete', options);
      },
   },
};
</script>
<style scoped lang="scss">
.oi-container {
   height: 100%;
   padding: 0 10px;
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
      margin-top: 50px;
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
               background-color: rgb(57, 104, 235);
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
               padding: 5px;
               z-index: 100;
               width: 380px;
               height: 35px;
               border-color: lightgray;
               border-radius: 20px;
               background-color: rgba(255, 255, 255, 0.68);
            }
            .ds-input:focus {
               outline: none;
            }
            .ds-list {
               z-index: 50;
               float: inherit;
               margin-top: -42px;
               border-radius: 20px;
               width: 375px;
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
                  color: white;
                  width: 58px;
                  height: 10px;
               }
               .ds-items:focus {
                  outline: none;
               }
               .unsel {
                  background-color: rgb(57, 104, 235);
               }
               .unsel:hover {
                  cursor: pointer;
                  background-color: rgb(45, 83, 186);
               }
               .sel {
                  background-color: rgb(45, 83, 186);
               }
               .sele {
                  background-color: gray;
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
         z-index: -10;
         height: 250px;
         width: 100%;
         display: flex;
         flex-direction: column;
         justify-content: space-between;
         span {
            font-size: 15pt;
            font-weight: 900;
            margin: 0 10px;
         }
         .option-wrapper {
            margin-top: 20px;
            background-color: burlywood;
            width: 100%;
            height: 90%;
         }
      }

   }
   .footer {
      height: 10%;
      .com-bt {
         color: white;
         padding: 5px;
         width: 150px;
         height: 50px;
         box-shadow: 0 4px 4px lightgray;
         border-radius: 30px;
         border: none;
         background: linear-gradient(to bottom right, #7A9FFF, #1D3CAA);
      }
      .com-bt:hover {
         cursor: pointer;
         background: linear-gradient(to bottom right,#1D3CAA,  #7A9FFF);
      }
      .com-bt:focus {
         outline: none;
      }
   }
}
</style>
