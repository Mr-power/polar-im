
<template>
  <div id="container">
    <div id="app">
      <div class="sidebar">
        <card></card>
        <list></list>
      </div>
      <div class="main">
        <communication v-show="showMessage"></communication>
        <userText v-show="showMessage"></userText>
        <userDetail v-show="!showMessage"></userDetail>
      </div>
    </div>
  </div>
</template>

<script>
import Card from './Card'
import List from './List'
import UserText from './UserText'
import Communication from './Message'
import UserDetail from './UserDetail'
import store from '@/store'
import { setNotFoundUser, setFoundUser, setQueryUser } from '@/store/actions'
import { mapGetters } from 'vuex'
export default {
  name: 'Test',
  components: {
    Card,
    List,
    Communication,
    UserText,
    UserDetail
  },
  beforeDestroy () {
    this.socket.close()
  },
  beforeCreate () {
  },
  methods: {
  },
  computed: {
    ...mapGetters({
      showMessage: 'showMessage',
      token: 'token'
    })
  },
  created () {
    let _this = this
    this.socket.getSocketUrl().then(res => {
      if (res.url) {
        _this.socket.init(res.url + '?token=' + this.token)
      }
    })
    // 在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem('store')) {
      store.replaceState(
        Object.assign(
          {},
          store.state,
          JSON.parse(sessionStorage.getItem('store'))
        )
      )
    }

    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    // beforeunload事件在页面刷新时先触发
    window.addEventListener('beforeunload', () => {
      setNotFoundUser(store, false)
      setFoundUser(store, false)
      setQueryUser(store, {})
      sessionStorage.setItem('store', JSON.stringify(store.state))
    })
    // window.removeEventListener('keydown')
  },

}
</script>

<style lang="less">
#container {
  color: #4d4d4d;
  -webkit-font-smoothing: antialiased;
  background: url('../../assets/bg.jpg') no-repeat center;
  background-size: 100% 100%;
  height: 100%;
  width: 100%;
  position: fixed;
  overflow: hidden;
  ul {
    list-style: none;
    margin: 0;
    padding: 0;
  }
  #app {
    margin: 20px auto;
    width: 800px;
    height: 600px;
    // background: url('../assets/bg.jpg') no-repeat center;
    // background-size: cover;
    overflow: hidden;
    border-radius: 3px;

    .sidebar,
    .main {
      height: 100%;
    }
    .sidebar {
      float: left;
      width: 200px;
      color: #f4f4f4;
      background-color: #2e3238;
    }
    .main {
      position: relative;
      overflow: hidden;
      background-color: #eee;
    }
    .text {
      // position: absolute;
      // width: 100%;
      bottom: 0;
      left: 0;
    }
    .communication {
      height: ~'calc(100% - 160px)';
    }
    .detail {
      height: ~'calc(100%)';
    }
  }
}
</style>
