<template>
  <el-dialog :visible.sync="dialogVisible" width="500px" :modal-append-to-body="false">
    <div slot="title" class="dialog-header">
      <h3>修改个人信息</h3>
    </div>
    <el-form
      :model="myInfoModel"
      :rules="formRules"
      class="el-dialog-form"
      ref="userForm"
      label-width="80px"
      label-position="right"
      size="small"
    >
      <el-form-item label="用户名">
        <el-col :span="16">
          <el-input :maxlength="20" v-model="myInfoModel.username" autofocus></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="头像">
        <el-col :span="16">
          <el-upload
            class="avatar-uploader"
            :action="uploadAvatarUrl"
            :headers="uploadRequestHeaders"
            accept=".jpg, .jpeg, .png, .JPG, .JPEG"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="myInfoModel.avatar" :src="myInfoModel.avatar" class="avatar" alt />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-col>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <button class="el-button" @click="dialogVisible = false">取 消</button>
      <button class="el-button" @click="doUpdateMyInfo" :disabled="!saveButtonEnable">确 定</button>
    </span>
  </el-dialog>
</template>

<script>
import baseUrl from '@/api/baseUrl'
import store from '@/store'
import { updateCurrentUserInfo } from '@/api/user'
export default {
  name: 'edit-personal-info',
  data () {
    return {
      loadingVisible: true,
      dialogVisible: false,
      uploadAvatarUrl: baseUrl + '/file/uploadAvatar',
      uploadRequestHeaders: {
        Authorization: store.getters.token
      },
      myInfoModel: {
        username: '',
        avatar: '',
      },
      formRules: {
        username: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { max: 20, message: '不能超过20个字符', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    saveButtonEnable () {
      return true
    }
  },
  methods: {
    // uploadAvatar(req){
    //   console.log(req)
    // },
    handleDialogOpen () {
      this.$nextTick(() => {
        this.$refs['myInfoModel'].clearValidate()
        this.$refs['username'].focus()
      })
    },
    handleAvatarSuccess (res, file) {
      console.log(res)
      this.myInfoModel.avatar = res.avatar
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isPNG = file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG或PNG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return (isJPG || isPNG) && isLt2M
    },
    doUpdateMyInfo () {
      this.loadingVisible = true
      let _this = this
      let currentUser = store.getters.user
      updateCurrentUserInfo(this.myInfoModel.username, this.myInfoModel.avatar)
        .then(res => {
          _this.dialogVisible = false
          if (res.data.success) {
            currentUser.avatar = _this.myInfoModel.avatar
          }
        })

    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openDialog', function (action) {
        let currentUser = store.getters.user
        this.myInfoModel.username = currentUser.username
        this.myInfoModel.avatar = currentUser.avatar
        this.dialogVisible = true
      })
    })
  }
}
</script>
<style scoped lang="less">
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
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 80px;
  width: 80px;
  margin: 0;
  padding: 0;
}
.avatar-uploader:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 80px;
  height: 80px;
  line-height: 80px;
  text-align: center;
}
.avatar {
  width: 80px;
  height: 80px;
  display: block;
  // border-radius: 80px;
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
