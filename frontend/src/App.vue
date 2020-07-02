<template>
  <v-app>
    <div v-if="$router.currentRoute.path !== '/login'">
      <v-toolbar color="primary" height="36px">
        <v-btn text tile color="accent" @click="changePage('/main')">
          Главная
        </v-btn>
        <v-divider vertical inset color="white"></v-divider>
        <v-btn text tile color="accent" @click="changePage('/events')">
          События
        </v-btn>
        <v-divider vertical inset color="white"></v-divider>
        <v-btn text tile color="accent" @click="changePage('/results')">
          Итоги
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn
                @click="logout"
                text
                color="accent"
        >
          <span class="mr-2">Выход</span>
          <v-icon>{{ icons.mdiExitToApp }}</v-icon>
        </v-btn>
      </v-toolbar>
    </div>
    <router-view :key="timestampKey"></router-view>
  </v-app>
</template>

<script>
  import { mdiExitToApp } from '@mdi/js'

  export default {
    name: 'App',
    data() {
      return {
        icons: {
          mdiExitToApp
        },
        timestampKey: null,
      }
    },
    created: function () {
      let session = this.$cookie.get(this.$sessionCookieName);
      if (!session) {
        if (this.$router.currentRoute.path !== '/login') {
          this.$router.push({path: '/login'});
        }
      }
      this.timestampKey = new Date().getTime();
    },
    mounted() {
      this.$eventBus.$on('change-page', (payload) => {
        this.changePage(payload, false);
      });
    },
    updated: function () {
      if (!this.$cookie.get(this.$sessionCookieName)) {
        if (this.$router.currentRoute.path !== '/login') {
          this.$router.push({path: '/login'});
        }
      }
    },
    beforeDestroy() {
      this.$eventBus.$off('change-page');
    },
    methods: {
      changePage: function (href) {
        if (this.$router.currentRoute.path === href) {
          this.timestampKey = new Date().getTime();
        } else {
          this.$router.push({path: href});
        }
      },
      logout: function () {
        this.$cookie.delete(this.$sessionCookieName);
        if (this.$router.currentRoute.path !== '/login') {
          this.$router.push({path: '/login'});
        }
      },
    }
  };
</script>

<style>
  @import '../public/css/material-icons.css';
</style>