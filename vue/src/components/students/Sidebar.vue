<template>
  <el-menu :default-openeds="['1', '3']" style="height: 100%; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff "
           active-text-color="#ffd04b"
           :collapse-transition="false "
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="@/icons/svg/TeachMangement.svg" alt="" style="width: 40px; height: 40px; position: relative; top: 12px; margin-right: 20px">
      <b style="color: aliceblue " v-show="logoTextShow">教务管理系统</b>
    </div>

    <!--第一组 主页-->
    <el-submenu index="1">

      <template slot="title">
        <i class="el-icon-s-promotion"></i>
        <span slot="title">学生选课</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/student/selectClass">
          <i class="el-icon-s-management"></i>选课
        </el-menu-item>
        <el-menu-item index="/student/dropClass">
          <i class="el-icon-s-release"></i>退课
        </el-menu-item>
        <el-menu-item index="/student/searchClassTable">
          <i class="el-icon-date"></i>课表查询
        </el-menu-item>
        <el-menu-item index="/student/ranking">
          <i class="el-icon-search"></i>成绩查询
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Sidebar",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean,
    collapse: ""
  },

}
</script>

<style scoped>

</style>
