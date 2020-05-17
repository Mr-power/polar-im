// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
// polyfill
import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import store from './store'
// WebSocket封装方法
import * as socket from './api/socket'
import contentmenu from 'v-contextmenu'
import 'v-contextmenu/dist/index.css'

Vue.use(contentmenu)

Vue.prototype.socket = socket

Vue.config.productionTip = false
// 引入 elementui
Vue.use(ElementUI)
// 引入 axios 和 vue-axios
// Vue.use(axios)
Vue.prototype.$http = axios
// 定义一个 time filter
Vue.filter('time', timestamp => {

  const dateTime = new Date(timestamp)
  const YY = dateTime.getFullYear()
  const MM =
    dateTime.getMonth() + 1 < 10
      ? '0' + (dateTime.getMonth() + 1)
      : dateTime.getMonth() + 1
  const D =
    dateTime.getDate() < 10 ? '0' + dateTime.getDate() : dateTime.getDate()
  const hh =
    dateTime.getHours() < 10
      ? '0' + dateTime.getHours()
      : dateTime.getHours()
  const mm =
    dateTime.getMinutes() < 10
      ? '0' + dateTime.getMinutes()
      : dateTime.getMinutes()
  const ss =
    dateTime.getSeconds() < 10
      ? '0' + dateTime.getSeconds()
      : dateTime.getSeconds()
  return `${YY}-${MM}-${D} ${hh}:${mm}`

  // return new Date(timestamp).toLocaleString()
})

// 定义一个 hour filter
Vue.filter('hour', date => {
  if (typeof date === 'string') {
    date = new Date(date)
  }
  return date.getHours() + ':' + date.getMinutes()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

// window.onbeforeunload = function () {
//   socket.close()
// }

// router跳转之前的动作
// router.beforeEach((to, from, next) => {
//   if (to.meta.requireSession) {
//     if (store.getters.getSessions === undefined) {
//     }
//     next()
//   } else {
//     next()
//   }
// })
