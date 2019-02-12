import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/update/:id',
      name: 'update',
      component: () => import(/* webpackChunkName: "update" */ './views/Update.vue')
    },
    {
      path: '/newAdvert',
      name: 'newAdvert',
      component: () => import(/* webpackChunkName: "new advert" */ './views/NewAdvert.vue')
    },
    {
      path: '/view',
      name: 'view',
      component: () => import(/* webpackChunkName: "view advert" */ './views/Adverts.vue')
    }
  ],
  mode: 'history'
})
