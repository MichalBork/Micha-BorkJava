import { createRouter, createWebHistory } from "vue-router";
import HomeDemo from '../components/Home.vue';
import CalendarDemo from '../components/Calendar.vue';
import LoginDemo from '../components/Logger.vue';


export const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name:'Login',
      component: LoginDemo
    },

    {
      path: "/calendar",
        name:'Calendar',
      beforeEnter: (to, from, next) => {

        localStorage.getItem('logged') === 'true' && localStorage.getItem('role') === 'admin' ? next() : next('/') ;
       },
      component: CalendarDemo
    },
    {
      path: "/calc",
        name:'Calc',
      beforeEnter: (to, from, next) => {

        localStorage.getItem('logged') === 'true' && localStorage.getItem('role') === 'user' ? next('/calc') : next('/') ;
      },
      component: HomeDemo
    },
  ]
})

;
