import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from "./components/pages/LoginPage";

Vue.use(VueRouter);

const routes = [
  {
    path: '*',
    component: LoginPage
  }
];

const router = new VueRouter({
  mode: 'history',
  routes
});

export default router;