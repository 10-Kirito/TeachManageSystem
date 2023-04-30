<template>
  <div>
    <h1>该页面是针对学生信息的增删查改内容，该内容还包含了学生密码的重置！</h1>
    <br>

    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入学生号" v-model="searchInfo.studentId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入学生姓名" v-model="searchInfo.studentName"></el-input>
      <template>
        <el-select v-model="searchInfo.gender" placeholder="请输入学生性别" style="width: 290px; margin-left: 5px">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </template>

      <template>
        <el-select v-model="searchInfo.departName" placeholder="请选择所属学院" style="width: 290px ; margin-left: 5px">
          <el-option
              v-for="item in options"
              :key="item"
              :value="item">
          </el-option>
        </el-select>
      </template>
      <el-button style="margin-left: 5px; width: 100px" type="primary" @click="getAllStudents">搜索</el-button>
      <el-button style="margin-left: 5px; width: 100px" type="danger" @click="reset">重置</el-button>
    </div>

    <el-table :data="studentDetails" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="studentId" label="学生学号" width="140" align="center"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" width="140" align="center"></el-table-column>
      <el-table-column prop="birthday" label="出生年月" width="140" align="center"></el-table-column>
      <el-table-column prop="nativePlace" label="籍贯" width="140" align="center"></el-table-column>
      <el-table-column prop="phoneNumber" label="电话号码" align="center"></el-table-column>
      <el-table-column prop="departName" label="所属学院" align="center"></el-table-column>
      <el-table-column prop="state" label="当前状态" align="center"></el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">重置密码 <i class="el-icon-edit"></i></el-button>
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

  </div>
</template>

<script>
export default {
  name: "StudentDetails",
  data(){
    return{
      studentDetails: [],


      total:0,
      pageNum:1,
      pageSize:10,

      searchInfo: {
        studentId: null,
        studentName: "",
        gender: "",
        departName: "",
        status: ""
      },

      options: []
    }
  },
  created() {
    this.getAllDepartName();
    this.getAllStudents();
  },
  methods: {
    // 重置密码
    handleEdit(data){
      console.log(data);
      throw this.request.get("/student-passwords/reset", {
        params: {
          studentId: data.studentId
        }
      }).then(response => {
        this.$message.success(response.msg);
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
    getAllStudents(){
      this.request.get("/student/getallstudents", {
        params:{
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          studentId: this.searchInfo.studentId,
          studentName: this.searchInfo.studentName,
          gender: this.searchInfo.gender,
          departName: this.searchInfo.departName,
          status: this.searchInfo.status
        }
      }).then(response =>{
        this.studentDetails = response.data.records;
        this.pageSize = response.data.size;
        this.pageNum =response.data.current;
        this.total = response.data.total;
      })
    },
    getAllDepartName(){
      this.request.get("/department/allName").then(data =>{
        // console.log(data);
        this.options = data;
      });
    },
    reset(){
      this.searchInfo = {};
      this.getAllStudents();
    }
  }
}
</script>

<style scoped>

</style>
