<template>
  <div class="detail">
    <div class="detail-title">
      <p>详细信息</p>
    </div>
    <div v-show="ifClickedFriend">
      <div class="profile">
        <div class="avatar">
          <img class="img" :src="friend.avatar" />
        </div>
        <div class="username">{{friend.username}}</div>
      </div>
      <div class="action">
        <div class="button" @click="startChat()">发消息</div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { setCurrentMenu, showMessage, setDisableSessionRightMenu } from '@/store/actions'
import { startSession } from '@/api/session'
import store from '@/store'
export default {
  computed: mapGetters({
    ifClickedFriend: 'clickedFriend',
    friend: 'friend',
    clickedFriendId: 'clickedFriendId'
  }),
  methods: {
    startChat () {
      showMessage(store, true)
      startSession(this.clickedFriendId)
      setCurrentMenu(store, 0)
      setDisableSessionRightMenu(store, false)
    }
  }
};
</script>

<style lang="less" scoped>
.detail {
  .detail-title {
    border-bottom: 1px solid #d6d6d6;
    text-align: center;
    line-height: 30px;
    p {
      font-weight: 400;
      display: inline-block;
      text-align: center;
      font-size: 14px;
      color: #666;
    }
  }
  .profile {
    height: ~'calc(100% - 400px)';
    padding: 10px 15px;

    text-align: center;
    .avatar {
      margin-top: 30px;
      margin-bottom: 22px;
      .img {
        display: block;
        width: 100px;
        height: 100px;
        margin: 0 auto;
        border-radius: 4px;
        -moz-border-radius: 4px;
        -webkit-border-radius: 4px;
      }
    }
    .username {
      font-weight: 400;
      font-size: 24px;
      margin-bottom: 10px;
      display: inline-block;
    }
  }
  .action {
    display: inline-block;
    position: absolute;
    // height: ~'calc(100% - 400px)';
    // padding: 10px 15px;
    .button {
      display: inline-block;
      position: absolute;
      margin-top: 50px;
      margin-left: 200px;
      width: 200px;
      text-align: center;
      color: #fff;
      line-height: 40px;
      background-color: #42ac3e;
      font-size: 14px;
      text-decoration: none;
      border-radius: 4px;
      -moz-border-radius: 4px;
      -webkit-border-radius: 4px;
    }
  }
}
</style>
