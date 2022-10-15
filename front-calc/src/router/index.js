import { createRouter, createWebHistory } from "vue-router";
import HomeDemo from '../components/Home.vue';
import CalendarDemo from '../components/Calendar.vue';


export const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: HomeDemo
    },

    {
      path: "/calendar",
      component: CalendarDemo
    },
  ]
});
