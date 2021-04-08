<template lang="">
   <div class="sm-container">
      <div class="top-wrapper">
         <div class="logo">
            <img src="@/assets/image/NDCU_logo_2.png" alt="" />
         </div>
         <div @click="onClickInputBt" class="btn-search">
            <i class="fas fa-search-location"></i>
         </div>
         <div @click="onClickBookmarkBt" class="btn-bookmark">
            <i class="far fa-bookmark"></i>
         </div>
      </div>
      <div class="bottom-wrapper">
         <i @click="aboutDialog = true" class="fas fa-info-circle about"></i><br /><br /><br /><br />
         <!--  login == 1 signup == 2 -->
         <span v-if="isLogin" @click="onClickBookmarkBt">{{ userInfo.name }}</span><br><br>
         <span v-if="isLogin" @click="doLogout">로그아웃</span>
         <span v-if="!isLogin" @click="controlModal(1, 'open')">로그인</span><br /><br />
         <span v-if="!isLogin" @click="controlModal(2, 'open')">회원가입</span>
      </div>

      <!-- 회원가입모달 -->
      <transition name="slide-to-middle">
         <div v-if="dialog" class="modal">
            <div class="modal__header">
               <img src="@/assets/image/NDCU_logo_eng.png" alt="">
            </div>
            <div class="modal__content">
               <div class="form-wrapper form-su">
                  <div class="input-wrapper">
                     <input @keypress.enter="doRegister" v-model="suEmail" id="email-ip" type="email" placeholder="이메일" @focus="onFocused('email')" @blur="onBlured('email')" />
                     <div id="email-dv" class="divider"></div>
                     <span v-if="!emailVal" class="tip">이메일 형식이 아닙니다.</span>
                  </div>
                  <div class="input-wrapper">
                     <input @keypress.enter="doRegister" v-model="suPwd" id="pwd-ip" maxlength="10" type="password" placeholder="비밀번호" @focus="onFocused('pwd')" @blur="onBlured('pwd')" />
                     <div id="pwd-dv" class="divider"></div>
                     <span v-if="!pwdVal1" class="tip">영문, 숫자 포함 6-10자 이내여야 합니다.</span>
                  </div>
                  <div class="input-wrapper">
                     <input @keypress.enter="doRegister" v-model="suPwdConf" id="pwdc-ip" maxlength="10" type="password" placeholder="비밀번호 확인" @focus="onFocused('pwdc')" @blur="onBlured('pwdc')" />
                     <div id="pwdc-dv" class="divider"></div>
                     <span v-if="!pwdVal2" class="tip">비밀번호가 일치하지 않습니다.</span>
                  </div>
                  <div class="ad-input-wrapper">
                     <div class="input-wrapper">
                        <input @keypress.enter="doRegister" v-model.trim="name" id="name-ip" maxlength="10" type="text" placeholder="별명" @focus="onFocused('name')" @blur="onBlured('name')" autocomplete="off"/>
                        <div id="name-dv" class="divider"></div>
                        <span id="name-tip" v-if="!nameVal" class="tip">필수항목(특수문자 제외)</span>
                     </div>
                     <div class="input-wrapper">
                        <input @keypress.enter="doRegister" v-model="tel" id="tel-ip" maxlength="11" type="tel" placeholder="휴대전화(선택)" @focus="onFocused('tel')" @blur="onBlured('tel')" autocomplete="off"/>
                        <div id="tel-dv" class="divider"></div>
                        <span v-if="telFocused" class="tel-tip">ex) 01012345678</span>
                     </div>
                  </div>
               </div>
            </div>
            <div class="modal__footer">
               <button class="login-bt" :class="{ vali: emailVali(suEmail) && pwdVali(suPwd) && pwdConfVali(suPwdConf) && nameVali(name) }" @click="doRegister">회원가입</button>
            </div>
            <a class="modal__close">
               <i @click="controlModal(2, 'close')" class="far fa-times-circle"></i>
            </a>
         </div>
      </transition>
      <!--  로그인 모달  -->
      <transition name="slide-to-middle">
         <div v-if="dialog2" class="modal">
            <div class="modal__header">
               <img src="@/assets/image/NDCU_logo_eng.png" alt="">
            </div>
            <div class="modal__content">
               <div class="form-wrapper form-li">
                  <div class="input-wrapper">
                     <input v-model="liEmail" id="email-ip" type="email" placeholder="이메일" @focus="onFocused('email')" @blur="onBlured('email')" @keypress.enter="doLogin"/>
                     <div id="email-dv" class="divider"></div>
                     <span v-if="!emailVal" class="tip">이메일 형식이 아닙니다.</span>
                  </div>
                  <div class="input-wrapper">
                     <input v-model="liPwd" maxlength="10" id="pwd-ip" type="password" placeholder="비밀번호" @focus="onFocused('pwd')" @blur="onBlured('pwd')" @keypress.enter="doLogin"/>
                     <div id="pwd-dv" class="divider"></div>
                     <span v-if="!pwdVal1" class="tip">영문, 숫자 포함 6-10자 이내여야 합니다.</span>
                  </div>
               </div>
               <div class="account-wrapper">
                  <span class="pwd-forgotten">비밀번호를 잊으셨나요</span>
                  <div class="signup-wrapper">
                     <div class="left-wrapper">
                        계정이 없으신가요?
                     </div>
                     <div @click="[controlModal(1, 'close'), controlModal(2, 'open')]" class="right-wrapper">
                        가입하기
                     </div>
                  </div>
               </div>
            </div>
            <div class="modal__footer">
               <button class="login-bt" :class="{ vali: emailVali(liEmail) && pwdVali(liPwd) }" @click="doLogin">로그인</button>
            </div>
            <a class="modal__close">
               <i @click="controlModal(1, 'close')" class="far fa-times-circle"></i>
            </a>
         </div>
      </transition>
      <!-- about modal -->
      <transition name="slide-up">
         <div v-if="aboutDialog" class="about-modal">
            <div class="left-wrapper">
               <div class="l-top-wrapper">
                  <div class="name-wrapper">
                     <img class="about-logo" src="@/assets/image/NDCU_logo_icon.png/" alt="">
                     <span class="name">나두창업</span><span class="sub-name"> Now Do 창업</span>
                  </div><br>
                  <span class="description">FUZZY-C-MEANS 클러스터링 알고리즘을 기반으로<br> 후보 상권지를 추천하는 예비 창업자를 위한 서포터,<br> 나두창업입니다</span>
               </div>
               <div class="l-bottom-wrapper">
                  <span>본 사이트는 SSAFY 4기 핫식스팀에 의해 개발되었습니다.</span><br />
                  <span>Developed By. Team Hot6</span>
               </div>
            </div>
            <div class="right-wrapper">
               <div class="r-left-wrapper">
                  데이터 출처
               </div>
               <div class="r-right-wrapper">
                  <div class="site-wrapper">
                     <img src="@/assets/image/data-source/logo3.png" alt="" />
                     <a>서울신용보증재단</a>
                  </div>
                  <div class="site-wrapper">
                     <img src="@/assets/image/data-source/logo1.png" alt="" />
                     <a>서울 열린데이터 광장</a>
                  </div>
                  <div class="site-wrapper">
                     <img src="@/assets/image/data-source/logo2.png" alt="" />
                     <a>소상공인시장진흥공단</a>
                  </div>
               </div>
            </div>
            <i @click="aboutDialog = false" class="fas fa-times about-close"></i>
         </div>
      </transition>
   </div>
