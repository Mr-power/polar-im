import { getRequest } from './httpUtil'
import { incommingMessage } from '@/store/actions'
import store from '@/store'
var websock = null
var globalCallback = null

function getSocketUrl () {
  return new Promise((resolve, reject) => {
    getRequest('/socket/getSocketUrl')
      .then(res => {
        resolve(res.data)
      }).catch(err => {
        reject(err.data)
      })
  })
}

// 初始化weosocket
function init (uri) {
  if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket
  }
  // ws地址 -->这里是你的请求路径
  websock = new window.WebSocket(uri)
  websock.onmessage = function (e) {
    websocketonmessage(e)
  }
  websock.onclose = function (e) {
    console.log(e)
    console.log('断开连接')
    // close(e)
  }
  websock.onopen = function () {
    websocketOpen()
  }

  // 连接发生错误的回调方法
  websock.onerror = function () {
    console.log('WebSocket连接发生错误')
  }
}

// 实际调用的方法
function send (agentData, callback) {
  globalCallback = callback
  if (websock.readyState === websock.OPEN) {
    // 若是ws开启状态
    websocketsend(agentData)
  } else if (websock.readyState === websock.CONNECTING) {
    // 若是 正在开启状态，则等待1s后重新调用
    setTimeout(function () {
      send(agentData, callback)
    }, 1000)
  } else {
    init()
    // 若未开启 ，则等待1s后重新调用
    setTimeout(function () {
      send(agentData, callback)
    }, 1000)
  }
}

// 数据接收
function websocketonmessage (e) {
  // if (globalCallback !== null) {
  // globalCallback(JSON.parse(e.data))
  // } else {
  incommingMessage(store, JSON.parse(e.data))
  // }
}

// 数据发送
function websocketsend (agentData) {
  websock.send(JSON.stringify(agentData))
}

// 关闭
function close () {
  websock.close()

}

// 创建 websocket 连接
function websocketOpen (e) {
  console.log('连接成功')
}
// getSocketUrl()
// initWebSocket()
// 将方法暴露出去
export { getSocketUrl, init, send, close }
