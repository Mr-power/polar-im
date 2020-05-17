<template>
  <div class="listbar">
    <v-contextmenu
      ref="sessionMenu"
      @contextmenu="menuRightChange"
      :disabled="disableSessionRightMenu"
    >
      <v-contextmenu-item @click="handleCloseSession">关闭聊天</v-contextmenu-item>
    </v-contextmenu>
    <ul class="m-tab">
      <li
        v-for="item in tabMenus"
        :key="item.id"
        :class="{ current: item.id === currentMenu }"
        @click="selectMenu(item.id)"
      >
        <i class="iconfont" :class="item.icon"></i>
      </li>
    </ul>
    <ul class="sessionList" v-if="currentMenu==0">
      <li
        v-for="item in sessions"
        :key="item.id"
        :class="{ active: item.id === currentId }"
        @click="changeSession(item.id)"
        v-contextmenu:sessionMenu
      >
        <img class="avatar" :alt="item.user.username" :src="item.user.avatar" />
        <p class="name">{{item.user.username}}</p>
      </li>
    </ul>
    <ul class="friendList" v-else-if="currentMenu==1">
      <li
        v-for="friend in friendList"
        :key="friend.id"
        @click="selectFriend(friend.id)"
        @contextmenu.prevent.stop
      >
        <img class="img lazy" :alt="friend.username" :src="friend.avatar" />
        <p class="friendname">{{friend.username}}</p>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import { changeSession, closeSession, getUserSessions } from '@/api/session'
import { queryFriendInfo } from '@/api/user'
import { selectSession, showMessage, setCurrentMenu, setClickedFriendId, setDisableSessionRightMenu } from '@/store/actions'
import store from '@/store'
export default {

  data () {
    return {

      rightSessionId: 0,
      tabMenus: [
        { "id": 0, "icon": "icon-liaotian" }, { "id": 1, "icon": "icon-tongxunlu" }
      ]
    }
  },
  components: {
  },
  computed: {
    ...mapGetters({
      sessions: 'sessions',
      friendList: 'friends',
      currentId: 'getCurrentId',
      currentMenu: 'currentMenu',
      disableSessionRightMenu: 'disableSessionRightMenu',
    }),
  }
  ,
  methods: {
    menuRightChange (ins) {
      this.rightSessionId = ins.data.key
    },
    handleCloseSession (vm, event) {
      console.log('关闭会话' + this.rightSessionId)
      closeSession(this.rightSessionId)
        .then(res => {
          if (res.data.success) {
            getUserSessions()
          }
        })
    },
    selectMenu (id) {
      showMessage(store, id === 0)
      setCurrentMenu(store, id)
      setDisableSessionRightMenu(store, id === 1)
    },
    changeSession (id) {
      changeSession(id)
        .then(res => {
          selectSession(store, id)
        })
    },
    selectFriend (id) {
      setClickedFriendId(store, id)
      queryFriendInfo(id)
    }
  },
};
</script>

<style scoped lang="less">
.listbar {
  .m-tab {
    &:extend(.pd);
    padding-top: 0;
    color: #fff;
    padding-bottom: 5px;
    display: flex;
    .current {
      color: #3caf36;
    }
    li {
      width: 50%;
      text-align: center;
      cursor: pointer;
      position: relative;
      i {
        font-size: 25px;
        line-height: 30px;
      }
    }
    li:after {
      content: '';
      position: absolute;
      top: 5px;
      right: 0;
      width: 0;
      height: 20px;
      border-right: 1px solid #24272c;
    }
    li:last-child:after {
      visibility: hidden;
    }
  }
  .friendList {
    li {
      padding: 12px 15px;
      border-bottom: 1px solid #292c33;
      cursor: pointer;
      transition: background-color 0.1s;

      &:hover {
        background-color: rgba(255, 255, 255, 0.03);
      }
      &.active {
        background-color: rgba(255, 255, 255, 0.1);
      }
    }
    .img,
    .friendname {
      vertical-align: middle;
    }
    .img {
      width: 20px;
      height: 20px;
      border-radius: 2px;
      -moz-border-radius: 2px;
      -webkit-border-radius: 2px;
    }
    .friendname {
      display: inline-block;
      margin: 0 0 0 15px;
      color: #fff;
      font-weight: 400;
      font-size: 13px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      word-wrap: normal;
    }
  }
  .sessionList {
    li {
      padding: 12px 15px;
      border-bottom: 1px solid #292c33;
      cursor: pointer;
      transition: background-color 0.1s;

      &:hover {
        background-color: rgba(255, 255, 255, 0.03);
      }
      &.active {
        background-color: rgba(255, 255, 255, 0.1);
      }
    }
    .avatar,
    .name {
      vertical-align: middle;
    }
    .avatar {
      width: 30px;
      height: 30px;
      border-radius: 2px;
    }
    .name {
      display: inline-block;
      margin: 0 0 0 15px;
    }
  }
}
</style>
