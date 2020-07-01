<script>
  export default {
    name: 'lynx-title',
    props: ['title'],
    watch: {
      title: {
        immediate: true,
        handler() {
          document.title = this.title;
        }
      }
    },
    render() {
      return this
    },
  }
</script>