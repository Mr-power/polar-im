<template>
  <div class="communication">
    <div class="message-title">
      <p>{{session.user.username}}</p>
    </div>
    <div class="message" v-scroll-bottom="session.messages">
      <ul v-if="session">
        <li v-for="item in session.messages">
          <p class="time">
            <span>{{ item.timeStamp | time }}</span>
          </p>
          <div class="main" :class="{ self: item.self }">
            <img
              class="avatar"
              width="30"
              height="30"
              :src="item.self ? user.avatar : session.user.avatar"
            />
            <div class="text">{{ item.content }}</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  computed: mapGetters({
    user: 'user',
    session: 'getSessionById'
  }),
  directives: {/*这个是vue的自定义指令,官方文档有详细说明*/
    // 发送消息后滚动到底部,这里无法使用原作者的方法，也未找到合理的方法解决，暂用setTimeout的方法模拟
    'scroll-bottom' (el) {
      //console.log(el.scrollTop);
      setTimeout(function () {
        el.scrollTop += 9999
      }, 1)
    }
  }
};
</script>

<style lang="less" scoped>
.communication {
  .message-title {
    border-bottom: 1px solid #d6d6d6;
    text-align: center;
    p {
      font-weight: 400;
      display: inline-block;
      text-align: center;
      font-size: 16px;
      color: #666;
    }
  }
  .message {
    height: ~'calc(100% - 53px)';
    padding: 10px 15px;
    overflow-y: scroll;
    li {
      margin-bottom: 15px;
    }
    .time {
      margin: 7px 0;
      text-align: center;
      > span {
        display: inline-block;
        padding: 0 18px;
        font-size: 12px;
        color: #fff;
        border-radius: 2px;
        background-color: #dcdcdc;
      }
    }
    .avatar {
      float: left;
      margin: 0 10px 0 0;
      border-radius: 3px;
    }
    .text {
      display: inline-block;
      position: relative;
      padding: 0 10px;
      max-width: ~'calc(100% - 40px)';
      min-height: 30px;
      line-height: 2.5;
      font-size: 12px;
      text-align: left;
      word-break: break-all;
      background-color: #fafafa;
      border-radius: 4px;
      &:before {
        content: ' ';
        position: absolute;
        top: 9px;
        right: 100%;
        border: 6px solid transparent;
        border-right-color: #fafafa;
      }
    }

    .self {
      text-align: right;
      .avatar {
        float: right;
        margin: 0 0 0 10px;
      }
      .text {
        background-color: #b2e281;
        &:before {
          right: inherit;
          left: 100%;
          border-right-color: transparent;
          border-left-color: #b2e281;
        }
      }
    }
  }
}
</style>
