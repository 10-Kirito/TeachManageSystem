<template>
  <div style=" line-height: 60px; display: flex">
    <div style="flex:1; font-size: 18px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>

      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <!--<el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>-->
        <!--<el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>-->
        <el-breadcrumb-item v-for="breadItem in breadCrumbList" :key="breadItem.path" :to="{ path: breadItem.path}">
          {{breadItem.meta.title}}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 80px; cursor: pointer">
      <span style="font-size: 18px">王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <el-dropdown-item>设置</el-dropdown-item>
        <router-link to="/login" style="text-decoration: none; margin-left: 30px">退出</router-link>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: "",
    path:'/'
    // user: Object,
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;
    },
    breadCrumbList(){
      return this.$route.matched;
    }
  },
  watch:{
    currentPathName(newVal,oldVal){
      console.log(newVal)
    }
  },
  mounted() {
    console.log(this.$route)
  }
}
</script>

<style scoped>

</style>
