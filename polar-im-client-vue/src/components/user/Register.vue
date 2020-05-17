<template>
  <el-dialog
    :modal-append-to-body="false"
    :visible.sync="dialogVisible"
    width="500px"
    v-loading="loadingVisible"
    @open="handleDialogOpen()"
  >
    <div slot="title" class="dialog-header">
      <h3>注册用户</h3>
    </div>
    <el-form
      :model="userModel"
      :rules="formRules"
      class="el-dialog-form"
      ref="userForm"
      label-width="80px"
      label-position="right"
      size="small"
    >
      <el-form-item label="账号" prop="account">
        <el-col :span="16">
          <el-input
            ref="account"
            :maxlength="16"
            v-model="userModel.account"
            @focus="clearValidate"
          ></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="用户名" prop="username">
        <el-col :span="16">
          <el-input ref="username" :maxlength="16" v-model="userModel.username"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-col :span="16">
          <el-input
            type="password"
            :maxlength="16"
            v-model="userModel.password"
            auto-complete="off"
          ></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-col :span="16">
          <el-input
            type="password"
            :maxlength="16"
            v-model="userModel.confirmPassword"
            auto-complete="off"
          ></el-input>
        </el-col>
      </el-form-item>
      <!-- <el-form-item label="验证码" prop="verificationCode">
        <el-col :span="16">
          <el-input class="vc-input" v-model="userModel.verificationCode" placeholder="请输入验证码"></el-input>
          <div class="vc" @click="getVerificationCode()">{{ this.verificationCode }}</div>
        </el-col>
      </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" size="small" @click="doRegister()">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { checkAccount, registerUser } from '@/api/auth'
import { outputError } from '@/utils/exception'

export default {
  name: "register-user",
  data () {
    var validateAccount = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'))
      } else {
        checkAccount(this.userModel.account)
          .then(res => {
            if (!res.data.success) {
              callback(new Error('账号已存在'))
            } else {
              callback()
            }
          })
      }
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.userModel.confirmPassword !== '') {
          this.$refs.userForm.validateField('confirmPassword')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.userModel.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    var validateVerificationCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value !== this.verificationCode) {
        callback(new Error('验证码不正确!'))
      } else {
        callback()
      }
    }
    return {
      loadingVisible: false,
      dialogVisible: false,
      accountAvaliable: false,
      userModel: {
        account: '',
        username: '',
        password: '',
        confirmPassword: '',
        // verificationCode: ''
      },
      verificationCode: '',
      formRules: {
        account: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 16 个字符', trigger: 'blur' },
          { validator: validateAccount, trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入用户名称', trigger: 'blur' },
          { min: 3, max: 32, message: '长度在 3 到 16 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        confirmPassword: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        // verificationCode: [
        // { validator: validateVerificationCode, trigger: 'blur' }
        // ]
      }
    }
  },
  methods: {
    handleDialogOpen () {
      // this.getVerificationCode()
      this.$nextTick(() => {
        this.$refs['account'].focus()
      })
    },
    clearValidate () {
      this.$refs['userForm'].clearValidate()
    },
    check () {
      let _this = this
      checkAccount(this.userModel.account)
        .then(res => {
          console.log(res.data.success)
          if (!res.data.success) {
            _this.accountAvaliable = false
          }
        })
    },
    doRegister () {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.loadingVisible = true
          registerUser(this.userModel.username, this.userModel.account, this.userModel.password)
            .then(response => {
              this.loadingVisible = false
              this.dialogVisible = false
              this.$emit('onRegisterSuccessed',
                this.userModel.account,
                this.userModel.password
              )
            })
            .catch(error => {
              this.loadingVisible = false
              outputError(this, error)
            })
        } else {
          return false
        }
      })
    },
    getVerificationCode () {
      this.loadingVisible = true
      getVerificationCode()
        .then(response => {
          this.verificationCode = response.data
          this.loadingVisible = false
        })
        .catch(error => {
          this.loadingVisible = false
          outputError(this, error)
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
.vc-input {
  float: left;
  width: 190px;
}
.vc {
  width: 70px;
  height: 30px;
  line-height: 30px;
  float: right;
  border: solid 1px #cecece;
  background-color: #f0f0f0;
  text-align: center;
  color: #007acc;
}
.vc:hover {
  cursor: pointer;
}
</style>

