<template>
  <div class="wrapper">
    <div style="margin: 270px auto; background-color: #fff;width: 400px;height: 380px;padding: 20px;border-radius:10px ">
      <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>

        <div style="margin: 10px 0;text-align: center">
          <el-button type="primary" size="big" autocomplete="off" @click="login" style="width: 360px">登录</el-button>
          <!--<router-link to="/admin" style="text-decoration: none; margin-top: 10px">退出</router-link>-->
          <!--<el-button type="warning" size="big" autocomplete="off" @click="$router.push('/register')">注册</el-button>-->
        </div>

        <el-form-item prop="identity">
          <el-radio-group v-model="user.identity">
            <el-radio label="admin" style="margin-right: 72px">管理员</el-radio>
            <el-radio label="teacher" style="margin-right: 72px">教师</el-radio>
            <el-radio label="student" >学生</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>

    </div>
  </div>

</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {
        username: '',
        password: '',
        identity: 'student'
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 4, max: 20, message: '账号长度最低在 4 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 20, message: '密码长度建议在 6 到 20 个字符', trigger: 'blur'}
        ],
        identity: [
          {required: true, message: '请选择您的身份', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    login() {
      // console.log(this.user);
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          if (this.user.identity == 'admin')
            this.adminLogin();
          else if (this.user.identity == 'teacher')
            this.teacherLogin();
          else if (this.user.identity == 'student')
            this.studentLogin();
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    adminLogin() {
      // console.log(this.user);
      this.request.get("admin-passwords/login", {params:{name: this.user.username, password: this.user.password}}).then(res => {
        if (res.code == 'SUCCESS'){
          localStorage.setItem("userInfo", JSON.stringify(res.data));
          this.$router.push("/admin");
          this.$message.success("登陆成功!");
        }else if(res.code == 'FORBIDDEN') {
          this.$message.success("用户密码错误!");
        }else {
          this.$message.success("该用户不存在!");
        }
      })
    },
    studentLogin() {
      // console.log(this.user);
      this.request.get("student-passwords/login", {params:{id: this.user.username, password: this.user.password}}).then(res => {
        if (res.code == 'SUCCESS'){
          localStorage.setItem("userInfo", JSON.stringify(res.data));
          this.$router.push("/student");
          this.$message.success("登陆成功!");
        }else if(res.code == 'FORBIDDEN') {
          this.$message.error("用户密码错误!");
        }else {
          this.$message.error("该用户不存在!");
        }
      })
    },
    teacherLogin() {
      // console.log(this.user);
      this.request.get("teacher-passwords/login", {params:{id: this.user.username, password: this.user.password}}).then(res => {
        if (res.code == 'SUCCESS'){
          localStorage.setItem("userInfo", JSON.stringify(res.data));
          this.$router.push("/teacher");
          this.$message.success("登陆成功!");
        }else if(res.code == 'FORBIDDEN') {
          this.$message.success("用户密码错误!");
        }else {
          this.$message.success("该用户不存在!");
        }
      })
    }
  }
}
</script>

<style>
.wrapper{
  height: 100vh;
  background-image: linear-gradient(to right, #eea2a2 0%, #bbc1bf 19%, #57c6e1 42%, #b49fda 79%, #7ac5d8 100%);
  overflow: hidden;
}
.el-input--medium .el-input__inner {
  height: 50px;
  line-height: 36px;
}

</style>
