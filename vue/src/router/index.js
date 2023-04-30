import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";
import Layout from '../views/Layout.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    redirect: "/Login"
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/admin',
    name: 'adminHome',
    component: Layout,
    children:[
      {
        path: 'home',
        name: 'home',
        component: () => import('../views/adminHome.vue'),
        meta:{
          title: "首页"
        },
      },
      {
        path: 'class',
        name: 'class',
        component: () => import('../views/ClassManage/ClassTable.vue'),
        meta: {
          title: "课程管理"
        }
      },
      {
       path: 'openclass',
       name:'openclass',
       component: () => import('../views/ClassManage/OpenClass.vue'),
       meta: {
         title: "已分配课程"
       }
      },
      {
        path: 'assignClass',
        name:'assignClass',
        component: () => import('../views/ClassManage/AssignCourses.vue'),
        meta: {
          title: "分配课程"
        }
      },
      {
        path: 'teacher',
        name: 'teacher',
        component: () => import('../views/TeacherManage/TeacherDetails.vue'),
        meta: {
          title: "教师信息"
        }
      },
      {
        path: 'situation',
        name: 'sitution',
        component: () => import('../views/TeacherManage/TeacherSituation.vue'),
        meta: {
          title: "教师授课情况"
        }
      },
      {
        path: 'student',
        name: 'student',
        component: () => import('../views/StudentManage/StudentDetails.vue'),
        meta: {
          title: "学生信息"
        }
      },
      {
        path: 'test',
        name: 'test',
        component: () => import('../views/StudentManage/Test.vue'),
        meta: {
          title: "正在开发中"
        }
      }

    ]
  },
  {
    path: '/student',
    name: 'studentHome',
    component: () => import('../views/studentViews/Home.vue'),
    meta:{title: "首页"},
    children:[
      {
        path: 'selectClass',
        name: 'selectClass',
        component: () => import('../views/studentViews/selectClass.vue'),
        meta:{
          title: "选课"
        },
      },
      {
        path: 'dropClass',
        name: 'dropClass',
        component: () => import('../views/studentViews/dropClass.vue'),
        meta: {
          title: "退课"
        }
      },
      {
        path: 'searchClassTable',
        name:'searchClassTable',
        component: () => import('../views/studentViews/searchClassTable.vue'),
        meta: {
          title: "课表查询"
        }
      },
      {
        path: 'ranking',
        name: 'ranking',
        component: () => import('../views/studentViews/queryResults.vue'),
        meta: {
          title: "选课排名查询"
        }
      }

    ]
  },
  {
    path: '/teacher',
    name: 'teacherHome',
    component: () => import('../views/teacherViews/Home.vue'),
    meta:{title: "首页"},
    children: [
      {
        path: 'myClass',
        name: 'myClass',
        component: () => import('../views/teacherViews/myClass.vue'),
        meta:{
          title: "我的课程"
        },
      },
      {
        path: 'registScore',
        name: 'registScore',
        component: () => import('../views/teacherViews/registScore.vue'),
        meta:{
          title: "分数登记"
        },
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  console.log(from)
  localStorage.setItem("currentPathName",to.name)  //设置当前路由名称,为了在header组件中使用
  store.commit("setPath")  //触发store数据更新
  next()   //路由放行
})

export default router
