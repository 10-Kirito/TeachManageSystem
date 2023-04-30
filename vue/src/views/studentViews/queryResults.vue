<template>
  <div>
    <h1>这里是查询成绩界面!</h1>
    <br>
    <el-table :data="classDetail" border stripe header-cell-class-name="headerBg" :header-cell-style="{background:'#ADD8E6',color:'#606266'}">
      <el-table-column
          type="index"
          :index="indexMethod"
          label="#"
          width="40px">
      </el-table-column>
      <el-table-column prop="classId" label="课程号" ></el-table-column>
      <el-table-column prop="className" label="课程名"></el-table-column>
      <el-table-column prop="score" label="学分"></el-table-column>
      <el-table-column prop="teacherId" label="教师号"></el-table-column>
      <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
      <el-table-column prop="usuallyScore" label="平时成绩"></el-table-column>
      <el-table-column prop="testScore" label="考试成绩"></el-table-column>
      <el-table-column prop="totalScore" label="总成绩"></el-table-column>
    </el-table>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "queryResults",
  data(){
    return{
      user:{
        birthday: null,
        departId: null,
        gender: null,
        nativePlace: null,
        phoneNumber: null,
        state: null,
        studentId: null,
        studentName: null
      },
      classDetail: []
    }
  },
  created() {
    this.getUser();
    this.getAllClass();
  },
  methods: {
    // 1. 获取用户信息
    getUser(){
      const data = JSON.parse(localStorage.getItem('userInfo'))
      if (data) {
        Vue.set(this, 'user', data);
      }
    },
    getAllClass(){
      this.getUser();
      this.request.get("/select-class/student/selected",{
        params: {
          studentId: this.user.studentId
        }
      }).then(reponse => {
        this.classDetail = reponse.data;
        console.log(this.classDetail);
      })
    },
    indexMethod(index){
      // 将表格自带的索引转化为相应的大小的字符，即A,B,C,D,E,F
      index++;
      let c = 64 + index;
      return String.fromCharCode(c);
    }
  }
}
</script>

<style scoped>

</style>
