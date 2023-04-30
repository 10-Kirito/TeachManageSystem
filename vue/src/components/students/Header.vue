<template>
  <div style=" line-height: 60px; display: flex">
    <div style="flex:1; font-size: 18px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item v-for="breadItem in breadCrumbList" :key="breadItem.path" :to="{ path: breadItem.path}">
          {{breadItem.meta.title}}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 80px; cursor: pointer" @command="handleCommand">
      <span style="font-size: 18px">{{user.studentName}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="person">个人信息</el-dropdown-item>
        <el-dropdown-item command="setting">设置</el-dropdown-item>
        <el-dropdown-item command="quit">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import Vue from "vue";

export default {
  name: "Header",
  data(){
    return{
      user:{}
    }
  },
  props:{
    collapseBtnClass: String,
    collapse: "",
    path:'/'
  },
  computed:{
    breadCrumbList(){
      return this.$route.matched;
    }
  },
  watch:{
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  methods: {
    handleCommand(command){
      switch (command){
        case "quit":{
          console.log(command);
          localStorage.clear();
          this.$router.push("/");
          break;
        }
        case "person":{
          console.log(command);
          break;
        }
        case "setting":{
          console.log(command);
          break;
        }
      }
    }
  },
  created() {
    const data = JSON.parse(localStorage.getItem('userInfo'))
    if (data) {
      Vue.set(this, 'user', data);
      console.log(this.user);
    }
  }
}
</script>

<style scoped>

</style>
