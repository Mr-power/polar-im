import { getRequest, postRequest } from './httpUtil'
import store from '@/store'
import { setToken, setCurrentUser, setSessions, setFriendList, setCurrentSessionId, setCurrentMenu, showMessage } from '@/store/actions'

export const checkAccount = (account) => {
  const params = { account }
  return getRequest('/auth/check', params)
    .then(res => {
      return res
    })
}
export const registerUser = (username, account, password) => {
  const params = { username, account, password }
  return postRequest('/auth/register', params)
    .then(res => {
      return res
    })
}

export const login = (account, password) => {
  const params = { account, password }
  return postRequest('/auth/login', params)
    .then(res => {
      // 清除以前的 sessionStorage
      window.sessionStorage.clear()
      const data = res.data
      // 更新token
      setToken(store, data.token)
      // 存储用户信息
      setCurrentUser(store, data.id, data.username, data.avatar)
      // 调用接口获取 session 之类的信息
      // getUserSessions(store.getters.user.id)
      // 获取 session
      setSessions(store, data.sessions)
      // 获取 好友列表
      setFriendList(store, data.friendList)
      // 当前选中的session
      setCurrentSessionId(store, data.currentSessionId)
      setCurrentMenu(store, 0)
      showMessage(store, true)
      return res
    })
}
