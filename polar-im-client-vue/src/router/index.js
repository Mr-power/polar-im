import Vue from 'vue'
import Router from 'vue-router'
import Test from '@/components/test/Test'
import EnterPage from '@/components/EnterPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'EnterPage',
      component: EnterPage
    },
    {
      path: '/test',
      name: 'Test',
      component: Test,
      meta: {
        requireSession: true
      }
    }
  ]
})
