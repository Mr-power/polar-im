import { postRequest, getRequest } from './httpUtil'
import store from '@/store'
import { setSessions, setCurrentSessionId } from '@/store/actions'

export const changeSession = (sessionId) => {
  const params = { sessionId }
  return postRequest('/session/changeSession', params)
    .then(res => {
      return res
    })
}
export const getUserSessions = () => {
  return getRequest('/session/getSession')
    .then(res => {
      const data = res.data
      // 获取 session
      setSessions(store, data.sessions)
      // 当前选中的session
      setCurrentSessionId(store, data.currentSessionId)
      return res
    })
}

export const startSession = (toUserId) => {
  const params = { toUserId }
  return postRequest('/session/startSession', params)
    .then(res => {
      const data = res.data
      // 获取 session
      setSessions(store, data.sessions)
      // 当前选中的session
      setCurrentSessionId(store, data.currentSessionId)
      return res
    })
}

export const closeSession = (sessionId) => {
  const params = { sessionId }
  return postRequest('/session/closeSession', params)
    .then(res => {
      // const data = res.data
      // 获取 session
      // setSessions(store, data.sessions)
      // 当前选中的session
      // setCurrentSessionId(store, data.currentSessionId)
      return res
    })
}
