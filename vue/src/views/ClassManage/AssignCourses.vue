<template>
  <div>
    <h1>当前所有开放的课程:</h1>
    <!-- 输入搜索框-->
    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入课程号" v-model="searchInfo.classId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程名" v-model="searchInfo.className"></el-input>
      <template>
        <el-select v-model="searchInfo.departName" placeholder="请选择所属学院" style="width: 290px ; margin-left: 5px">
          <el-option
              v-for="item in options"
              :key="item"
              :value="item">
          </el-option>
        </el-select>
      </template>
      <el-button style="margin-left: 5px; width: 100px;" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 5px; width: 100px" type="danger" @click="reset">重置</el-button>
    </div>

    <!-- 页面所展示的表格-->
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="classId" label="课程号"></el-table-column>
      <el-table-column prop="className" label="课程名"></el-table-column>
      <el-table-column prop="departName" label="所属学院"></el-table-column>

      <el-table-column width="300px" label="相关操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">查看已分配老师 <i class="el-icon-edit" style="margin-left: 2px"></i></el-button>
          <el-button type="danger" @click="handleDel(scope.row)">删除 <i class="el-icon-circle-close" style="margin-left: 2px"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页选项-->
    <div style="margin-top: 20px;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!--弹窗-->
    <el-dialog title="课程分配" :visible.sync="dialogFormVisible" width="40%" >
      <h1>已分配老师信息:</h1>
      <br>
      <el-table :data="assignTeachers" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="teacherId" label="教师号" width="80px"></el-table-column>
        <el-table-column prop="teacherName" label="教师名" width="80px"></el-table-column>
        <el-table-column prop="position" label="职称" width="80px"></el-table-column>
        <el-table-column prop="departName" label="所属院系"></el-table-column>
        <el-table-column width="150px">
          <template slot-scope="scope">
            <el-button type="danger" @click="cancelAssign(scope.row)">取消分配<i class="el-icon-circle-close" style="margin-left: 2px"></i></el-button>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-top: 20px;">
        <el-pagination
            @size-change="handleSizeChange_1"
            @current-change="handleCurrentChange_1"
            :current-page="pagaNum_1"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize_1"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total_1">
        </el-pagination>
      </div>
      <br>
      <h1>未分配老师信息:</h1>
      <br>
      <el-table :data="unAssignTeachers" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="teacherId" label="教师号" width="80px"></el-table-column>
        <el-table-column prop="teacherName" label="教师名" width="80px"></el-table-column>
        <el-table-column prop="position" label="职称" width="80px"></el-table-column>
        <el-table-column prop="departName" label="所属院系"></el-table-column>
        <el-table-column width="150px">
          <template slot-scope="scope">
            <el-button type="success" @click="assignTeacher(scope.row)">分配该课程<i class="el-icon-circle-check" style="margin-left: 2px"></i></el-button>
          </template>
        </el-table-column>

      </el-table>

      <div style="margin-top: 20px;">
        <el-pagination
            @size-change="handleSizeChange_2"
            @current-change="handleCurrentChange_2"
            :current-page="pagaNum_2"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize_2"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total_2">
        </el-pagination>
      </div>


      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">退出</el-button>
        </span>
      </template>
    </el-dialog>
  </div>

</template>

