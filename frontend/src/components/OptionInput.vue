<template>
  <div class="oi-container">
    <div class="top-wrapper">
      <span>업종</span>
      <category @category-select="onCateSelect"></category>
    </div>
    <div class="middle-wrapper">
      <span>지역</span>
        {{ selectedDs }}
        <div 
          class="search"
          @keyup.right="selectValue('right')"
          @keyup.left="selectValue('left')"
        >
          <input
            id="districtInput" 
            class="ds-input" 
            type="text" 
            @input="filter($event.target.value)"
            @keypress.enter="addDistrct($event.target.value)"
            @click="getAllDs"
            autocomplete="off"
          >
          <ul
            tabindex="0"
            class="ds-list"
            v-if="filterList.length"
            @mouseover="removeValue"
          >
            <li
              tabindex="-1"
              v-for="(district, idx) in filterList"
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
    </div>
    <div class="footer">

    </div>
  </div>
</template>
<script>
import Category from './categories/Category.vue'

export default {
  name: 'OptionInput',
  components: {
    Category,
  },
  data: function () {
    return {
      selectedCate: '',
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
        '강동구'
      ],
      filterList: []
    }
  },
  methods: {
    onCateSelect: function (category) {
      this.selectedCate = category
    },
    changeValue(district) {
      this.addDistrct(district)
      this.filterList = []
      document.querySelector('.ds-input').value = null
    },
    addDistrct: function (district) {
      const isSel = this.selectedDs.indexOf(district)
      if (isSel >= 0) {
        alert('이미 선택된 지역입니다.')
      } 
      else {
        if (this.districts.indexOf(district) != -1) {
          if (this.selectedDs.length < 3) {
            this.selectedDs.push(district)
            this.filterList = []
            document.querySelector('.ds-input').value = null
          } else {
            alert('지역은 최대 3개까지 선택가능합니다.')
          }
        } else {
          alert('지역명을 입력해주세요.')
        }
      }
    },
    removeValue: function () {
      if (document.querySelector('.ds-list').classList.contains('key')) {
        document.querySelector('.ds-list').classList.remove('key')
        document.querySelector('.ds-list li.sel').classList.remove('sel')
      }
    },
    selectValue: function (keycode, ds) {
      if (this.isActive === true) {
        const hasClass = document.querySelector('.ds-list').classList.contains('key')
        if(keycode === 'right') {
          if (!hasClass) { // 선택된 항목이 없으면
            const thisEl = document.querySelectorAll('.ds-list li')[0]
            document.querySelector('.ds-list').classList.add('key') // 선택된 항목이 있음을 표시
            thisEl.classList.add('sel') //첫번째 항목 선택된 것 표시
            thisEl.focus() // 첫번쨰 항목으로 포커싱
          } else { // 이미 선택된 항목이 있으면
            const lastEl = document.querySelector('.ds-list li:last-child')
            const thisEl = document.querySelector('.ds-list li.sel')
            const nextEl = thisEl.nextElementSibling
            if (!lastEl.classList.contains('sel')) { // 현재 선택된 항목이 마지막항목이 아니면
              thisEl.classList.remove('sel') // 현재 선택된 항목 선택 제거
              nextEl.classList.add('sel') //다음 항목 선택
              nextEl.focus()
            }
          }
        }
        else if (keycode === 'left' && hasClass) {
          const firstEl = document.querySelectorAll('.ds-list li')[0]
          const thisEl = document.querySelector('.ds-list li.sel')
          const prevEl = thisEl.previousElementSibling
          if (!firstEl.classList.contains('sel')) { //현재 선택된 항목이 첫번쨰 항목이 아니면
            thisEl.classList.remove('sel')
            prevEl.classList.add('sel')
            prevEl.focus()
          } else {
            document.querySelector('.ds-input').focus()
          }
        }
        else if (keycode === 'enter' && hasClass) {
          this.changeValue(ds)
        }
      }
    },
    getAllDs: function () {
      this.filterList = this.districts
    },
    filter: function (q) {
      const reg = /[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9|\s]/.test(q)
      if (reg === false) {
        this.isActive = true
        this.filterList = this.districts.filter((el) => {
          return el.match(q)
        })
      } else {
        this.isActive = false
      }
    },
  },
  
}
</script>
<style scoped lang="scss">
.oi-container {
  height: 100%;
  padding: 50px 10px 10px;
  .middle-wrapper {
    .search {
      .ds-input {
        width: 100%;
      }
      .ds-list {
        width: 100%;
        background-color: violet;
        padding: 10px;
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        li {
          margin: 3px;
          background-color: aquamarine;
          width: 65px;
        }
      }
    }
  }
}
</style>