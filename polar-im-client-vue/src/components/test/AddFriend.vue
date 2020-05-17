<template>
  <el-dialog
    :visible.sync="dialogVisible"
    :modal-append-to-body="false"
    width="500px"
    v-loading="loadingVisible"
    @open="handleDialogOpen()"
    @close="handleDialogClose()"
  >
    <div slot="title" class="dialog-header">
      <h3>添加好友</h3>
    </div>
    <div class="search-area">
      <form
        ref="searchFriendForm"
        :model="searchFriendForm"
        :rules="rules"
        @keydown.enter.stop="search"
      >
        <input
          type="text"
          v-model="searchFriendForm.account"
          placeholder="输入账号查询用户"
          :maxlength="20"
          class="friend-input"
          id="friendAccount"
        />
        <i class="iconfont icon-sousuo friend-search" @click="search()"></i>
      </form>
    </div>
    <div class="hint" v-show="notFoundUser">
      <span class="badge">没有找到用户</span>
    </div>
    <div class="user-area" v-show="foundUser">
      <img class="avatar" :src="queryUser.avatar" />
      <label class="username">{{queryUser.username}}</label>
      <label class="menu" @click="handleAddFriend">发送好友申请</label>
    </div>
    <span slot="footer" class="dialog-footer">
      <button class="el-button" @click="handleDialogClose">取 消</button>
      <!-- <button class="el-button">确 定</button> -->
    </span>
  </el-dialog>
</template>

<script>
import { findUserByAccount, addFriend } from '@/api/user'
import { setNotFoundUser, setFoundUser, setQueryUser, setFriendList } from '@/store/actions'
import store from '@/store'
import { mapGetters } from 'vuex'
export default {
  name: "add-friend",
  data () {
    return {
      loadingVisible: false,
      dialogVisible: false,
      searchFriendForm: {
        account: '',
      },
      rules: {
        account: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
        ],
      }
    }
  },
  computed: {
    ...mapGetters({
      notFoundUser: 'notFoundUser',
      foundUser: 'foundUser',
      queryUser: 'queryUser',
    })
  },
  methods: {
    handleAddFriend () {
      addFriend(this.queryUser.id).then(res => {
        console.log(res)
        if (!res.data.success) {
          console.log(res.data.msg)
          return
        }
        console.log('添加好友成功')
        setFriendList(store, res.data.friendList)
        this.handleDialogClose()
      })
    },
    handleDialogOpen () {
      this.$nextTick(() => {
        // this.$refs['oldPassword'].focus()
      })
    },
    handleDialogClose () {
      this.dialogVisible = false
      setNotFoundUser(store, false)
      setFoundUser(store, false)
      setQueryUser(store, {})
      this.searchFriendForm.account = ''
    },
    search () {
      var account = this.searchFriendForm.account
      if ('' == account) {
        return
      }
      findUserByAccount(account)
        .then(res => {
          if (!res.data.success) {
            setNotFoundUser(store, true)
            setFoundUser(store, false)
            setQueryUser(store, {})
            return false
          }
          setNotFoundUser(store, false)
          setFoundUser(store, true)
          setQueryUser(store, res.data.user)
        })

    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openDialog', function (action) {
        this.dialogVisible = true
      })
    })
  }
}
</script>

<style lang="less" scoped>
.dialog-header {
  width: 100%;
  height: 50px;
  line-height: 50px;
  color: #fff;
  background-color: #2e3238;
}
.dialog-header h3 {
  padding-left: 15px;
}
.search-area {
  padding: 0 30px;
  .friend-input {
    -webkit-appearance: none;
    background-color: #fff;
    background-image: none;
    border-radius: 4px;
    border: 1px solid #dcdfe6;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    color: #606266;
    display: inline-block;
    font-size: inherit;
    height: 40px;
    line-height: 40px;
    outline: 0;
    padding: 0 15px;
    -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
    transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
    width: 80%;
  }
  .friend-search {
    font-size: inherit;
    cursor: pointer;
  }
}
.hint {
  padding: 0 30px;
  padding-top: 10px;
  .badge {
    height: 18px;
    line-height: 18px;
    color: #f56c6c !important;
  }
}
.user-area {
  padding: 20px 30px;
  .avatar {
    // cursor: pointer;
    width: 40px;
    height: 40px;
    img {
      border-radius: 3px;
      cursor: pointer;
    }
  }
  .username {
    display: inline-block;
  }
  .menu {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #2e3238;
    border: 0;
    color: #fff;
    -webkit-appearance: none;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    margin-left: 30px;
    -webkit-transition: 0.1s;
    transition: 0.1s;
    font-weight: 500;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;
  }
}
.dialog-footer {
  .el-button {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #2e3238;
    border: 0;
    color: #fff;
    -webkit-appearance: none;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    -webkit-transition: 0.1s;
    transition: 0.1s;
    font-weight: 500;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;
  }
}
</style>

