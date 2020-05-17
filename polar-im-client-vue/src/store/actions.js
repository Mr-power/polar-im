import * as api from '../api'
import user from './modules/user'

export const getAllMessages = ({ commit }) => {
  api.getAllMessages(messages => {
    commit('receiveAll', messages)
  })
}

export const sendMessage = ({ commit }, payload) => {
  commit('SEND_MESSAGE', payload)
}

export const incommingMessage = ({ commit }, payload) => {
  commit('incommingMessage', payload)
}

export const switchThread = ({ commit }, payload) => {
  commit('switchThread', payload)
}

export const setToken = ({ commit }, token) => {
  commit('setToken', token)
}

export const setCurrentUser = ({ commit }, id, username, avatar) => {
  user.id = id
  user.username = username
  user.avatar = avatar
  commit('setCurrentUser', user)
}

export const updateCurrentUser = ({ commit }, username, avatar) => {
  commit('updateUserInfo', username, avatar)
}

export const setCurrentSessionId = ({ commit }, currentId) => {
  commit('setCurrentSessionId', currentId)
}

export const setSessions = ({ commit }, sessions) => {
  commit('setSessions', sessions)
}

export const setFriendList = ({ commit }, friendList) => {
  commit('setFriendList', friendList)
}

export const showMessage = ({ commit }, showMessage) => {
  commit('showMessage', showMessage)
}

export const setClickedFriendId = ({ commit }, clickedFriendId) => {
  commit('setClickedFriendId', clickedFriendId)
}

export const setFriend = ({ commit }, friend) => {
  commit('setFriend', friend)
}

export const setCurrentMenu = ({ commit }, currentMenu) => {
  commit('setCurrentMenu', currentMenu)
}

export const setClickedFriend = ({ commit }, clickedFriend) => {
  commit('setClickedFriend', clickedFriend)
}

export const initData = ({ commit }) => {
  commit('INIT_DATA')
}

export const selectSession = ({ commit }, id) => {
  commit('selectSession', id)
}

export const search = ({ commit }, value) => {
  commit('SET_FILTER_KEY', value)
}

export const setDisableSessionRightMenu = ({ commit }, value) => {
  commit('setDisableSessionRightMenu', value)
}

export const setQueryUser = ({ commit }, value) => {
  commit('setQueryUser', value)
}

export const setNotFoundUser = ({ commit }, value) => {
  commit('setNotFoundUser', value)
}

export const setFoundUser = ({ commit }, value) => {
  commit('setFoundUser', value)
}
