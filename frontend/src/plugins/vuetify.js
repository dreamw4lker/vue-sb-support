import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import ru from 'vuetify/lib/locale/ru'

Vue.use(Vuetify);

export default new Vuetify({
  lang: {
    locales: { ru },
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
