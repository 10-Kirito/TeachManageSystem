<template>
  <div>
    <el-tag type="success" style="height: 40px;line-height: 37px;width: 80px;font-size: 15px;">我的课程</el-tag>
    <!-- 输入搜索框-->
    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入课程号" v-model="searchInfo.classId"></el-input>
      <!--<el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请选择课程" v-model="searchInfo.className"></el-input>-->
      <template>
        <el-select v-model="searchInfo.className" placeholder="请选择课程" style="width: 290px ; margin-left: 5px">
          <el-option
              v-for="item in classOptions"
              :key="item"
              :value="item">
          </el-option>
        </el-select>
      </template>
      <el-button style="margin-left: 5px; width: 100px" type="primary" @click="myClassDetail">搜索</el-button>
      <el-button style="margin-left: 5px; width: 100px" type="danger" @click="reset">重置</el-button>
    </div>

    <!-- 该名教师教授的所有的课程-->
    <div class="grid-content bg-purple-light">
      <el-table :data="myClassDetails" :header-cell-style="{background:'#ADD8E6',color:'#606266'}" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="classId" label="课程号" ></el-table-column>
        <el-table-column prop="className" label="课程名" ></el-table-column>
        <el-table-column prop="time" label="上课时间" ></el-table-column>
        <el-table-column prop="location" label="上课地点" ></el-table-column>
        <el-table-column prop="capacity" label="总容量" ></el-table-column>
        <el-table-column prop="enrollment" label="已选人数" ></el-table-column>
        <el-table-column prop="" width="320px">
          <template slot-scope="scope">
            <el-button type="success" @click="getStudentDetails(scope.row)">查看选课学生<i class="el-icon-edit" style="margin-left: 2px"></i></el-button>
            <el-button type="success" @click="exp(scope.row)">导出学生名单<i class="el-icon-circle-close" style="margin-left: 2px"></i></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页选项-->
    <div style="margin-top: 20px;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="选课名单" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
      <div class="grid-content bg-purple-light">
        <el-table :data="myClassStudents" :header-cell-style="{background:'#ADD8E6',color:'#606266'}" border stripe header-cell-class-name="headerBg">
          <el-table-column prop="studentId" label="学号" ></el-table-column>
          <el-table-column prop="studentName" label="姓名" ></el-table-column>
          <el-table-column prop="gender" label="男" ></el-table-column>
          <el-table-column prop="departName" label="学院" ></el-table-column>
        </el-table>
      </div>
      <!-- 分页选项-->
      <div style="margin-top: 20px;">
        <el-pagination
            @size-change="handleSizeChange_1"
            @current-change="handleCurrentChange_1"
            :current-page="pageNum_1"
            :page-sizes="[5, 10]"
            :page-size="pageSize_1"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total_1">
        </el-pagination>
      </div>


      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">退出</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "myClass",
  data(){
    return{
      total:0,
      pageNum:1,
      pageSize:5,

      total_1:0,
      pageNum_1:1,
      pageSize_1:5,

      dialogVisible: false,
      searchInfo: {
        classId: "",
        className: ""
      },
      classOptions: [],
      user:[],
      myClassDetails: [],
      // 选择指定课程的学生信息
      myClassStudents: []
    }
  },
  created() {
    this.getUser();
    this.myClassName();
    this.myClassDetail();
  },
  methods: {
    // 1. 获取当前登录教师信息
    getUser(){
      const data = JSON.parse(localStorage.getItem('userInfo'))
      if (data) {
        Vue.set(this, 'user', data);
      }
    },
    // 2. 下拉框功能，查询当前教师所有的课程名
    myClassName(){
      this.request.get("/open-class/myClassName", {
        params: {
          teacherId: this.user.teacherId
        }
      }).then(response => {
        if (response.code == "SUCCESS")
          this.classOptions = response.data;
      })
    },
    // 3. 查询当前教师所教授的课程详细信息
    myClassDetail(){
      this.request.get("/open-class/myClassDetailsPages", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          teacherId: this.user.teacherId,
          classId: this.searchInfo.classId,
          className: this.searchInfo.className
        }
      }).then(response => {
        if (response.code == "SUCCESS") {
          console.log(response);
          this.myClassDetails = response.data.records;
          this.total = response.data.total;
          this.pageNum = response.data.current;
          this.pageSize = response.data.size;
        }
      })
    },
    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.myClassDetail();
    },
    handleCurrentChange(pageNum) {
      console.log(`当前页: ${pageNum}`);
      this.pageNum = pageNum
      this.myClassDetail();
    },
    handleSizeChange_1(pageSize_1) {
      console.log(`每页 ${pageSize_1} 条`);
      this.pageSize_1 = pageSize_1
      this.getStudentDetails();
    },
    handleCurrentChange_1(pageNum_1) {
      console.log(`当前页: ${pageNum_1}`);
      this.pageNum_1 = pageNum_1
      this.getStudentDetails();
    },
    // 4. 弹窗关闭按钮功能绑定
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    // 5. 相应课程所选学生信息：
    getStudentDetails(data){
      this.dialogVisible = true

      throw this.request.get("/select-class/getClassStudents", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          teacherId: this.user.teacherId,
          classId: data.classId
        }
      }).then(response => {
        console.log(response);

        if (response.code == "SUCCESS"){
          this.myClassStudents = response.data.records;
          this.total_1 = response.data.total;
          this.pageNum_1 = response.data.current;
          this.pageSize_1 = response.data.size;
        }
      })


      console.log(data);
      console.log(this.user);
    },
    exp(data){
      let teacherId = this.user.teacherId;
      window.open('http://localhost:9090/select-class/exportClassStudents?teacherId='+ teacherId + '&classId=' + data.classId);
    },
    reset(){
      this.searchInfo = {};
      this.myClassDetail();
    }
  }
}
</script>

<style scoped>

</style>
