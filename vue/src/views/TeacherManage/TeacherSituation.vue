<template>
  <div>
    <h1>教师授课情况!!!</h1>
    <!-- 输入搜索框-->
    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入教师号" v-model="searchInfo.teacherId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入教师名" v-model="searchInfo.teacherName"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入教师性别" v-model="searchInfo.gender"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入教师职称" v-model="searchInfo.position"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="教师所属院系" v-model="searchInfo.departId"></el-input>
      <el-button style="margin-left: 5px; width: 100px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 5px; width: 100px" type="danger" @click="reset">重置</el-button>
    </div>

    <!-- 新增按钮-->
    <div style="margin: 10px 0; margin-top: 25px; margin-bottom: 25px">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline" style="margin-left: 2px"></i></el-button>
      <el-button type="danger" @click="handleDelMul" style="margin-right: 5px">批量删除 <i class="el-icon-remove-outline" style="margin-left: 2px"></i></el-button>
      <!--点击上传文件-->
      <el-upload action="http://localhost:9090/class/importClass" style="display: inline-block" accept="xlsx" :show-file-list="false" :on-success="handleExcelImportSuccess">
        <el-button type="primary" style="margin-right: 5px">导入 <i class="el-icon-upload2" style="margin-left: 2px"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp">导出 <i class="el-icon-download" style="margin-left: 2px"></i></el-button>
    </div>

    <!-- 页面所展示的表格-->
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="teacherId" label="教师号" ></el-table-column>
      <el-table-column prop="teacherName" label="教师名" ></el-table-column>
      <el-table-column prop="gender" label="性别" ></el-table-column>
      <el-table-column prop="birthday" label="出生年月" ></el-table-column>
      <el-table-column prop="position" label="职称" ></el-table-column>
      <el-table-column prop="departId" label="所属院系"></el-table-column>

      <el-table-column width="212">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit" style="margin-left: 2px"></i></el-button>
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
    <el-dialog title="教师信息" :visible.sync="dialogFormVisible" width="20%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="教师号">
          <el-input v-model="addInfo.teacherId" autocomplete="off" style="width: 220px;"/>
        </el-form-item>
        <el-form-item label="教师名">
          <el-input v-model="addInfo.teacherName" autocomplete="off" style="width: 220px;"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="addInfo.gender" placeholder="性别" style="width: 220px;">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生年月">
          <div class="block">
            <span class="demonstration"></span>
            <el-date-picker
                v-model="addInfo.birthday"
                type="date"
                placeholder="选择日期"
                style="width: 220px;">
            </el-date-picker>
          </div>
        </el-form-item>

        <el-form-item label="职称">
          <el-select v-model="addInfo.position" placeholder="职称" style="width: 220px;">
            <el-option label="辅导员" value="辅导员"></el-option>
            <el-option label="助教" value="助教"></el-option>
            <el-option label="讲师" value="讲师"></el-option>
            <el-option label="副教授" value="副教授"></el-option>
            <el-option label="教授" value="教授"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="所属院系">
          <el-input v-model="addInfo.departId" autocomplete="off" style="width: 220px;"/>
        </el-form-item>
      </el-form>
      <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false" style="margin-right: 80px">取消</el-button>
                <el-button type="primary" @click="handleSave" style="margin-right: 40px">确认</el-button>
              </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "TeacherDetails",
  data(){
    return {
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,

      searchInfo: {
        teacherId :null,
        teacherName : "",
        gender : "",
        position: "",
        departId: null
      },
      dialogFormVisible: false,
      addInfo: {
        teacherId :null,
        teacherName : "",
        gender : "",
        birthday:"",
        position: "",
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
    handleExcelImportSuccess(){
      this.$message.success("文件上传成功！")
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/teacher/exportAll");
    },
    load() {
      this.request.get("/teacher/page", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          teacherId: this.searchInfo.teacherId,
          teacherName: this.searchInfo.teacherName,
          gender: this.searchInfo.gender,
          position: this.searchInfo.position,
          departId: this.searchInfo.departId
        }
      }).then(teacherPgae => {
        this.tableData = teacherPgae.records;
        this.total = teacherPgae.total;
        console.log(teacherPgae);
      })
    },
    reset() {
      this.pageNum = 1;
      this.searchInfo.teacherId = null;
      this.searchInfo.teacherName = "";
      this.searchInfo.gender = "";
      this.searchInfo.position = "";
      this.searchInfo.departId = null;

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
    handleAdd(){
      this.dialogFormVisible = true;
      this.addInfo = {};
    },
    handleSave(){
      // 弹窗点击确定保存相应的数据
      this.request.post("/teacher",this.addInfo).then(res => {
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    handleDel(row) {
      this.$confirm('此操作将永久删除该教师信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 前端向后端发送信息，删除课程
        this.request.post("/teacher/delete",row).then(res => {
          if(res){
            this.$message.success("删除成功");
            this.dialogFormVisible = false;
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
    handleEdit(row) {
      this.addInfo = row;
      this.dialogFormVisible = true;
    },
    handleSelectionChange(val){
      console.log(val);
      this.multipleSelection = val;
    },
    handleDelMul() {
      this.$confirm('此操作将永久删除这些教师信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 前端向后端发送信息，删除课程
        this.request.post("/teacher/muldelete",this.multipleSelection).then(res => {
          if(res){
            this.$message.success("删除成功");
            this.dialogFormVisible = false;
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
    }
  }
}
</script>

<style scoped>

</style>
