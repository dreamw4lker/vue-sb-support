import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginPage from "./components/pages/login/LoginPage";
import MainPage from "./components/pages/main/MainPage";
import EventsPage from "./components/pages/events/EventsPage";
import ResultsPage from "./components/pages/results/ResultsPage";

Vue.use(VueRouter);

const routes = [
  {
    path: '/login',
    component: LoginPage
  },
  {
    path: '/main',
    component: MainPage
  },
  {
    path: '/events',
    component: EventsPage
  },
  {
    path: '/results',
    component: ResultsPage
  },
  {
    path: '*',
    redirect: '/main'
  }
];

const router = new VueRouter({
  mode: 'history',
  routes
});

export default router;