</template>

<script>
import * as EmailValidator from 'email-validator';
import { registerFounder, loginFounder } from '@/api/founder';
import { mapState, mapActions } from 'vuex';
//import router from '../router/router';

export default {
   name: 'SideMenu',
   data: function() {
      return {
         dialog2: false, // signup modal
         dialog: false, // login modal
         aboutDialog: false,
         emailVal: true,
         pwdVal1: true,
         pwdVal2: true,
         nameVal: true,
         telFocused: false,
         //signup value
         suEmail: '',
         suPwd: '',
         suPwdConf: '',
         name: '',
         tel: '',
         // login value
         liEmail: '',
         liPwd: '',
      };
   },
   computed: {
      ...mapState(['userInfo', 'isLogin']),      
   },
   methods: {
      // css 적용위한 Validation 함수들
      pwdVali: function(str) {
         const reg_pwd = /^.*(?=.{6,10})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
         if (reg_pwd.test(str)) {
            return true;
         }
         return false;
      },
      pwdConfVali: function(str) {
         if (str !== this.suPwd) {
            return false;
         } else return true;
      },
      emailVali: function(str) {
         if (EmailValidator.validate(str)) {
            return true;
         }
         return false;
      },
      nameVali: function(str) {
         const reg_name = /[~!@#$%^&*()_+|<>?:;{}]/ // 특수문자 test
         if (str === '' || reg_name.test(str)) {
            return false;
         } else return true
      },
      // 추천 조건 입력 창 열기
      onClickInputBt: function() {
         this.$emit('open-input-form');
      },
      // 즐겨찾기 탭 열기
      onClickBookmarkBt: function() {
         // 로그인 여부 확인
         if(this.isLogin) {
            this.$emit('open-bookmark');
         } else {
            alert('로그인이 필요한 서비스입니다.')
            this.controlModal(1, 'open');
         }         
      },
      // 회원가입,로그인 모달 컨트롤
      controlModal: function(num, con) {
         if (num === 1) {
            // 회원가입모달 대상이고
            if (con === 'open') {
               // 열라는 명령이면
               this.dialog2 = true; // 모달열기
               document.querySelector('.sm-container').classList.add('blur-display'); // 배경 블러적용
               this.$emit('dialog-change', this.dialog2); // MapSearch 배경 블러 적용 위해 emit
            } else {
               // 닫는 명령이면
               this.dialog2 = false; //모달닫기
               document.querySelector('.sm-container').classList.remove('blur-display');
               this.$emit('dialog-change', this.dialog2); // 배경들 블러 해제
               this.emailVal = true;
               this.pwdVal1 = true;
               this.pwdVal2 = true; // 다음에 모달 다시 열였을 때 올바른 css적용위해
               this.liEmail = '';
               this.liPwd = ''; // 입력값들 초기화
            }
         } else if (num === 2) {
            // 회원가입 모달 대상일때
            if (con === 'open') {
               this.dialog = true;
               document.querySelector('.sm-container').classList.add('blur-display');
               this.$emit('dialog-change', this.dialog);
            } else if (con === 'close') {
               this.dialog = false;
               document.querySelector('.sm-container').classList.remove('blur-display');
               this.$emit('dialog-change', this.dialog);
               this.emailVal = true;
               this.pwdVal1 = true;
               this.pwdVal2 = true;
               this.nameVal = true;
               this.suEmail = '';
               this.suPwd = '';
               this.suPwdConf = ''
               this.name = ''
               this.tel = '';
            }
         }
      },
      onFocused: function(type) {
         // 인풋창에 포커스됐을때 css적용위해
         const targetInput = document.getElementById(`${type}-dv`);
         targetInput.classList.remove('unval');
         targetInput.classList.add('divider');
         targetInput.classList.add('focused');
         if (type === 'tel') { // tel 적는 곳은 포커스 됐을 때만 hint보여주기
            this.telFocused = true
         }
      },
      onBlured: function(type) {
         // 인풋창에서 포키스아웃됐을 때 css 컨트롤위해
         const targetInput = document.getElementById(`${type}-ip`).value; // 회원가입과 로그인이 모달을 공유하기 때문에 데이터 충돌 방지하기 위해..
         const targetDiv = document.getElementById(`${type}-dv`); // divider 
         const reg_pwd = /^.*(?=.{6,10})(?=.*[0-9])(?=.*[a-zA-Z]).*$/; // 영문,숫자 포함 6 - 10자
         const reg_name = /[~!@#$%^&*()_+|<>?:;{}]/ // 별명 특수문자 포함되어있는지
         targetDiv.classList.remove('focused');
         targetDiv.classList.remove('divder'); // 기존에 적용된 css제거
         if (type === 'email') {
            if (!EmailValidator.validate(targetInput)) {
               // email 형식아니면
               targetDiv.classList.add('unval'); // 유효X css적용
               this.emailVal = false; //  유효하지 않음 처리
            } else {
               this.emailVal = true; // 조건 만족시 유효처리
            }
         } else if (type === 'pwd') {
            if (!reg_pwd.test(targetInput)) {
               targetDiv.classList.add('unval');
               this.pwdVal1 = false;
            } else {
               this.pwdVal1 = true;
            }
         } else if (type === 'pwdc') {
            if ((!reg_pwd.test(targetInput) && this.suPwd !== this.suPwdConf) || (!reg_pwd.test(targetInput) && !targetInput)) {
               targetDiv.classList.add('unval');
               this.pwdVal2 = false;
            } else {
               this.pwdVal2 = true;
            }
         } else if (type === 'name') {
            if (!this.name || reg_name.test(this.name)) {
               targetDiv.classList.add('unval')
               this.nameVal = false
            } else {
               this.nameVal = true
            }
         } else {
            this.telFocused = false
         }
      },

      // 회원가입 요청처리 메서드
      doRegister: function() {
         // 회원가입 요청 보내기
         if (this.emailVali(this.suEmail) && this.pwdVali(this.suPwd) && this.pwdConfVali(this.suPwdConf) && this.nameVali(this.name))
         registerFounder(
            {
               email: this.suEmail,
               password: this.suPwd,
               name: this.name,
               phonenumber: this.tel
            },
            () => {
               // 회원가입에 성공한 경우
               alert('성공적으로 회원가입되었습니다.')
               this.controlModal(2, 'close');
               this.controlModal(1, 'open');
            },
            (error) => {
               if(error.response.status == 409) {
                  alert('이미 가입되어 있는 이메일입니다');
               }
            }
         )       
      },

      // 로그인 요청처리 메서드
      doLogin: function() {
         // 로그인 요청 보내기
         if (this.emailVali(this.liEmail) && this.pwdVali(this.liPwd)) {
            loginFounder(
               {
                  email: this.liEmail,
                  password: this.liPwd,
               },
               (res) => {               
                  if (res.status == 200) {
                     let token = res.data['accessToken'];
                     localStorage.setItem('accessToken', token); // 토큰 로컬스토리지에 저장
                     this.$store.dispatch('getUserInfo'); // 토큰을 이용한 유저정보 가져오기
                     this.controlModal(1, 'close');
                     alert('로그인이 완료되었습니다.')
                  }        
               },
               (err) => {
                  if(err.response.status == 401) {
                     alert('아이디 또는 비밀번호가 일치하지 않습니다.');
                     this.isLoginError = true;
                  }               
               }
            ) 
         }
      },
      ...mapActions(["doLogout"])      
   },
   filters: {
      extractId: function (value) {
         const targetIdx = value.indexOf('@')
         return value.slice(0, targetIdx)
      }
   }
};
</script>
<style scoped lang="scss">
.sm-container {
   overflow: auto;
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
            width: 70%;
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
      .about {
         cursor: pointer;
         font-size: 15pt;
      }
      span {
         cursor: pointer;
      }
   }
}
.slide-up-enter-active,
.slide-up-leave-active {
   transition: all 0.2s ease;
}
.slide-up-enter,
.slide-up-leave-to {
   transform: translateY(200px);
}
.about-modal {
   border-radius: 30px 30px 0 0;
   position: fixed;
   width: 100vw;
   bottom: 0;
   height: 200px;
   padding: 10px 0;
   background-color: #284ac7bb;
   display: flex;
   justify-content: space-evenly;
   align-items: center;
   color: whitesmoke;
   .left-wrapper {
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      padding-left: 150px;
      height: 100%;
      width: 40%;
      .l-top-wrapper {
         .name-wrapper {
            display: flex;
            align-items: center;
            .about-logo {
               width: 30px;
            }
            .name {
               margin-left: 10px;
               font-size: 15pt;
               font-weight: 800;
            }
            .sub-name {
               font-size: 12pt;
               margin-left: 5px;
               color: rgba(255, 255, 255, 0.753)
            };
         }
         .description {
            font-size: 10pt;
            font-weight: 600;
            line-height: 24px;
         }
      }
      .l-bottom-wrapper {
         span {
            font-size: 9pt;
            font-weight: 100;
            color: rgba(255, 255, 255, 0.753)
         }
      }
   }
   .right-wrapper {
      padding-right: 150px;
      display: flex;
      width: 40%;
      height: 80px;
      justify-content: center;
      .r-left-wrapper {
         font-size: 13pt;
         font-weight: 600;
         margin-right: 30px;
      }
      .r-right-wrapper {
         height: 100%;
         display: flex;
         flex-direction: column;
         justify-content: space-between;
         .site-wrapper {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            img {
               width: 30px;
               height: 30px;
               margin-right: 10px;
            }
            a {
               font-size: 11pt;
               // cursor: pointer;
            }
            // a:hover {
            //   text-decoration: underline;
            // }
         }
      }
   }
   .about-close {
      position: absolute;
      top: 20px;
      right: 20px;
      cursor: pointer;
   }
}
.slide-to-middle-enter-active,
.slide-to-middle-leave-active {
   transition: all 0.3s ease;
}
.slide-to-middle-enter,
.slide-to-middle-leave-to {
   opacity: 0;
}
.modal {
   box-shadow: 9px 9px 20px #56565629;
   border-radius: 20px;
   background-color: white;
   width: 450px;
   height: 600px;
   display: flex;
   padding: 20px;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   position: fixed;
   top: 50%;
   left: 50%;
   transform: translate(-50%, -50%);
   .modal__header {
      height: 50%;
      width: 200px;
      img {
         width: 100%;
         height: 100%;
      }
   }
   .modal__content {
      height: 60%;
      width: 70%;
      padding: 0 20px 20px 20px;
      .form-li {
         height: 75%;
      }
      .form-su {
         height: 100%;
      }
      .form-wrapper {
         display: flex;
         flex-direction: column;
         align-items: center;
         justify-content: space-around;
         .ad-input-wrapper {
            display: flex;
            align-items: center;
            justify-content: space-between;
            .input-wrapper {
               width: 45%;
               input {
                  width: 100%;
                  height: 50px;
                  border: none;
               }
               input:focus {
                  outline: none;
               }
               .focused {
                  border-bottom: 2px rgb(57, 104, 235) solid;
                  border-radius: 5px;
                  animation: 0.2s ease-out 0s 1 focused;
                  @keyframes focused {
                     0% {
                        width: 0;
                     }
                     100% {
                        width: 100%;
                     }
                  }
               }
               .divider {
                  height: 1px;
                  width: 100%;
                  background-color: gray;
               }
               .tip {
                  margin-top: 5px;
                  font-size: 9pt;
                  color: rgb(221, 94, 94);
               }
               .tel-tip {
                  margin-top: 5px;
                  font-size: 9pt;
                  color: gray;
               }
               .unval {
                  height: 2px;
                  width: 100%;
                  background-color: rgb(221, 94, 94);
               }
            }
         }
         .input-wrapper {
            width: 100%;
            input {
               width: 100%;
               height: 50px;
               border: none;
            }
            input:focus {
               outline: none;
            }
            .focused {
               border-bottom: 2px rgb(57, 104, 235) solid;
               border-radius: 5px;
               animation: 0.2s ease-out 0s 1 focused;
               @keyframes focused {
                  0% {
                     width: 0;
                  }
                  100% {
                     width: 100%;
                  }
               }
            }
            .divider {
               height: 1px;
               width: 100%;
               background-color: gray;
            }
            .tip {
               margin-top: 5px;
               font-size: 9pt;
               color: rgb(221, 94, 94);
            }
            .unval {
               height: 2px;
               width: 100%;
               background-color: rgb(221, 94, 94);
            }
         }
      }
      .account-wrapper {
         width: 100%;
         height: 25%;
         display: flex;
         flex-direction: column;
         align-items: center;
         justify-content: space-around;
         .pwd-forgotten {
            font-weight: 800;
            cursor: pointer;
         }
         .signup-wrapper {
            width: 100%;
            display: flex;
            justify-content: space-between;
            .left-wrapper {
               color: gray;
            }
            .right-wrapper {
               color: #5082ff;
            }
            .right-wrapper:hover {
               text-decoration-line: underline;
               cursor: pointer;
            }
         }
      }
   }
   .modal__footer {
      height: 10%;

      .login-bt {
         width: 200px;
         height: 50px;
         border: none;
         color: white;
         border-radius: 30px;
         background: linear-gradient(to bottom right, #c2c2c2, #565656);
         pointer-events: none;
      }
      .login-bt:focus {
         outline: none;
      }
      .vali {
         background: linear-gradient(to bottom right, #7a9fff, #1d3caa);
         pointer-events: auto;
         box-shadow: 0 4px 4px lightgray;
         cursor: pointer;
      }
      .vali:hover {
         background: linear-gradient(to bottom right, #1d3caa, #7a9fff);
      }
   }
   .modal__close {
      position: inherit;
      top: 10px;
      right: 10px;
      i {
         cursor: pointer;
         font-size: 20pt;
         color: gray;
      }
   }
}
.blur-display > *:not(.modal) {
   filter: blur(5px);
   animation: 0.3s ease-out 0s 1 blur;
   pointer-events: none;
   @keyframes blur {
      0% {
         filter: blur(0);
      }
      100% {
         filter: blur(5px);
      }
   }
}
</style>
