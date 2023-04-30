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
      <img src="../icons/svg/TeachMangement.svg" alt="" style="width: 40px; height: 40px; position: relative; top: 12px; margin-right: 20px">
      <b style="color: aliceblue " v-show="logoTextShow">教务管理系统</b>
    </div>

    <!--第一组 主页-->
    <el-submenu index="1">

      <template slot="title">
        <i class="el-icon-s-promotion"></i>
        <span slot="title">主页</span>
      </template>
      <el-menu-item-group>
        <template slot="title">信息管理</template>
        <el-menu-item index="1-1">开课申请</el-menu-item>
      </el-menu-item-group>
    </el-submenu>

    <!--第二组 课程-->
    <el-submenu index="2">

      <template slot="title">
        <i class="el-icon-reading"></i>
        <span slot="title">课程管理</span>
      </template>

      <el-menu-item-group>
        <template slot="title">课程信息</template>
        <el-menu-item index="/admin/class">所有课程</el-menu-item>
        <el-menu-item index="/admin/openclass">已分配课程</el-menu-item>
        <el-menu-item index="/admin/assignClass">课程分配</el-menu-item>
      </el-menu-item-group>
    </el-submenu>

    <!--第三组 教师-->
    <el-submenu index="3">

      <template slot="title">
        <i class="el-icon-user-solid"></i>
        <span slot="title">教师管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/admin/teacher">教师信息</el-menu-item>
        <el-menu-item index="/admin/situation">授课情况</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="授课情况">
        <el-menu-item index="3-3">成绩分布</el-menu-item>
        <el-menu-item index="3-3">教师评估</el-menu-item>
      </el-menu-item-group>
    </el-submenu>

    <!--第四组 学生-->
    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-user"></i>
        <span slot="title">学生管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/admin/student">学生信息</el-menu-item>
        <el-menu-item index="/admin/test">正在开发中</el-menu-item>
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
