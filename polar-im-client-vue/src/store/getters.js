export const threads = state => state.threads
export const token = state => state.token ? state.token : window.sessionStorage.getItem('token')
export const user = state => state.user
export const showMessage = state => state.showMessage
export const currentMenu = state => state.currentMenu
export const clickedFriend = state => state.clickedFriend
export const clickedFriendId = state => state.clickedFriendId
export const setFriend = state => state.setFriend
export const friend = state => state.friend
export const disableSessionRightMenu = state => state.disableSessionRightMenu
export const queryUser = state => state.queryUser
export const notFoundUser = state => state.notFoundUser
export const foundUser = state => state.foundUser

export const sessions = state => {
  return state.sessions.filter(session => session.user.username.includes(state.filterKey))
}

export const friends = state => {
  return state.friendList.filter(friend => friend.username.includes(state.filterKey))
}

export const getSessions = state => {
  return state.sessions
}

export const getFriendList = state => {
  return state.friendList
}

export const getSessionById = state => {
  return state.sessions.find(session => session.id === state.currentSessionId)
}

export const getCurrentId = state => {
  return state.currentSessionId
}

export const currentThread = state => {
  return state.currentThreadID
    ? state.threads[state.currentThreadID]
    : {}
}

export const currentMessages = state => {
  const thread = currentThread(state)
  return thread.messages
    ? thread.messages.map(id => state.messages[id])
    : []
}

export const unreadCount = ({ threads }) => {
  return Object.keys(threads).reduce((count, id) => {
    return threads[id].lastMessage.isRead ? count : count + 1
  }, 0)
}

export const sortedMessages = (state, getters) => {
  const messages = getters.currentMessages
  return messages.slice().sort((a, b) => a.timestamp - b.timestamp)
}