<script>
export default {
  inject:['reload'],
  name: "OpenClass",
  data(){
    return {
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,

      total_1: 0,
      pagaNum_1: 1,
      pageSize_1: 5,

      total_2: 0,
      pagaNum_2: 1,
      pageSize_2: 5,

      searchInfo: {
        classId :null,
        className : null,
        departName: null
      },
      dialogFormVisible: false,
      addInfo: {
        classId :"",
        className : "",
        teacherName:"",
        classScore : null,
        classTime: null,
        departId: null
      },
      options: [],
      assignTeachers: [],
      unAssignTeachers: []
    }
  },
  created() {
    // 请求分页查询数据
    this.load();
  },
  methods: {
    load() {
      this.request.get("/open-class/allOpenClass/pages", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          classId: this.searchInfo.classId,
          className: this.searchInfo.className,
          departName: this.searchInfo.departName
        }
      }).then(classPgae => {
        console.log(classPgae);
        this.tableData = classPgae.records;
        this.total = classPgae.total;
      });

      this.request.get("/department/allName").then(data =>{
        // console.log(data);
        this.options = data;
      });
    },
    reset() {
      this.pageNum = 1;
      this.pagaNum_1 = 1;
      this.pagaNum_2 = 1;
      this.searchInfo.classId = "";
      this.searchInfo.className = "";
      this.searchInfo.departName = null;

      this.load();
    },
    // 取消分配指定的老师
    cancelAssign(data){
      console.log(data);
      console.log(this.addInfo);

      this.request.get("/open-class/addOpenClass/cancelAssign", {
        params : {
          classRecord: this.addInfo.classRecord,
          teacherId: data.teacherId
        }
      }).then(response => {
        console.log(response);
        if (response.code == 'SUCCESS')
          this.$message.success(response.msg);
        this.reload();
      })

    },
    // 分配课程按钮，实质上就是插入一条新的开课记录
    assignTeacher(data){
      console.log(data);
      console.log(this.addInfo);

      this.request.get("/open-class/addOpenClass/assignTeacher", {
        params : {
          classRecord: this.addInfo.classRecord,
          teacherId: data.teacherId
        }
      }).then(response => {
        console.log(response);
        if (response.code == 'SUCCESS')
          this.$message.success(response.msg);
        this.reload();
      })
    },
    // 处理该页面中的三个分页选项，代码的冗余量就不管了
    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.load()
    },
    handleSizeChange_1(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize_1 = pageSize;
      this.getAllAssignTeachers();
    },
    handleSizeChange_2(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize_2 = pageSize;
      this.getAllUnAssignTeachers();
    },
    handleCurrentChange(pageNum) {
      console.log(`当前页: ${pageNum}`);
      this.pageNum = pageNum
      this.load()
    },
    handleCurrentChange_1(pageNum) {
      console.log(`当前页: ${pageNum}`);
      this.pagaNum_1 = pageNum
      this.getAllAssignTeachers();
    },
    handleCurrentChange_2(pageNum) {
      console.log(`当前页: ${pageNum}`);
      this.pagaNum_2 = pageNum;
      this.getAllUnAssignTeachers();
    },

    // 删除开课信息，注意如果将该开课信息删除的话，对应的所有的授课教师也会被删除
    handleDel(row) {
      this.$confirm('取消开设本课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.addInfo = row;
        console.log(row);
        // 前端向后端发送信息，删除课程
        console.log(this.addInfo.classRecord);
        this.request.get("/open-class/delOpenClass", {
          params: {
            classRecord: this.addInfo.classRecord
          }
        }).then(response => {
          if (response.code == 'SUCCESS')
            this.$message.success(response.msg);
          this.reload();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 该按钮是设置弹窗，弹窗内容为查看分配老师以及分配指定的老师
    handleEdit(row) {
      this.addInfo = row;
      this.dialogFormVisible = true;
      // 获取所有的教授同一课程的老师信息，并且是同一学院的
      this.getAllAssignTeachers();

      // 获取所有的没有教授该课程的老师信息，并且要求是同一学院的
      this.getAllUnAssignTeachers();
    },
    // 获得所有的已经分配该课程的老师
    getAllAssignTeachers(){
      console.log(this.pagaNum_1);
      console.log(this.pageSize_1);
      this.request.get("open-class/allAssignTeacher", {
        params: {
          currentPage: this.pagaNum_1,
          pageSize: this.pageSize_1,
          classId: this.addInfo.classId,
          departName: this.addInfo.departName
        }
      }).then(response =>{
        this.assignTeachers = response.records;
        console.log(response);
        this.total_1 = response.total;
      })
    },
    // 获得所有的没有分配该课程的老师
    getAllUnAssignTeachers(){
      console.log(this.pagaNum_2);
      console.log(this.pageSize_2);
      this.request.get("open-class/allUnAssignTeacher", {
        params: {
          currentPage: this.pagaNum_2,
          pageSize: this.pageSize_2,
          classId: this.addInfo.classId,
          departName: this.addInfo.departName
        }
      }).then(response =>{
        this.unAssignTeachers = response.records;
        console.log(response);
        this.total_2 = response.total;
      })
    },
  }
}
</script>

<style scoped>
.headerBg{
  background: #ccc !important;
}
</style>
