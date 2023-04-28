<template>
  <div>
    <div style="margin: 5px 0">
      <h1>查询条件</h1>
    </div>

    <div style="margin: 10px 0">
      <el-input style="width: 290px ;" suffix-icon="el-icon-search" placeholder="请输入课程号" v-model="searchInfo.classId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程名" v-model="searchInfo.className"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程学分" v-model="searchInfo.classScore"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入教师姓名" v-model="searchInfo.teacherName"></el-input>
      <template>
        <el-select v-model="searchInfo.departName" placeholder="请选择所属学院" style="width: 290px ; margin-left: 5px">
          <el-option
              v-for="item in options"
              :key="item"
              :value="item">
          </el-option>
        </el-select>
      </template>
    </div>

    <div style="margin: 10px 0">
      <el-button style="width: 90px" type="primary" @click="load">查询</el-button>
      <el-button style="margin-left: 5px; width: 90px" type="danger" @click="reset">重置</el-button>
    </div>

    <!-- 可选课程显示-->
    <el-table :data="classData" :row-style="{height: '30px'}"
              :header-cell-style="{background:'#ADD8E6',color:'#606266'}"
              border stripe header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="classId" label="课程号" ></el-table-column>
      <el-table-column prop="className" label="课程名"></el-table-column>
      <el-table-column prop="score" label="学分" width="60px"></el-table-column>
      <el-table-column prop="teacherId" label="教师号" width="80px"></el-table-column>
      <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
      <el-table-column prop="position" label="教师职称"></el-table-column>
      <el-table-column prop="time" label="上课时间"></el-table-column>
      <el-table-column prop="location" label="上课地点"></el-table-column>
      <el-table-column prop="capacity" label="容量" width="60px"></el-table-column>
      <el-table-column prop="enrollment" label="人数" width="60px"></el-table-column>
      <el-table-column prop="questionTime" label="答疑时间"></el-table-column>
      <el-table-column prop="departName" label="所属学院"></el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-button style="width: 90px" type="primary" @click="putData">确定</el-button>
    </div>

    <el-row>
      <el-col :span="18">
        <div class="grid-content">
        当前页:{{pageNum}}, 总页数:{{pageSize}}, 总行数:{{ total }}
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              background
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <!-- 本学期已选课程-->
    <el-row>
      <el-col :span="14">
        <div class="grid-content bg-purple">
          <el-table :data="selectedData" :header-cell-style="{background:'#ADD8E6',color:'#606266'}" border stripe header-cell-class-name="headerBg">
            <el-table-column
                type="index"
                :index="indexMethod"
                label="#"
                width="40px"
                align="center">
            </el-table-column>
            <el-table-column prop="className" label="课程名"></el-table-column>
            <el-table-column prop="classId" label="课程号" width="100px"></el-table-column>
            <el-table-column prop="teacherId" label="教师号" width="100px" align="center"></el-table-column>
            <el-table-column prop="teacherName" label="教师号" width="100px"></el-table-column>
            <el-table-column prop="time" label="教师号"></el-table-column>
            <el-table-column prop="score" label="学分" width="50px" align="center"></el-table-column>
          </el-table>


        </div>
      </el-col>
      <el-col :span="10">
        <div class="grid-content bg-purple-light">
          <el-table :data="tableData" :header-cell-style="{background:'#ADD8E6',color:'#606266'}" border stripe header-cell-class-name="headerBg">
            <el-table-column prop="orderNumber" label="#" width="40px"></el-table-column>
            <el-table-column prop="time" label="上课时间" width="100px"></el-table-column>
            <el-table-column prop="weekArray.monday" label="一" width="80px"></el-table-column>
            <el-table-column prop="weekArray.tuesday" label="二" width="80px"></el-table-column>
            <el-table-column prop="weekArray.wednesday" label="三" width="80px"></el-table-column>
            <el-table-column prop="weekArray.thursday" label="四" width="80px"></el-table-column>
            <el-table-column prop="weekArray.friday" label="五" width="80px"></el-table-column>
            <el-table-column prop="weekArray.saturday" label="六" width="78px"></el-table-column>
            <el-table-column prop="weekArray.sunday" label="日" width="80px"></el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import Vue from "vue";
