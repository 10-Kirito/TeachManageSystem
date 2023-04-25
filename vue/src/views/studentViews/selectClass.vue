<template>
  <div>
    <h1>查询条件:</h1>
    <br>

    <div style="margin: 10px 0">
      <el-input style="width: 290px;" suffix-icon="el-icon-search" placeholder="请输入课程号" v-model="searchInfo.classId"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程名" v-model="searchInfo.className"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程学分" v-model="searchInfo.classScore"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="请输入课程学时" v-model="searchInfo.classTime"></el-input>
      <el-input style="width: 290px ; margin-left: 5px" suffix-icon="el-icon-search" placeholder="课程所属院系" v-model="searchInfo.departName"></el-input>
      <el-button style="margin-left: 5px; width: 90px" type="primary">搜索</el-button>
      <el-button style="margin-left: 5px; width: 90px" type="danger">重置</el-button>
    </div>

    <!-- 可选课程显示-->
    <el-table :data="testClass" :row-style="{height: '30px'}" border stripe="headerBg" :header-cell-style="{background:'#ADD8E6',color:'#606266'}">
      <el-table-column type="selection" width="55"></el-table-column>
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
    <el-row>
      <el-col :span="18">
        <div class="grid-content">
        当前页:3, 总页数:10, 总行数:100
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content">
          <el-pagination
              background
              layout="prev, pager, next"
              :total="1000">
          </el-pagination>
        </div>
      </el-col>
    </el-row>
    <br>
    <!-- 本学期已选课程-->
    <el-row>
      <el-col :span="14">
        <div class="grid-content bg-purple">
          <el-table :data="testClass" border stripe ="headerBg" :header-cell-style="{background:'#ADD8E6',color:'#606266'}">
            <el-table-column
                type="index"
                :index="indexMethod"
                label="#"
                width="40px">
            </el-table-column>
            <el-table-column prop="className" label="课程名"></el-table-column>
            <el-table-column prop="classId" label="课程号" ></el-table-column>
            <el-table-column prop="teacherId" label="教师号"></el-table-column>
            <el-table-column prop="score" label="学分"></el-table-column>
          </el-table>


        </div>
      </el-col>
      <el-col :span="10">
        <div class="grid-content bg-purple-light">
          <el-table :data="tableData" border stripe ="headerBg" :header-cell-style="{background:'#ADD8E6',color:'#606266'}">
            <el-table-column prop="orderNumber" label="#" width="40px"></el-table-column>
            <el-table-column prop="time" label="上课时间" width="100px"></el-table-column>
            <el-table-column prop="weekArray.monday" label="一" width="80px"></el-table-column>
            <el-table-column prop="weekArray.tuesday" label="二" width="80px"></el-table-column>
            <el-table-column prop="weekArray.wednesday" label="三" width="80px"></el-table-column>
            <el-table-column prop="weekArray.thursday" label="四" width="80px"></el-table-column>
            <el-table-column prop="weekArray.friday" label="五" width="80px"></el-table-column>
            <el-table-column prop="weekArray.saturday" label="六" width="75px"></el-table-column>
            <el-table-column prop="weekArray.sunday" label="日" width="76px"></el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  name: "selectClass",
  data(){
    return{
      searchInfo: {
        classId :null,
        className : null,
        classScore : null,
        classTime: null,
        departName: null
      },
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
        this.subhandleData(parts);
      }
    },
    subhandleData(parts){
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
