import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import eventBus from './plugins/EventBus'
import VueCookie from 'vue-cookie'
import router from './router'
import vuetify from './plugins/vuetify';
import VueJWT from 'vuejs-jwt'
import VueMoment from 'vue-moment';

Vue.prototype.$eventBus = eventBus;

Vue.config.productionTip = false;

Vue.use(VueCookie);
Vue.use(VueJWT);
Vue.use(VueMoment);

const sessionCookieName = 'JWT_SESSION';
Vue.prototype.$sessionCookieName = sessionCookieName;
axios.defaults.withCredentials = true;

axios.interceptors.response.use(function (response) {
    return response;
  }, function (error) {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          VueCookie.delete(sessionCookieName);
          if (router.currentRoute.path !== '/login') {
            router.push({path: '/login'})
          } else {
            console.error("Error 401");
          }
          break;
        case 400:
        case 403:
        case 404:
        case 410:
        case 411:
          console.error("Error " + error.response.status);
          break;
        case 500:
          console.error("Error 500. Внутренняя ошибка сервера");
          break;
        case 502:
        case 503:
        case 504:
          console.error("Error " + error.response.status + ". Сервер недоступен");
          break;
        default:
          console.error("Error " + error.response.status);
      }
    } else {
      console.error("Неизвестная ошибка");
    }
    return Promise.reject(error);
  }
);

axios.interceptors.request.use(function (config) {
  switch (config.method) {
    case 'post':
    case 'put':
    case 'delete':
      config.headers.common['Csrf-Token'] = VueCookie.get('CSRF_TOKEN');
      //break statement should NOT be placed here!
    case 'get':
      if (VueCookie.get(sessionCookieName)) {
        config.headers.common['Authorization'] = 'Bearer ' + VueCookie.get(sessionCookieName);
      }
      return config;
    default:
      return config;
  }
}, function (error) {
  return Promise.reject(error);
});

Vue.prototype.$axios = axios;


router.beforeEach((to, from, next) => {
  if (to.path !== '/login' && !VueCookie.get(sessionCookieName)) {
    next('/login')
  } else {
    next()
  }
});

const root = new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app');

