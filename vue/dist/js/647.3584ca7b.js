"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[647],{8647:function(e,l,a){a.r(l),a.d(l,{default:function(){return o}});var t=function(){var e=this,l=e._self._c;return l("div",[l("h1",[e._v("本学期课程安排")]),l("br"),l("el-table",{attrs:{data:e.classDetail,border:"",stripe:"","header-cell-class-name":"headerBg","header-cell-style":{background:"#ADD8E6",color:"#606266"}}},[l("el-table-column",{attrs:{type:"index",index:e.indexMethod,label:"#",width:"40px"}}),l("el-table-column",{attrs:{prop:"classId",label:"课程号"}}),l("el-table-column",{attrs:{prop:"className",label:"课程名"}}),l("el-table-column",{attrs:{prop:"score",label:"学分"}}),l("el-table-column",{attrs:{prop:"teacherId",label:"教师号"}}),l("el-table-column",{attrs:{prop:"teacherName",label:"教师姓名"}}),l("el-table-column",{attrs:{prop:"time",label:"上课时间"}}),l("el-table-column",{attrs:{prop:"location",label:"上课地点"}}),l("el-table-column",{attrs:{prop:"questionTime",label:"答疑时间"}}),l("el-table-column",{attrs:{prop:"school",label:"校区"}})],1),l("br"),l("h1",[e._v("本学期课表")]),l("br"),l("el-table",{attrs:{data:e.tableData,border:"",stripe:"","header-cell-class-name":"headerBg","header-cell-style":{background:"#ADD8E6",color:"#606266"}}},[l("el-table-column",{attrs:{prop:"orderNumber",label:"#",width:"40px"}}),l("el-table-column",{attrs:{prop:"time",label:"上课时间",width:"100px"}}),l("el-table-column",{attrs:{prop:"weekArray.monday",label:"一"}}),l("el-table-column",{attrs:{prop:"weekArray.tuesday",label:"二"}}),l("el-table-column",{attrs:{prop:"weekArray.wednesday",label:"三"}}),l("el-table-column",{attrs:{prop:"weekArray.thursday",label:"四"}}),l("el-table-column",{attrs:{prop:"weekArray.friday",label:"五"}}),l("el-table-column",{attrs:{prop:"weekArray.saturday",label:"六"}}),l("el-table-column",{attrs:{prop:"weekArray.sunday",label:"日"}})],1)],1)},r=[],s=a(6369),u={name:"searchClassTable",data(){return{user:{birthday:null,departId:null,gender:null,nativePlace:null,phoneNumber:null,state:null,studentId:null,studentName:null},allTime:[],tableData:[{orderNumber:1,time:"8:00-8:45",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:2,time:"8:55-9:40",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:3,time:"10:00-10:45",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:4,time:"10:55-11.40",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:5,time:"13:00-13:45",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:6,time:"13:55-14:40",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:7,time:"15:00-15:45",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:8,time:"15:55-16:40",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:9,time:"18:00-18:45",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:10,time:"18:55-19:40",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:11,time:"20:00-20:45",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}},{orderNumber:12,time:"20:55-21:40",weekArray:{monday:null,tuesday:null,wednesday:null,thursday:null,friday:null,saturday:null,sunday:null}}],classDetail:[],testClass:[{classId:"08305124",className:"计算机系统结构",score:4,teacherId:1001,teacherName:"沈文枫",time:"三1-3,四3-4",location:"材料222",questionTime:"一7-8",school:"宝山"},{classId:"08305124",className:"计算机系统结构",score:4,teacherId:1001,teacherName:"沈文枫",time:"三1-3,四3-4",location:"材料222",questionTime:"一7-8",school:"宝山"},{classId:"08305124",className:"计算机系统结构",score:4,teacherId:1001,teacherName:"沈文枫",time:"三1-3,四3-4",location:"材料222",questionTime:"一7-8",school:"宝山"}],testData:["三1-3,四3-4","五1-4,二7-10,一1-12"]}},async created(){await this.getUser(),await this.getAllClass(),await this.getAllClassTime(),await this.handleData()},methods:{getUser(){const e=JSON.parse(localStorage.getItem("userInfo"));e&&s["default"].set(this,"user",e)},getAllClass(){this.getUser(),this.request.get("/select-class/student/selected",{params:{studentId:this.user.studentId}}).then((e=>{this.classDetail=e.data}))},async getAllClassTime(){await this.request.get("/select-class/student/allTime",{params:{studentId:this.user.studentId}}).then((e=>{this.allTime=e.data}))},handleData(){let e=64;for(let l=0;l<this.allTime.length;l++){let a=this.allTime[l].split("，");e++,this.subHandleData(a,String.fromCharCode(e))}},subHandleData(e,l){for(let a=0;a<e.length;a++){let t=e[a].split("-");this.fillData(t[0].charAt(0),t[0].charAt(1),t[1],l)}},fillData(e,l,a,t){switch(e){case"一":for(let e=parseInt(l);e<=parseInt(a);e++)this.tableData[e-1].weekArray.monday=t;break;case"二":for(let e=parseInt(l);e<=parseInt(a);e++)this.tableData[e-1].weekArray.tuesday=t;break;case"三":for(let e=parseInt(l);e<=parseInt(a);e++)this.tableData[e-1].weekArray.wednesday=t;break;case"四":for(let e=parseInt(l);e<=parseInt(a);e++)this.tableData[e-1].weekArray.thursday=t;break;case"五":for(let e=parseInt(l);e<=parseInt(a);e++)this.tableData[e-1].weekArray.friday=t;break}},indexMethod(e){e++;let l=64+e;return String.fromCharCode(l)}}},n=u,d=a(1001),y=(0,d.Z)(n,t,r,!1,null,"53aa9e78",null),o=y.exports}}]);
//# sourceMappingURL=647.3584ca7b.js.map