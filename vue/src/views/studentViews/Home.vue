<template>
  <div class="home" style="height: 100%">
    <el-container style="height: 100%; ">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); height: 100%; box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
          <Sidebar :isCollapse="isCollapse" :logoTextShow="logoTextShow "/>
      </el-aside>
      <el-container>

        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>
        </el-header>

        <!--主界面路由组件-->
        <el-main>
          <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>

// @ is an alias to /src
import Sidebar from "@/components/students/Sidebar.vue";
import Header from "@/components/students/Header.vue";

export default {
  name: 'Home',
  components:{
    Sidebar,
    Header
  },
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
    }
  },
  created() {

  },
  methods: {
    collapse() { // 点击触发函数
      this.isCollapse = ! this.isCollapse
      if (this.isCollapse) {
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    }
  }
}
</script>
