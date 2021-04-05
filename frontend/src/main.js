import Vue from 'vue'
import VueSlideBar from 'vue-slide-bar'
import App from './App.vue'

Vue.config.productionTip = false

Vue.component('VueSlideBar', VueSlideBar)

new Vue({
  render: h => h(App),
}).$mount('#app')
