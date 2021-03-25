<template>
   <div class="oi-container">
      <div class="top-wrapper">
         <span>업종</span>
         <category class="category" @category-select="onCateSelect"></category>
      </div>
      <div class="middle-wrapper">
         <span class="md-subtitle">지역</span>
         <div :class="{ 'selected-ds': selectedDs.length }" v-if="showDsList">
            <div class="ds-item" v-for="(ds, idx) in selectedDs" :key="idx">
               <span class="ds-text">{{ ds }}</span>
               <span @click="deleteDs(idx)" class="del">x</span>
            </div>
         </div>
         <div class="search" @keyup.right="selectValue('right')" @keyup.left="selectValue('left')">
            <input id="districtInput" class="ds-input" type="text" @input="filter($event.target.value)" @keypress.enter="addDistrct($event.target.value)" @click="getAllDs" autocomplete="off" />
            <ul tabindex="0" class="ds-list" v-if="filterList.length" @mouseover="removeValue">
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
      <div class="footer">
         <button @click="getRecommended" class="com-bt">완료</button>
      </div>
   </div>
</template>
<script>
import Category from './categories/Category.vue';

export default {
   name: 'OptionInput',
   components: {
      Category,
   },
   data: function() {
      return {
         selectedCate: '',
         showDsList: false,
         isActive: false,
         query: '',
         selectedDs: [],
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
         filterList: [],
      };
   },
   methods: {
      onCateSelect: function(category) {
         console.log(category.cate, this.selectedCate);
         if (category.cate !== this.selectedCate) {
            if (this.selectedCate === '') {
               this.selectedCate = category.cate;
            }
         } else if (category.cate === this.selectedCate) {
            this.selectedCate = '';
         }
      },
      changeValue(district) {
         this.addDistrct(district);
         this.filterList = [];
         document.querySelector('.ds-input').value = null;
      },
      addDistrct: function(district) {
         const isSel = this.selectedDs.indexOf(district);
         if (isSel >= 0) {
            alert('이미 선택된 지역입니다.');
         } else {
            if (this.districts.indexOf(district) != -1) {
               if (this.selectedDs.length < 3) {
                  this.selectedDs.push(district);
                  this.filterList = [];
                  document.querySelector('.ds-input').value = null;
               } else {
                  alert('지역은 최대 3개까지 선택가능합니다.');
               }
            } else {
               alert('올바른 지역명이 아닙니다.');
            }
         }
      },
      deleteDs: function(idx) {
         this.selectedDs.splice(idx, 1);
      },
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
      getAllDs: function() {
         this.showDsList = true;
         this.filterList = this.districts;
      },
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
   height: 87%;
   padding: 30px 10px;
   display: flex;
   flex-direction: column;
   justify-content: space-evenly;
   align-items: center;
   .top-wrapper {
      height: 30%;
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
      height: 15%;
      width: 100%;
      // display: flex;
      // flex-direction: column;
      // justify-content: center;
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
               height: 0;
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
            background-color: rgb(80, 101, 176);
            margin: 0 5px;
            width: 58px;
            height: 10px;
            padding: 10px;
            border-radius: 20px;
            color: white;
            text-align: center;
            .ds-text {
               font-size: 9pt;
               font-weight: 100;
            }
            .del {
               font-size: 6pt;
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
         margin-top: 20px;
         display: flex;
         flex-direction: column;
         align-items: center;
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
               background-color: rgb(80, 101, 176);
            }
            .unsel:hover {
               cursor: pointer;
               background-color: rgb(57, 72, 128);
            }
            .sele {
               background-color: gray;
            }
         }
         .ds-list:focus {
            outline: none;
         }
      }
   }
   .bottom-wrapper {
      margin-top: -10px;
      z-index: -10;
      height: 30%;
      width: 100%;
      span {
         font-size: 15pt;
         font-weight: 900;
         margin: 0 10px;
      }
      .option-wrapper {
         margin-top: 20px;
         background-color: burlywood;
         width: 100%;
         height: 100%;
      }
   }
   .footer {
      position: absolute;
      bottom: 30px;
      right: 30px;
      .com-bt {
         color: white;
         padding: 5px;
         width: 70px;
         height: 40px;
         border-radius: 20px;
         border: none;
         background-color: rgb(80, 101, 176);
      }
      .com-bt:hover {
         cursor: pointer;
         background-color: rgb(57, 72, 128);
      }
      .com-bt:focus {
         outline: none;
      }
   }
}
</style>
