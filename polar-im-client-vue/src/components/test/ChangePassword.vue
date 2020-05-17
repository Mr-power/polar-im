<template>
  <el-dialog
    :visible.sync="dialogVisible"
     :modal-append-to-body='false'
    width="500px"
    v-loading="loadingVisible"
    @open="handleDialogOpen()"
  >
    <div slot="title" class="dialog-header">
      <h3>修改密码</h3>
    </div>
    <el-form
      :model="changePwdModel"
      :rules="formRules"
      class="el-dialog-form"
      ref="changePwdForm"
      label-width="80px"
      label-position="right"
      size="small"
    >
      <el-form-item label="原密码" prop="oldPassword">
        <el-col :span="16">
          <el-input
            ref="oldPassword"
            type="password"
            :maxlength="16"
            v-model="changePwdModel.oldPassword"
            auto-complete="off"
          ></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-col :span="16">
          <el-input
            type="password"
            :maxlength="16"
            v-model="changePwdModel.newPassword"
            auto-complete="off"
          ></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPassword">
        <el-col :span="16">
          <el-input
            type="password"
            :maxlength="16"
            v-model="changePwdModel.checkPassword"
            auto-complete="off"
          ></el-input>
        </el-col>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <button class="el-button" @click="dialogVisible = false">取 消</button>
      <button class="el-button" @click="doChangePassword">确 定</button>
    </span>
  </el-dialog>
</template>

<script>
import { changePassword } from '@/api/user'
// import { outputError } from '@/exception/exception'

export default {
  name: "change-password",
  data () {
    var validateOldPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入原密码'))
      } else {
        callback()
      }
    }
    var validateNewPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'))
      } else {
        if (this.changePwdModel.checkPassword !== '') {
          this.$refs.changePwdForm.validateField('checkPassword')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.changePwdModel.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loadingVisible: false,
      dialogVisible: false,
      changePwdModel: {
        oldPassword: '',
        newPassword: '',
        checkPassword: ''
      },
      formRules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { validator: validateOldPass, trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validateNewPass, trigger: 'blur' }
        ],
        checkPassword: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleDialogOpen () {
      this.$nextTick(() => {
        this.$refs['oldPassword'].focus()
      })
    },
    doChangePassword () {
      this.$refs['changePwdForm'].validate((valid) => {
        if (valid) {
          this.loadingVisible = true
          changePassword(this.changePwdModel.oldPassword, this.changePwdModel.newPassword)
            .then(res => {
              this.loadingVisible = false
              console.log(res)
              if (res.data.success) {
                this.dialogVisible = false
                return
              }
              console.log('修改密码失败，请确认原密码是否正确！')
            })
          //   .catch(error => {
          //     this.loadingVisible = false
          //     console.log(error)
          //   })
        } else {
          return false
        }
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

