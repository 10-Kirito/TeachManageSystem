<template>
  <div>
    <h1> 这里是学生退课界面！！</h1>
    <br>

    <el-table :data="classDetail" border stripe header-cell-class-name="headerBg"
              :header-cell-style="{background:'#ADD8E6',color:'#606266'}"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column
          type="index"
          :index="indexMethod"
          label="#"
          width="40px"
          align="center">
      </el-table-column>
      <el-table-column prop="classId" label="课程号" ></el-table-column>
      <el-table-column prop="className" label="课程名"></el-table-column>
      <el-table-column prop="score" label="学分"></el-table-column>
      <el-table-column prop="teacherId" label="教师号"></el-table-column>
      <el-table-column prop="teacherName" label="教师姓名"></el-table-column>
      <el-table-column prop="time" label="上课时间"></el-table-column>
      <el-table-column prop="location" label="上课地点"></el-table-column>
      <el-table-column prop="questionTime" label="答疑时间"></el-table-column>
      <el-table-column prop="school" label="校区"></el-table-column>
    </el-table>

    <div style="margin-top: 10px">
      <el-button type="danger" @click="putData">退课</el-button>
    </div>


    <div class="grid-content bg-purple-light" style="margin-top: 10px">
      <el-table :data="tableData" :header-cell-style="{background:'#ADD8E6',color:'#606266'}" border stripe header-cell-class-name="headerBg">
        <el-table-column prop="orderNumber" label="#" width="45px"></el-table-column>
        <el-table-column prop="time" label="上课时间" ></el-table-column>
        <el-table-column prop="weekArray.monday" label="一"></el-table-column>
        <el-table-column prop="weekArray.tuesday" label="二"></el-table-column>
        <el-table-column prop="weekArray.wednesday" label="三"></el-table-column>
        <el-table-column prop="weekArray.thursday" label="四"></el-table-column>
        <el-table-column prop="weekArray.friday" label="五"></el-table-column>
        <el-table-column prop="weekArray.saturday" label="六"></el-table-column>
        <el-table-column prop="weekArray.sunday" label="日"></el-table-column>
      </el-table>
    </div>



  </div>
</template>

<script>
import Vue from "vue";

export default {
  inject:['reload'],
  name: "dropClass",
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
      classDetail: [],
      multipleSelection: [],
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
      allTime: []
    }
  },
  async created(){
    await this.getAllClass();
    await this.getAllClassTime();
    await this.handleData();
  },
  methods: {
    // 1. 获取当前用户信息
    getUser(){
      const data = JSON.parse(localStorage.getItem('userInfo'))
      if (data) {
        Vue.set(this, 'user', data);
      }
    },
    // 2. 获取所有的已经选择的课程的信息
    async getAllClass(){
      this.getUser();
      await this.request.get("/select-class/student/selected",{
        params: {
          studentId: this.user.studentId
        }
      }).then(reponse => {
        console.log(reponse);
        this.classDetail = reponse.data;
      })
    },
    // 3. 获取所有的课程的上课时间信息
    async getAllClassTime(){
      await this.request.get("/select-class/student/allTime",{
        params: {
          studentId: this.user.studentId
        }
      }).then(reponse => {
        this.allTime = reponse.data;
      })
    },
    handleSelectionChange(val){
      this.multipleSelection = val;
    },
    // 3. 将第一列的值变为A B C D E F G···
    indexMethod(index){
      // 将表格自带的索引转化为相应的大小的字符，即A,B,C,D,E,F
      index++;
      let c = 64 + index;
      return String.fromCharCode(c);
    },
    // 4. 将所有的课程信息填入课程表当中
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
    // 5. 退课接口
    putData(){
      if (this.multipleSelection.length != 0) {
        const encodedUser = encodeURIComponent(JSON.stringify(this.user));
        const encodedData = encodeURIComponent(JSON.stringify(this.multipleSelection));
        this.request.get("/select-class/student/delete", {
          params: {
            encodeStudent: encodedUser,
            encodeData: encodedData
          }
        }).then(response => {
          this.$message.success(response.msg);
          this.reload();
        })
      }else {
        this.multipleSelection = [];
        this.reload();
        this.$message.error("请选择课程！");
      }
    },
  }
}
</script>

<style scoped>

</style>
