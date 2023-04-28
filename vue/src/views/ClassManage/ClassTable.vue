<template>
  <div>
    <!-- 输入搜索框-->
    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入课程号" v-model="searchInfo.classId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程名" v-model="searchInfo.className"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程学分" v-model="searchInfo.classScore"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程学时" v-model="searchInfo.classTime"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="课程所属院系" v-model="searchInfo.departName"></el-input>
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
      <el-table-column prop="classId" label="课程号" ></el-table-column>
      <el-table-column prop="className" label="课程名" ></el-table-column>
      <el-table-column prop="classScore" label="学分" width="50px"></el-table-column>
      <el-table-column prop="classTime" label="学时"  width="50px"></el-table-column>
      <el-table-column prop="departName" label="所属院系"></el-table-column>

      <el-table-column width="320px">
        <template slot-scope="scope">
          <el-button type="success" @click="openThisClass(scope.row)">开课 </el-button>
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
    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="课程号">
          <el-input v-model="addInfo.classId" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程名">
          <el-input v-model="addInfo.className" autocomplete="off" />
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="addInfo.classScore" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课时">
          <el-input v-model="addInfo.classTime" autocomplete="off" />
        </el-form-item>
        <el-form-item label="所属院系">
          <!--<el-input v-model="addInfo.departId" autocomplete="off" />-->
          <el-select v-model="addInfo.departName" placeholder="请选择院系">
            <el-option
                v-for="item in options"
                :key="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
              <span class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="handleSave">确认</el-button>
              </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import {options} from "axios";

export default {
  name: "ClassTable",
  data(){
    return {
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      options: [],
      searchInfo: {
        classId :null,
        className : null,
        classScore : null,
        classTime: null,
        departName: null
      },
      dialogFormVisible: false,
      addInfo: {
        classId :null,
        className : null,
        classScore : null,
        classTime: null,
        departName: null
      },
      multipleSelection: []
    }
  },
  created() {
    // 请求分页查询数据
    this.load();

    this.request.get("/department/allName").then(data =>{
      console.log(data);
      this.options = data;
    })
  },
  methods: {
    openThisClass(data){
      this.$confirm('是否开设此课程？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(data);
        this.request.get("/open-class/addOpenClass", {
          params:{
            classRecord: data.id
          }
        }).then(response =>{
          console.log(response);
          if (response.code == 'SUCCESS')
            this.$message.success(response.msg);
          else
            this.$message.error(response.msg);
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消!'
        });
      });
    },
    handleExcelImportSuccess(){
      this.$message.success("文件上传成功！")
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/class/exportAllClass");
    },
    load() {
      this.request.get("/class/page", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          classId: this.searchInfo.classId,
          className: this.searchInfo.className,
          classScore: this.searchInfo.classScore,
          classTime: this.searchInfo.classTime,
          departName: this.searchInfo.departName
        }
      }).then(classPgae => {
        console.log(classPgae);
        this.tableData = classPgae.records;
        this.total = classPgae.total;
      })
    },
    reset() {
      this.pageNum = 1;
      this.searchInfo.classId = null;
      this.searchInfo.className = null;
      this.searchInfo.classScore = null;
      this.searchInfo.classTime = null;
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
      this.request.post("/class",this.addInfo).then(res => {
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
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 前端向后端发送信息，删除课程
        this.request.post("/class/delete",row).then(res => {
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
      this.$confirm('此操作将永久删除这些课程信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 前端向后端发送信息，删除课程
        this.request.post("/class/muldelete",this.multipleSelection).then(res => {
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
.headerBg{
  background: #ccc !important;
}
</style>
