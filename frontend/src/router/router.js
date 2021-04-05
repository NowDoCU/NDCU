import Vue from 'vue';
import VueRouter from 'vue-router';
import Main from '../views/MapSearch.vue'

Vue.use(VueRouter);

// const onlyAuthUser = (to, from, next) => {
//     if (localStorage.getItem('accessToken') !== null) {
//       next();
//     } else {
//       alert('로그인이 필요한 페이지입니다. ');
//       next('/');
//     }
//   };

const routes = [{
    path: '/',
    name: 'Main',    
    components: {      
      default: Main,
    },
  },
];

const router = new VueRouter({
  mode: 'history', //뒤에 # 을 없애줌
  // base: process.env.BASE_URL,
  routes,
});

export default router;