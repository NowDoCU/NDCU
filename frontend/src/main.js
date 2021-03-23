import Vue from 'vue'
import App from './App.vue'
import VoerroTagsInput from '@voerro/vue-tagsinput'

Vue.config.productionTip = false

Vue.component('tags-input', VoerroTagsInput)

new Vue({
  render: h => h(App),
}).$mount('#app')
