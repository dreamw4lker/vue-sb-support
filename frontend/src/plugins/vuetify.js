import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

export default new Vuetify({
  lang: {
    current: 'ru',
  },
  icons: {
    iconfont: 'mdiSvg',
  },
  theme: {
    themes: {
      light: {
        primary: '#43A047',
        accent: '#ffffff'
      },
    },
  },
});
