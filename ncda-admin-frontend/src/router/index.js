import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/index'
  },
  {
    path: '/',
    component: () => import('../components/common/Base.vue'),
    meta: {
      title: '公共部分'
    },
    children: [
      {
        path: '/index',
        component: () => import('@/components/page/home/Home.vue'),
        meta: {
          title: '系统首页'
        }
      },
      {
        path: '/chart-simple',
        component: () => import('@/components/page/EChartsSimple.vue'),
        meta: {
          title: '简单图表'
        }
      },
      {
        path: '/chart-complex',
        component: () => import('@/components/page/EChartsComplex.vue'),
        meta: {
          title: '复杂图表'
        }
      },
      {
        path: '/tab',
        component: () => import('../components/page/Tab.vue'),
        meta: {
          title: 'tab选项卡'
        }
      },
      {
        path: '/table',
        component: () => import('../components/page/Table.vue'),
        meta: {
          title: '表格'
        }
      },
      {
        path: '/accountBill',
        component: () => import('@/components/page/accountBill/AccountBill'),
        meta: {
          title: '我的账本'
        }
      },
      {
        path: '/test',
        component: () => import('@/components/page/Test'),
        meta: {
          title: '其他'
        }
      },
      {
        path: '/exchange',
        component: () => import('@/components/page/exchange/Exchange'),
        meta: {
          title: 'exchange'
        }
      }
    ]
  },
  {
    path: '/login',
    component: () => import('../components/page/Login.vue')
  },
  {
    path: '/error',
    component: () => import('../components/page/Error.vue')
  },
  {
    path: '/404',
    component: () => import('../components/page/404.vue')
  },
  {
    path: '/todo/accountBill',
    component: () => import('@/components/page/accountBill/AccountBill'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
