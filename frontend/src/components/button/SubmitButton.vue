<template>
  <div>
    <v-btn
      :loading="loading"
      :disabled="loading"
      color="primary"
      dark
      @click="emitSubmit">{{ text }}
    </v-btn>
    <v-overlay
      v-if="overlayable"
      :value="loading">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </div>
</template>

<script>
  export default {
    name: "lynx-submit-button",
    props: {
      loading: Boolean,
      text: {
        type: String,
        default: function () {
          return "Сохранить"
        }
      },
      overlayable: {
        type: Boolean,
        default: false
      }
    },
    methods: {
      emitSubmit() {
        this.$emit('submit');
      }
    }
  }
</script>

<style scoped>
  .theme--dark.v-btn.v-btn--disabled:not(.v-btn--icon):not(.v-btn--flat):not(.v-btn--outline) {
    background-color: rgba(67, 160, 71, 0.7) !important;
  }

  .theme--dark.v-btn.v-btn--disabled .v-btn__loading {
    color: aliceblue !important;
  }
</style>