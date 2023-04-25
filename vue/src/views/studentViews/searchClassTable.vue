<template>
  <div>
    <h1>本学期课程安排</h1>
    <el-table :data="testClass" border stripe header-cell-class-name="headerBg">
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
      <el-table-column prop="time" label="上课时间"></el-table-column>
      <el-table-column prop="location" label="上课地点"></el-table-column>
      <el-table-column prop="questionTime" label="答疑时间"></el-table-column>
      <el-table-column prop="school" label="校区"></el-table-column>
    </el-table>
    <br>
    <h1>本学期课表</h1>
    <!-- 页面所展示的表格-->
    <el-table :data="tableData" border stripe header-cell-class-name="headerBg">
      <el-table-column prop="orderNumber" label="#" width="40px"></el-table-column>
      <el-table-column prop="time" label="上课时间" width="100px"></el-table-column>
      <el-table-column prop="weekArray.monday" label="一"></el-table-column>
      <el-table-column prop="weekArray.tuesday" label="二"></el-table-column>
      <el-table-column prop="weekArray.wednesday" label="三"></el-table-column>
      <el-table-column prop="weekArray.thursday" label="四"></el-table-column>
      <el-table-column prop="weekArray.friday" label="五"></el-table-column>
      <el-table-column prop="weekArray.saturday" label="六"></el-table-column>
      <el-table-column prop="weekArray.sunday" label="日"></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "searchClassTable",
  data(){
    return {
      testData: ["三1-3,四3-4", "五1-4,二7-10,一1-12"],
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
      classDetail: [],
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
      ]
    }
  },
  created() {
    this.handleData();
  },
  methods: {
    handleData(){
      for (let i=0; i < this.testData.length; i++){
        let parts = this.testData[i].split(",");
        this.subHandleData(parts);
      }
    },
    subHandleData(parts){
      for (let j = 0; j < parts.length; j++){
        let subparts = parts[j].split("-");
        this.fillData(subparts[0].charAt(0), subparts[0].charAt(1), subparts[1]);
      }
    },
    fillData(day, begin, end){
      //debugger
      console.log("分离后的数据为:"+" "+day+" "+begin+" "+end);
      switch (day) {
        case '一': {
          console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            console.log(i);
            this.tableData[i-1].weekArray.monday = "A";
          }
          break;
        }
        case '二': {
          console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            console.log(i);
            this.tableData[i-1].weekArray.tuesday = "A";
          }
          break;
        }
        case '三': {
          console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            console.log(i);
            this.tableData[i-1].weekArray.wednesday = "A";
          }
          break;
        }
        case '四': {
          console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            console.log(i);
            this.tableData[i-1].weekArray.thursday = "A";
          }
          break;
        }
        case '五': {
          console.log(day);
          for (let i = parseInt(begin); i <= parseInt(end); i++){
            console.log(i);
            this.tableData[i-1].weekArray.friday = "A";
          }
          break;
        }
      }
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
.headerBg{
  background: #ccc !important;
}
</style>
