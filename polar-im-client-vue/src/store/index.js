import Vue from 'vue' // 引入 Vue
import Vuex from 'vuex' // 引入 Vuex
import user from './modules/user'

import * as getters from './getters'
import * as actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)

// const debug = process.env.NODE_ENV !== 'production'
const now = new Date()
const store = new Vuex.Store({
  state: {
    showMessage: true,
    currentMenu: 0,
    disableSessionRightMenu: false,
    clickedFriend: false,
    clickedFriendId: 0,
    friend: {},
    queryUser: {},
    notFoundUser: false,
    foundUser: false,
    token: '',
    // 会话列表
    sessions: [
    ],
    friendList: [
    ],
    // 当前选中的会话
    currentSessionId: 1,
    // 过滤出只包含这个key的会话
    filterKey: '',
    // 对数据的全局存储'
    currentThreadID: null,
    threads: {
      /*
      id: {
        id,
        name,
        messages: [...ids],
        lastMessage
      }
      */
    },
    messages: {
      /*
      id: {
        id,
        threadId,
        threadName,
        authorName,
        text,
        timestamp,
        isRead
      }
      */
    }
  },
  getters,
  actions,
  mutations,
  modules: {
    user
  }
})
store.watch(
  (state) => state.sessions,
  (val) => {
    console.log('CHANGE: ', val)
    // console.log(JSON.stringify(val))
    // localStorage.setItem('vue-chat-session', JSON.stringify(val))
  },
  {
    deep: true
  }
)
export default store
