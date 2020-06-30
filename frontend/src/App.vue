<template>
  <v-app>
    <router-view :key="$route.fullPath"></router-view>
  </v-app>
</template>

<script>
  export default {
    name: 'App',
    created: function () {
      let session = this.$cookie.get(this.$sessionCookieName);
      if (!session) {
        if (this.$router.currentRoute.path !== '/login') {
          this.$router.push({path: '/login'});
        }
      }
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
          this.$router.replace({path: href + '/#update'});
          this.$nextTick(() => (this.$router.replace({path: href})))
        } else {
          this.$router.push({path: href});
        }
      },
      logout: function () {
        this.$cookie.delete(this.$sessionCookieName);
        this.$nextTick(() => (this.$router.push({path: '/login'})));
      },
    }
  };
</script>

<style>
  @import '../public/css/material-icons.css';
</style>
