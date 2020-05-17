import { postRequest, getRequest } from './httpUtil'
import { setClickedFriendId, setClickedFriend, setFriend, updateCurrentUser } from '@/store/actions'
import store from '@/store'

export const updateCurrentUserInfo = (username, avatar) => {
  const params = { username, avatar }
  return postRequest('/user/update', params)
    .then(res => {
      updateCurrentUser(store, username, avatar)
      return res
    })
}

export const changePassword = (oldPassword, newPassword) => {
  const params = { oldPassword, newPassword }
  return postRequest('/user/password', params)
    .then(res => {
      return res
    })
}

export const queryFriendInfo = (friendId) => {
  const params = { friendId }
  return getRequest('/user/friend', params)
    .then(res => {
      if (res.data.success) {
        setClickedFriendId(store, friendId)
        setClickedFriend(store, true)
        setFriend(store, res.data.friend)
      }
    })
}

export const findUserByAccount = (account) => {
  const params = { account }
  return getRequest('/user/account', params)
    .then(res => {
      return res
    })
}

export const addFriend = (friendId) => {
  const params = { friendId }
  return postRequest('/user/addFriend', params)
    .then(res => {
      return res
    })
}
