import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import { store } from './store'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false
axios.defaults.baseURL = 'http://www.omdbapi.com/?apikey=f4e8b714&page=1&type=movie&Content-Type=application/json'
new Vue({
  render: h => h(App),
  store,
  router
}).$mount('#app')
