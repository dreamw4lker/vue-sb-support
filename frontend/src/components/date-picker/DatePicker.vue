<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <v-menu
    v-model="menu"
    :close-on-content-click="false"
    transition="scale-transition"
    offset-y
    max-width="290px"
    min-width="290px"
  >
    <template v-slot:activator="{ on }">
      <v-text-field
        ref="datePicker"
        v-model="computedFormattedDate"
        :label="label"
        :append-icon="icons.mdiCalendar"
        v-on="on"
        :disabled="disabled"
        :class="textFieldClassString ? textFieldClassString : textFieldClass"
        :hide-details="hideDetails"
        :rules="rules"
        :dense="dense"
        outlined
      ></v-text-field>
    </template>
    <v-date-picker
      color="green darken-1"
      locale="ru-RU"
      no-title
      v-model="internalDate"
      @input="$emit('update', internalDate); menu = false"
    >
    </v-date-picker>
  </v-menu>
</template>
<script>
  import moment from 'moment';
  import { mdiCalendar } from '@mdi/js'

  export default {
    data() {
      return {
        icons: {
          mdiCalendar
        },
        menu: false,
        internalDate: this.date,
        internalDateValue: this.date
      }
    },
    props: {
      date: String,
      label: String,
      disabled: {
        type: Boolean,
        default: false
      },
      textFieldClass: {
        type: Object
      },
      textFieldClassString: {
        type: String
      },
      hideDetails: {
        type: Boolean,
        default: false
      },
      dense: {
        type: Boolean,
        default: false
      },
      rules: {
        type: Array,
        default: []
      }
    },
    watch: {
      date(val) {
        this.internalDate = val;
        this.internalDateValue = val
      }
    },
    computed: {
      computedFormattedDate: {
        get: function () {
          return (this.internalDateValue && this.internalDateValue.length === 10) ? moment(this.internalDate).format('DD.MM.YYYY') : this.internalDateValue
        },
        set: function (newValue) {
          if (newValue && newValue.length < 11) {
            if (newValue.length === 2 || newValue.length === 5) {
              this.internalDateValue = newValue + "."
            } else if (newValue.length === 10) {
              let newDate = newValue.substr(6, 4) + '-' + newValue.substr(3, 2) + '-' + newValue.substr(0, 2);
              if (new Date(newDate) != 'Invalid Date') {
                this.internalDate = newDate;
                this.internalDateValue = newDate
              } else {
                this.internalDate = null;
                this.internalDateValue = null;
                this.$refs.datePicker.internalValue = null
              }
              this.$emit('update', this.internalDate)
            } else {
              this.internalDateValue = newValue
            }
          } else {
            this.internalDate = null;
            this.internalDateValue = null;
            this.$emit('update', this.internalDate)
          }
        }
      }
    }
  }
</script>