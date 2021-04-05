import Vue from 'vue';
import Vuex from 'vuex';
import router from '../router/router'
import jwt_decode from 'jwt-decode';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    // 컴포넌트 간에 공유할 data
    isLogin: false, // 로그인 여부
    userInfo: null,    
  },
  mutations: {
    // state의 변화를 일으키는 곳, state 조작은 여기서만 하길 권장

    //로그인 성공 시
    loginSuccess(state, payload) {
      state.isLogin = true;
      state.userInfo = payload;
    },

    // 로그인이 실패했을 때
    loginError(state) {
      state.isLogin = false;
      state.isLoginError = true;
    },

    //로그아웃 시
    logout(state) {
      localStorage.removeItem('accessToken');
      // localStorage.clear;
      state.isLogin = false;
      state.userInfo = null;
    },
  },
  actions: {
    doLogout({
      commit
    }) {
      commit('logout');
      alert('정상적으로 로그아웃 되었습니다')
      localStorage.removeItem('accessToken');
      router.push({path: '/'})
      // axios.defaults.headers.common["auth-token"] = undefined;
    },

    getUserInfo({
      commit
    }) {
      let token = localStorage.getItem('accessToken');

      if (token != null) {
        let decode = jwt_decode(token);
        let userInfo = {
          id: decode.id,
          name: decode.name,
          email: decode.email,
        };

        commit('loginSuccess', userInfo);
      }
    },

    expired({
      commit
    }) {
      alert('세션이 만료되었습니다.')
      commit('logout');
    //   router.push({
    //     name: 'Landing'
    //   })
    },
  },
});

export default store