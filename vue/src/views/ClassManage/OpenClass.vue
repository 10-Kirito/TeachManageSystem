<template>
  <div>
    <h1>这里是已开放课程中已经分配好的课程，就是本学期所开的课程！在这里对所开的课程进行增删查改！</h1>
    <!-- 输入搜索框-->
    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入课程号" v-model="searchInfo.classId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程名" v-model="searchInfo.className"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入教师姓名" v-model="searchInfo.classScore"></el-input>
      <el-button style="margin-left: 5px; width: 100px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 5px; width: 100px" type="danger" @click="reset">重置</el-button>
    </div>

    <!-- 页面所展示的表格-->
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="classId" label="课程号" width="90"></el-table-column>
      <el-table-column prop="className" label="课程名" width="180"></el-table-column>
      <el-table-column prop="score" label="学分" width="50"></el-table-column>
      <el-table-column prop="teacherId" label="教师号" width="80"></el-table-column>
      <el-table-column prop="teacherName" label="教师姓名" width="80"></el-table-column>
      <el-table-column prop="position" label="职称" width="80"></el-table-column>
      <el-table-column prop="time" label="上课时间"></el-table-column>
      <el-table-column prop="location" label="上课地点"></el-table-column>
      <el-table-column prop="capacity" label="容量"></el-table-column>
      <el-table-column prop="enrollment" label="人数"></el-table-column>
      <el-table-column prop="departName" label="学院"></el-table-column>

      <el-table-column width="210">

        <template slot-scope="scope">
          <el-button type="success" @click="handleExpansion(scope.row)">扩容<i class="el-icon-edit" style="margin-right: 5px"></i></el-button>
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
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>


    <el-dialog title="修改课程容量:" :visible.sync="dialogVisible_1" width="30%" :before-close="handleClose">
      <div class="block">
        <span class="demonstration">当前容量:  &nbsp<el-input v-model="expansion" style="width: 60px"></el-input></span>
        <el-slider v-model="expansion"></el-slider>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible_1 = false">取 消</el-button>
        <el-button type="primary" @click="handleExitExpansion">确 定</el-button>
      </span>
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
      pageSize:5,

      dialogVisible_1: false,
      expansion: 60,

      searchInfo: {
        classId :"",
        className : "",
        teacherName: ""
      },
      addInfo: {
        classId :"",
        className : "",
        teacherName:"",
        classScore : null,
        classTime: null,
        departId: null
      },
      multipleSelection: []
    }
  },
  created() {
    // 请求分页查询数据
    this.load();
  },
  methods: {
    load() {
      this.request.get("/open-class/pages", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          classId: this.searchInfo.classId,
          className: this.searchInfo.className,
          teacherName: this.searchInfo.teacherName
        }
      }).then(classPgae => {
        console.log(classPgae);
        this.tableData = classPgae.records;
        this.total = classPgae.total;
      })
    },
    reset() {
      this.pageNum = 1;
      this.searchInfo.classId = "";
      this.searchInfo.className = "";
      this.searchInfo.teacherName = "";
      this.load();
    },

    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(`当前页: ${pageNum}`);
      this.pageNum = pageNum
      this.load()
    },

    handleDel(row) {
      this.$confirm('从本学期所开设课程中删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 前端向后端发送信息，删除课程
        this.request.post("/class/delete",row).then(res => {
          if(res){
            this.$message.success("删除成功");
            this.load();
          }else {
            this.$message.error("删除失败")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 点击扩容按钮绑定的处理函数
    handleExpansion(row) {
    //  capacity: 60
    //  classId: "08305001"
    //  className: "离散数学"
    //  classRecord: 6
    //  departName : "计算机科学与工程学院"
    //  enrollment: 0
    //  location: "计308"
    //  position: "副教授"
    //  recordId: 1
    //  score: 4
    //  teacherId: 1001
    //  teacherName: "陈迪茂"
    //  term: "23年春季"
    //  time: "一5-6，三5-6，五7-8"
      this.addInfo = row;
      this.dialogVisible_1 = true;
    },
    // 编辑当前容量并进行更新
    handleExitExpansion(){
      // console.log(this.expansion);
      // console.log(this.addInfo);

      this.request.get("/open-class/updateExpansion", {
        params: {
          recordId: this.addInfo.recordId,
          expansion: this.expansion
        }
      }).then(reponse => {
        console.log(reponse);
      })


      this.dialogVisible_1 = false;
      this.reload();
    },
    handleSelectionChange(val){
      console.log(val);
      this.multipleSelection = val;
    },
    // 对话框关闭
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
  background: #ccc !important;
}
</style>
