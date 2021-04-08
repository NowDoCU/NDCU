import Vue from 'vue';
import VueRouter from 'vue-router';
import Main from '../views/MapSearch.vue'

Vue.use(VueRouter);

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