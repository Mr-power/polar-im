<script>
import { mapActions, mapGetters } from 'vuex'
import store from '@/store'
export default {
  data () {
    return {
      content: ''
    }
  },
  created () {

  },
  // computed: mapGetters(['getCurrentId']),
  computed: mapGetters({
    currentId: 'getCurrentId'
  }),
  methods: {
    ...mapActions([
      'sendMessage'
    ]),
    incommingMessage(message){
      console.log(message)
    },
    send (e) {
      e.preventDefault() // 阻止浏览器默认换行操作
      var msg = this.content.replace('\n', '')
      if (msg != '') {
        this.sendMessage(msg)
        var sessionId = store.getters.getCurrentId
        var content = msg
        const params = { sessionId, content }
        // 发送 websocket 消息
        let _this = this
        this.socket.send(params, function (res) {
          console.log(res)
        })
      } else {
        console.log('不能发送空白消息')
      }
      this.content = ''
    }
  }
};
</script>

<template>
  <div class="text">
    <ul class="tool-list">
      <li>
        <i class="iconfont icon-face"></i>
      </li>
    </ul>
    <div class="text-area">
      <textarea
        id="area"
        placeholder="press Enter to send"
        v-model="content"
        v-on:keydown.enter="send"
      ></textarea>
    </div>
    <div class="text-footer">
      <button v-on:click="send">发送</button>
    </div>
  </div>
</template>

<style lang="less" scoped>
.text {
  height: 160px;
  border-top: solid 1px #ddd;
  .tool-list {
    border: none;
    background-color: #fff;
    padding: 5px 17px;
    display: flex;
    li {
      height: 30px;
      width: 30px;
      line-height: 30px;
      margin-right: 10px;
      text-align: center;
      i {
        cursor: pointer;
        font-size: 22px;
      }
    }
  }
  .text-area {
    height: 90px;
    textarea {
      padding: 10px;
      height: 100%;
      width: 100%;
      border: none;
      outline: none;
      font-family: 'Micrsofot Yahei';
      resize: none;
    }
  }
  .text-footer {
    display: flex;
    padding: 10px 20px;
    background: #fff;
    justify-content: flex-end;
    align-items: flex-end;
    button {
      background: #fff;
      padding: 3px 20px;
      color: #222;
      border: 1px solid #c1c1c1;
      border-radius: 3px;
    }
    span {
      font-size: 14px;
      color: #999;
      margin-right: 10px;
    }
  }
}
</style>