export default {
  inject:['reload'],
  name: "selectClass",
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
      searchInfo: {
        classId :null,
        className : null,
        classScore : null,
        teacherName: null,
        departName: null
      },
      classData: [],
      selectedData: [],
      allTime: [],
      total:0,
      pageNum:1,
      pageSize:10,
      tableData:[
        {
          orderNumber: 1,
          time: "8:00-8:45",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 2,
          time: "8:55-9:40",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 3,
          time: "10:00-10:45",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 4,
          time: "10:55-11.40",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 5,
          time: "13:00-13:45",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 6,
          time: "13:55-14:40",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 7,
          time: "15:00-15:45",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 8,
          time: "15:55-16:40",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 9,
          time: "18:00-18:45",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 10,
          time: "18:55-19:40",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 11,
          time: "20:00-20:45",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        },
        {
          orderNumber: 12,
          time: "20:55-21:40",
          weekArray: {
            monday: null,
            tuesday: null,
            wednesday: null,
            thursday: null,
            friday: null,
            saturday: null,
            sunday: null
          }
        }
      ],
      testData: ["三1-3,四3-4", "五1-4,二7-10,一1-12"],
      testClass: [
        {
          classId: "08305124",
          className: "计算机系统结构",
          score:4,
          teacherId:1001,
          teacherName: "沈文枫",
          time: "三1-3,四3-4",
          location: "材料222",
          questionTime: "一7-8",
          school: "宝山"
        },
        {
          classId: "08305124",
          className: "计算机系统结构",
          score:4,
          teacherId:1001,
          teacherName: "沈文枫",
          time: "三1-3,四3-4",
          location: "材料222",
          questionTime: "一7-8",
          school: "宝山"
        },
        {
          classId: "08305124",
          className: "计算机系统结构",
          score:4,
          teacherId:1001,
          teacherName: "沈文枫",
          time: "三1-3,四3-4",
          location: "材料222",
          questionTime: "一7-8",
          school: "宝山"
        }
      ],
      options:[],
      multipleSelection: []
    }
  },
  async created() {
    // 获取用户信息
    await this.getUser();
    await this.load();
    // 同步
    await this.getAllClassTime();

    await this.handleData();
  },
  methods: {
    // 1. 获取学生的相关信息
    getUser(){
      const data = JSON.parse(localStorage.getItem('userInfo'))
      if (data) {
        Vue.set(this, 'user', data);
      }
    },
    // 2. 获取所有的院系名称，并存储，制作下拉框;
    //    获取所有的可选的课程信息;
    //    获取该名学生选择的所有的课程信息;
    //    获取该名学生已选课程的上课时间;
    load(){
      // 获取所有的院系名称，并存储
      this.request.get("/department/allName").then(data =>{
        this.options = data;
      })
      // 获取所有的课程表
      this.getClassData();
      // 获取所有的该名学生选择的课程
      this.getAllClass();
    },
    // 同步
    async getAllClassTime(){
      await this.request.get("/select-class/student/allTime",{
        params: {
          studentId: this.user.studentId
        }
      }).then(reponse => {
        this.allTime = reponse.data;
      })
    },
    getAllClass(){
      this.getUser();
      this.request.get("/select-class/student/selected",{
        params: {
          studentId: this.user.studentId
        }
      }).then(reponse => {
        // console.log(reponse);
        this.selectedData = reponse.data;
        // console.log(this.selectedData);
      })
    },
    getClassData(){
      this.request.get("/open-class/pages", {
        params: {
          currentPage: this.pageNum,
          pageSize: this.pageSize,
          classId: this.searchInfo.classId,
          className: this.searchInfo.className,
          teacherName: this.searchInfo.teacherName,
          classScore: this.searchInfo.classScore,
          departName: this.searchInfo.departName
        }
      }).then(classPgae => {
        // console.log(classPgae);
        this.classData = classPgae.records;
        this.total = classPgae.total;
      })
    },
    reset(){
      this.searchInfo = {};
      this.load();
    },
    // 下面的方法handleData()、subHandleData()、fillData()是为了处理学生已经选择的课程，
    // 并将最终的信息放进课程表当中
    handleData(){
      let c = 64;
      for (let i=0; i < this.allTime.length; i++){
        let parts = this.allTime[i].split("，");
        c++;
        this.subHandleData(parts, String.fromCharCode(c));
      }
    },
    subHandleData(parts, CHAR){
      for (let j = 0; j < parts.length; j++){
        let subparts = parts[j].split("-");
        this.fillData(subparts[0].charAt(0), subparts[0].charAt(1), subparts[1], CHAR);
      }
    },
    fillData(day, begin, end, CHAR){
      //debugger
      // console.log("分离后的数据为:"+" "+day+" "+begin+" "+end);
      switch (day) {
        case '一': {
          // console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            this.tableData[i-1].weekArray.monday = CHAR;
          }
          break;
        }
        case '二': {
          // console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            this.tableData[i-1].weekArray.tuesday = CHAR;
          }
          break;
        }
        case '三': {
          // console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            this.tableData[i-1].weekArray.wednesday = CHAR;
          }
          break;
        }
        case '四': {
          // console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            this.tableData[i-1].weekArray.thursday = CHAR;
          }
          break;
        }
        case '五': {
          // console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            this.tableData[i-1].weekArray.friday = CHAR;
          }
          break;
        }
      }
    },
    // 将第一列的值变为A B C D E F G···
    indexMethod(index){
      // 将表格自带的索引转化为相应的大小的字符，即A,B,C,D,E,F
      index++;
      let c = 64 + index;
      return String.fromCharCode(c);
    },
    // 分页选项相关处理函数
    handleSizeChange(pageSize) {
      // console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      // console.log(`当前页: ${pageNum}`);
      this.pageNum = pageNum
      this.load()
    },
    handleSelectionChange(val){
      // console.log(val);
      this.multipleSelection = val;
    },
    putData(){
      if (this.multipleSelection.length != 0) {
        const encodedUser = encodeURIComponent(JSON.stringify(this.user));
        const encodedData = encodeURIComponent(JSON.stringify(this.multipleSelection));
        this.request.get("/select-class/student/select", {
          params: {
            encodeStudent: encodedUser,
            encodeData: encodedData
          }
        }).then(response => {
          // console.log(response);
          if (response.code == "BAD_REQUEST")
            this.$message.error(response.msg);
          else
            this.$message.success(response.msg);
        })
      }
      this.multipleSelection = [];
      this.reload();
    },
    clearTable(){
      // 该函数的作用是清空已经排好课的课程表
      for (let i = 0; i < 12; i++) {
        this.tableData[i].weekArray = {};
      }
    }
  }
}
</script>

<style scoped>
.headerBg{
  background: #ccc !important;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.bg-purple-light {
  background: #e5e9f2;
}
.bg-purple {
  background: #d3dce6;
}
.tableWidth{
  width: 20px;
}
</style>
