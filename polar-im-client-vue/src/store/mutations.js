import Vue from 'vue'
import * as socket from '@/api/socket'
export default {
  receiveAll (state, messages) {
    let latestMessage
    messages.forEach(message => {
      // create new thread if the thread doesn't exist
      if (!state.threads[message.threadID]) {
        createThread(state, message.threadID, message.threadName)
      }
      // mark the latest message
      if (!latestMessage || message.timestamp > latestMessage.timestamp) {
        latestMessage = message
      }
      // add message
      addMessage(state, message)
    })
    // set initial thread to the one with the latest message
    setCurrentThread(state, latestMessage.threadID)
  },
  SEND_MESSAGE ({ sessions, currentSessionId, user }, content) {
    SEND_MESSAGE({ sessions, currentSessionId, user }, content)
  },
  incommingMessage ({ sessions, currentSessionId, user }, content) {
    incommingMessage({ sessions, currentSessionId, user }, content)
  },
  receiveMessage (state, message) {
    addMessage(state, message)
  },

  switchThread (state, id) {
    setCurrentThread(state, id)
  },

  setToken (state, token) {
    // 对数据的同步更改
    setToken(state, token)
  },

  updateUserInfo (state, user) {
    updateUser(state, user)
  },

  setCurrentUser (state, username, avatar) {
    // 对数据的同步更改
    setCurrentUser(state, username, avatar)
  },
  removeToken (state) {
    removeToken(state)
  },
  selectSession (state, id) {
    SELECT_SESSION(state, id)
  },
  SET_FILTER_KEY (state, value) {
    SET_FILTER_KEY(state, value)
  },
  // 对数据的同步更改
  setSessions (state, sessions) {
    state.sessions = sessions
  },
  setFriendList (state, friendList) {
    state.friendList = friendList
  },
  setCurrentMenu (state, currentMenu) {
    state.currentMenu = currentMenu
    // 如果切换到会话列表 那么隐藏好友信息
    if (currentMenu === 0) {
      state.clickedFriend = false
      state.clickedFriendId = 0
      state.friend = {}
    }
  },
  setCurrentSessionId (state, currentId) {
    state.currentSessionId = currentId
  },
  showMessage (state, showMessage) {
    state.showMessage = showMessage
  },
  setClickedFriendId (state, clickedFriendId) {
    state.clickedFriendId = clickedFriendId
  },
  setFriend (state, friend) {
    state.friend = friend
  },
  setDisableSessionRightMenu (state, disableSessionRightMenu) {
    state.disableSessionRightMenu = disableSessionRightMenu
  },
  setClickedFriend (state, clickedFriend) {
    state.clickedFriend = clickedFriend
  },
  setNotFoundUser (state, notFoundUser) {
    state.notFoundUser = notFoundUser
  },
  setFoundUser (state, foundUser) {
    state.foundUser = foundUser
  },
  setQueryUser (state, queryUser) {
    state.queryUser = queryUser
  }
}

function createThread (state, id, name) {
  Vue.set(state.threads, id, {
    id,
    name,
    messages: [],
    lastMessage: null
  })
}

function addMessage (state, message) {
  // add a `isRead` field before adding the message
  message.isRead = message.threadID === state.currentThreadID
  // add it to the thread it belongs to
  const thread = state.threads[message.threadID]
  if (!thread.messages.some(id => id === message.id)) {
    thread.messages.push(message.id)
    thread.lastMessage = message
  }
  // add it to the messages map
  Vue.set(state.messages, message.id, message)
}

function setCurrentThread (state, id) {
  state.currentThreadID = id
  if (!state.threads[id]) {
    debugger
  }
  // mark thread as read
  state.threads[id].lastMessage.isRead = true
}

// 对数据的同步更改
function setToken (state, token) {
  state.token = token
  window.sessionStorage.setItem('token', token)
}

function updateUser (state, username, avatar) {
  state.user.username = username
  state.user.avatar = avatar
}

function setCurrentUser (state, user) {
  state.user = user
}

function removeToken (state) {
  state.user.token = ''
  window.sessionStorage.setItem('token', '')
}

function incommingMessage ({ sessions, currentSessionId, user }, content) {
  console.log(content)
  var messageSessionId = content.sessionId
  const session = sessions.find(item => item.id === messageSessionId)
  session.messages.push({
    content: content.content,
    timeStamp: new Date(),
    self: false
  })
}

function SEND_MESSAGE ({ sessions, currentSessionId, user }, content) {
  const session = sessions.find(item => item.id === currentSessionId)
  session.messages.push({
    content: content,
    timeStamp: new Date(),
    self: true
  })
}

function SELECT_SESSION (state, id) {
  state.currentSessionId = id
}

function SET_FILTER_KEY (state, value) {
  state.filterKey = value
}
