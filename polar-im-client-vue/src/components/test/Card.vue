<template>
  <div class="card">
    <header>
      <avatar class="avatar" :username="user.username"  :size="40" :src="user.avatar" :rounded="false"></avatar>
      <edit-personal-info ref="editPersonalInfoDlg"></edit-personal-info>
      <change-password ref="changePasswordDlg"></change-password>
      <add-friend ref="addFriendDlg"></add-friend>
      <div class="info">
        <div class="name">{{user.username}}</div>
        <div class="menu">
          <el-dropdown trigger="click" @command="handleCommand">
            <i class="iconfont icon-menu"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="editPersonalInfo">账号设置</el-dropdown-item>
              <el-dropdown-item command="changePassword">修改密码</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <div class="menu">
            <el-dropdown trigger="click" @command="handleCommand">
              <i class="iconfont icon-yuedu"></i>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="addFriend">添加好友</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
      </div>
    </header>
    <footer class="search">
      <i class="iconfont icon-sousuo u-search"></i>
      <input type="text" placeholder="search user" @keyup="onKeyup($event)" />
    </footer>
  </div>
</template>
<script>
import { mapGetters, mapActions } from 'vuex'
import Avatar from 'vue-avatar'
export default {
  data () {
    return {
    }
  },
  computed: mapGetters(['user']),
  components: {
    EditPersonalInfo: resolve => require(['./EditPersonalInfo'], resolve),
    ChangePassword: resolve => require(['./ChangePassword'], resolve),
    AddFriend: resolve => require(['./AddFriend'], resolve),
    Avatar
  },
  methods: {
    ...mapActions({
      search (dispatch, value) {
        dispatch('search', value)
      }
    }),
    onKeyup (e) {
      this.search(e.target.value)
    },
    handleCommand (command) {
      switch (command) {
        case 'logout':
          this.logout()
          break
        case 'editPersonalInfo':
          this.$refs.editPersonalInfoDlg.$emit('openDialog')
          break
        case 'changePassword':
          this.$refs.changePasswordDlg.$emit('openDialog')
          break
        case 'addFriend':
          this.$refs.addFriendDlg.$emit('openDialog')
          break
      }
    },
    logout () {
      let _this = this
      this.$confirm('确定注销当前用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        sessionStorage.clear()
        _this.$router.push('/')
        // 模拟f5刷新
        // this.$router.go(0)
      }).catch(_ => {
      })
    },
  }
}


</script>
<style>
@import '../../assets/font/iconfont.css';
</style>>
<style scoped lang="less">
.card {
  padding: 12px;
  border-bottom: solid 1px #24272c;

  footer {
    margin-top: 10px;
  }
  .info {
    &:extend(.text);
    margin-left: 50px;
    height: 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .name {
      font-size: 18px;
    }
    .menu {
      cursor: pointer;
    }
  }
  .search {
    &:extend(.pd);
    padding-top: 0;
    border-radius: 4px;
    position: relative;
    .u-search {
      position: absolute;
      color: #ddd;
      font-size: 20px;
      line-height: 34px;
      left: 8px;
    }
    input {
      width: 100%;
      height: 30px;
      line-height: 30px;
      border: 0;
      border-radius: 2px;
      background-color: #26292e;
      color: #fff;
      padding-left: 30px;
      font-size: 12px;
      box-sizing: border-box;
    }
  }
  .avatar {
    float: left;
    cursor: pointer;
    width: 40px;
    height: 40px;
  }
}
</style>
