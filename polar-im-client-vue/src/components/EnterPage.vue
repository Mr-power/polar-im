<template>
  <div class="login">
    <div class="login-form-layout">
      <el-form
        autocomplete="on"
        :model="loginForm"
        ref="loginForm"
        :rules="rules"
        label-position="left"
      >
        <div class="login-header">
          <strong>Polar IM</strong>
        </div>
        <div class="alert-margin" v-show="showMes">
          <el-alert :title="mes" type="error" show-icon></el-alert>
        </div>
        <el-form-item prop="account">
          <el-input
            name="account"
            type="text"
            v-model="loginForm.account"
            autocomplete="on"
            placeholder="请输入账号"
            @focus="clearValidate"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            name="password"
            v-model="loginForm.password"
            type="password"
            autocomplete="on"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item class="login-button">
          <el-button type="primary" :loading="loading" @click.native.prevent="onSubmit()">登录</el-button>
        </el-form-item>
        <div class="login-footer">
          <a href="#" @click="openRegisterDialog()">没有账号，立即注册</a>
        </div>
      </el-form>
    </div>
    <register-user ref="registerUser" @onRegisterSuccessed="onRegisterSuccessed"></register-user>
  </div>
</template>

<script>
import { login } from '@/api/auth'
// import store from '@/store'
export default {
  name: 'EnterPage',
  data () {
    return {
      mes: '',
      showMes: false,
      loading: false,
      // 登录表单
      loginForm: {
        account: '',
        password: '',
        captcha: ''
      },
      rules: {
        account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  beforeDestroy () {
    window.removeEventListener('keydown', this.handleKeyDown, true)
  },
  created () {
    window.addEventListener('keydown', this.handleKeyDown, true)
  },
  // 所有dom渲染完成之后执行，也就是ready之后
  mounted: function () {
    // 添加keypress监听器
    // this.$nextTick(function () {
    //   if (document.addEventListener) {
    //     //如果是Firefox
    //     document.addEventListener('keypress', ieHandler, true)
    //   } else {
    //     document.attachEvent('onkeypress', ieHandler)
    //   }
    //   function ieHandler (evt) {
    //     if (evt.keyCode == 13) {
    //       document.getElementById('btnLogin').click()
    //     }
    //   }
    // })
  },
  components: {
    RegisterUser: resolve => require(['@/components/user/Register'], resolve)
  },
  methods: {
    clearValidate () {
      this.$refs['loginForm'].clearValidate()
      this.showMes = false
      this.mes = ''
    },
    onRegisterSuccessed (account, password) {
      this.loginForm.account = account
      this.loginForm.password = password
      this.onSubmit()
    },
    openRegisterDialog () {
      this.$refs.registerUser.$emit('openDialog')
    },
    // 提交
    onSubmit () {
      var _this = this
      this.$refs['loginForm'].validate(function (valid) {
        if (valid) {
          _this.loading = true
          login(_this.loginForm.account, _this.loginForm.password)
            .then((result) => {
              _this.loading = false
              let redirect = decodeURIComponent(
                _this.$route.query.redirect || "/test"
              )
              _this.$router.push(redirect)
            }).catch((err) => {
              console.log(err)
              _this.loading = false
              _this.showMes = true
              _this.mes = err.data.msg

            })

        }
      })
    },
    handleKeyDown (e) {
      let key = null
      if (window.event === undefined) {
        key = e.keyCode
      } else {
        key = window.event.keyCode
      }
      if (key === 13) {
        this.onSubmit()
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style  lang="less" scoped>
.login {
  /* top: 0;
  left: 0; */
  background: url('../assets/login.jpg') no-repeat center;
  background-size: 100% 100%;
  height: 100%;
  width: 100%;
  position: fixed;
  overflow: hidden;
}
.login-form-layout {
  width: 300px;
  margin: 150px auto;
  padding: 0px;
}
.login-header {
  padding: 0px 0px 25px 0px;
  position: relative;
  z-index: 10;
  text-align: center;
}
.alert-margin {
  margin-bottom: 10px;
}
.login-center-layout {
  background: #409eff;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-top: 200px;
}
.login-button {
  margin-bottom: 60px;
  width: 100%;
  .el-button {
    width: 100%;
  }
}

.login-footer {
  font-size: 13px;
  padding: 0px 40px 40px;
  text-align: center;
}
</style>
