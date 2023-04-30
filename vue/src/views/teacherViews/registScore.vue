<template>
  <div>
    <h1>分数登记</h1>
    <div style="margin: 10px 0">
      <el-input suffix-icon="el-icon-search" style="width:200px" placeholder="请输入学号" v-model="searchInfo.studentId"></el-input>
      <el-input suffix-icon="el-icon-search" style="width:200px; margin-left: 5px;" placeholder="请输入姓名" v-model="searchInfo.studentName"></el-input>
        <template>
          <el-select v-model="searchInfo.className" clearable placeholder="请选择课程" style="margin-left: 5px">
            <el-option
                v-for="item in courseName"
                :key="item"
                :value="item">
            </el-option>
          </el-select>
        </template>


      <el-button  style="margin-left: 10px;" type="primary" @click="getStudentDetails">搜索</el-button>
      <el-button  type="warning" @click="reset">重置</el-button>
    </div>




    <el-table :data="studentDetails" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="studentId" label="学生学号" width="140" align="center"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" width="140" align="center"></el-table-column>
      <el-table-column prop="classId" label="课程号" width="140" align="center"></el-table-column>
      <el-table-column prop="className" label="课程名" width="140" align="center"></el-table-column>
      <el-table-column prop="term" label="学期" align="center"></el-table-column>
      <el-table-column prop="usuallyScore" label="平时成绩" align="center"></el-table-column>
      <el-table-column prop="testScore" label="考试成绩" align="center"></el-table-column>
      <el-table-column prop="totalScore" label="总成绩" align="center"></el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">登记成绩 <i class="el-icon-edit"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2,5,10,20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="登记成绩" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form label-width="80px"  size="small">
        <el-form-item label="学号" >
          <el-input v-model="details.studentId" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名" >
          <el-input v-model="details.studentName" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="平时成绩" >
          <el-input v-model="registInfo.usuallyScore" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试成绩">
          <el-input v-model="registInfo.testScore" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>


      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="regist">确 定</el-button>
      </span>
    </el-dialog>




  </div>
</template>

<script>

import Vue from "vue";

export default {
  name: "registScore",
  inject:['reload'],
  data(){
    return{
      user: {},

      studentDetails: [],
      courseName: [],
      total:0,
      pageNum:1,
      pageSize:10,

      searchInfo:{
        studentId: "",
        studentName: "",
        className: ""
      },

      registInfo: {
        recordId: null,
        usuallyScore: null,
        testScore: null
      },

      dialogVisible:false,
      details: {}
    }
  },
  created() {
    this.getUser();
    this.myClassName();
    this.getStudentDetails();
  },
  methods: {
    // 1. 获取当前登录教师信息
    getUser(){
      const data = JSON.parse(localStorage.getItem('userInfo'))
      if (data) {
        Vue.set(this, 'user', data);
      }
    },
    // 2. 获取所有的课程名字
    myClassName(){
      this.request.get("/open-class/myClassName", {
        params: {
          teacherId: this.user.teacherId
        }
      }).then(response => {
        if (response.code == "SUCCESS")
          this.courseName = response.data;
      })
    },
    // 3. 分页查询
    handleSizeChange(pageSize){
      // console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      // console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },

    // 登记成绩按钮绑定函数
    handleEdit(data){
      this.dialogVisible = true;
      // console.log(data);
      this.registInfo.recordId = data.recordId;
      this.details = data;
    },
    // 登记分数
    regist(){
      this.dialogVisible = false;
      // console.log(this.registInfo);

      this.request.get("/select-class/registScore", {
        params :{
          recordId: this.registInfo.recordId,
          usuallyScore: this.registInfo.usuallyScore,
          testScore: this.registInfo.testScore
        }
      }).then(response => {
        if (response.code == "SUCCESS"){
          this.$message.success(response.msg);
          this.reload();
        }
      })
    },
    // 获取指定教师教授的学生信息
    getStudentDetails() {
      this.request.get("/select-class/registStudents", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          studentId: this.searchInfo.studentId,
          studentName: this.searchInfo.studentName,
          className: this.searchInfo.className
        }
      }).then(response => {
        // console.log(response);
        if (response.code == "SUCCESS") {
          this.studentDetails = response.data.records;
          this.pageSize = response.data.size;
          this.pageNum = response.data.current;
          this.total = response.data.total;
        }
      })
    },
    // 重置按钮
    reset(){
      this.searchInfo ={};
      this.getStudentDetails();
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    }


  }
}
</script>

<style scoped>
.headerBg{
  background: #eee !important;
}
</style>
