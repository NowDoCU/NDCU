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
         <span v-if="!isLogin" @click="controlModal(1, 'open')">로그인</span><br /><br />
         <span v-if="isLogin" @click="doLogout">로그아웃</span><br /><br />
         <span @click="controlModal(2, 'open')">회원가입</span>
      </div>

      <!-- 회원가입모달 -->
      <transition name="slide-to-middle">
         <div v-if="dialog" class="modal">
            <div class="modal__header">
               로고
            </div>
            <div class="modal__content">
               <div class="form-wrapper form-su">
                  <div class="input-wrapper">
                     <input v-model="suEmail" id="email-ip" type="email" placeholder="이메일" @focus="onFocused('email')" @blur="onBlured('email')" />
                     <div id="email-dv" class="divider"></div>
                     <span v-if="!emailVal" class="tip">이메일 형식이 아닙니다.</span>
                  </div>
                  <div class="input-wrapper">
                     <input v-model="suPwd" id="pwd-ip" type="password" placeholder="비밀번호" @focus="onFocused('pwd')" @blur="onBlured('pwd')" />
                     <div id="pwd-dv" class="divider"></div>
                     <span v-if="!pwdVal1" class="tip">영문, 숫자 포함 6-20자 이내여야 합니다.</span>
                  </div>
                  <div class="input-wrapper">
                     <input v-model="suPwdConf" id="pwdc-ip" type="password" placeholder="비밀번호 확인" @focus="onFocused('pwdc')" @blur="onBlured('pwdc')" />
                     <div id="pwdc-dv" class="divider"></div>
                     <span v-if="!pwdVal2" class="tip">비밀번호가 일치하지 않습니다.</span>
                  </div>
               </div>
            </div>
            <div class="modal__footer">
               <button class="login-bt" :class="{ vali: emailVali(suEmail) && pwdVali(suPwd) && pwdConfVali(suPwdConf) }" @click="doRegister">회원가입</button>
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
               로고
            </div>
            <div class="modal__content">
               <div class="form-wrapper form-li">
                  <div class="input-wrapper">
                     <input v-model="liEmail" id="email-ip" type="email" placeholder="이메일" @focus="onFocused('email')" @blur="onBlured('email')" />
                     <div id="email-dv" class="divider"></div>
                     <span v-if="!emailVal" class="tip">이메일 형식이 아닙니다.</span>
                  </div>
                  <div class="input-wrapper">
                     <input v-model="liPwd" id="pwd-ip" type="password" placeholder="비밀번호" @focus="onFocused('pwd')" @blur="onBlured('pwd')" />
                     <div id="pwd-dv" class="divider"></div>
                     <span v-if="!pwdVal1" class="tip">영문, 숫자 포함 6-20자 이내여야 합니다.</span>
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
                  <span class="name">세모창</span><br />
                  <span class="description">Lorem ipsum dolor sit amet consectetur adipisicing elit.</span>
               </div>
               <div class="l-bottom-wrapper">
                  <span>본 사이트의 저작권은 SSAFY 4기 핫식스팀에게 있습니다.</span><br />
                  <span>Copyright © Hot6 All Rights Reserved.</span>
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
         //signup value
         suEmail: '',
         suPwd: '',
         suPwdConf: '',
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
         const reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
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
      // 추천 조건 입력 창 열기
      onClickInputBt: function() {
         this.$emit('open-input-form');
      },
      // 즐겨찾기 탭 열기
      onClickBookmarkBt: function() {
         this.$emit('open-bookmark');
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
               this.suEmail = '';
               this.suPwd = '';
               this.suPwdConf = '';
            }
         }
      },
      onFocused: function(type) {
         // 인풋창에 포커스됐을때 css적용위해
         const targetInput = document.getElementById(`${type}-dv`);
         targetInput.classList.remove('unval');
         targetInput.classList.add('divider');
         targetInput.classList.add('focused');
      },
      onBlured: function(type) {
         // 인풋창에서 포키스아웃됐을 때 css 컨트롤위해
         const targetInput = document.getElementById(`${type}-ip`).value;
         const targetDiv = document.getElementById(`${type}-dv`);
         const reg_pwd = /^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
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
         } else {
            if ((!reg_pwd.test(targetInput) && this.suPwd !== targetInput) || (!reg_pwd.test(targetInput) && !targetInput)) {
               targetDiv.classList.add('unval');
               this.pwdVal2 = false;
            } else {
               this.pwdVal2 = true;
            }
         }
      },

      // 회원가입 요청처리 메서드
      doRegister: function() {
         // 회원가입 요청 보내기         
         registerFounder(
            {
               email: this.suEmail,
               password: this.suPwd,
            },
            () => {
               // 회원가입에 성공한 경우
               alert('성공적으로 회원가입되었습니다.')
               this.controlModal(2, 'close');
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
         loginFounder(
            {
               email: this.liEmail,
               password: this.liPwd,
            },
            (res) => {
               console.log(res);
               if (res.status == 200) {
                  let token = res.data['accessToken'];
                  localStorage.setItem('accessToken', token); // 토큰 로컬스토리지에 저장
                  this.$store.dispatch('getUserInfo'); // 토큰을 이용한 유저정보 가져오기
                  this.controlModal(1, 'close');
                  alert('로그인이 완료되었습니다.')
               } else {
                  alert('아이디 또는 비밀번호가 일치하지 않습니다.');
                  this.isLoginError = true;
               }               
            },
            (err) => {
               console.log(err);
            }
         ) 
      },
      ...mapActions(["doLogout"])      
   },
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
         .name {
            font-size: 18pt;
            font-weight: 800;
         }
         .description {
            font-size: 11pt;
            font-weight: 100;
         }
      }
      .l-bottom-wrapper {
         span {
            font-size: 10pt;
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
   background-color: white;
   width: 450px;
   height: 500px;
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
      height: 30%;
      width: 300px;
      background: rgb(57, 104, 235);
   }
   .modal__content {
      height: 60%;
      width: 70%;
      padding: 20px;
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
               font-size: 10pt;
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
