<template>
    <v-container>
        <v-layout wrap>
            <v-flex xs12>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Итоги по кварталам</span>
                    </v-card-title>
                    <v-card-text>
                        <v-form ref="filterForm">
                            <v-layout wrap>
                                <v-flex xs12 sm4>
                                    <v-select
                                            :items="yearsList"
                                            v-model="filterItem.year"
                                            label="Год"
                                            item-text="name"
                                            item-value="id"
                                            :rules="rules.year"
                                            no-data-text="Список годов пуст"
                                            outlined
                                            dense
                                    ></v-select>
                                </v-flex>
                                <v-flex xs12 sm4 class="pl-2">
                                    <v-select
                                            :items="quartersList"
                                            v-model="filterItem.quarter"
                                            label="Квартал"
                                            item-text="name"
                                            item-value="id"
                                            :rules="rules.quarter"
                                            outlined
                                            dense
                                    ></v-select>
                                </v-flex>
                                <v-flex xs12 sm4 class="text-xs-center text-sm-right">
                                    <v-btn color="primary" @click="applyFilter">
                                        Выбрать
                                    </v-btn>
                                </v-flex>
                            </v-layout>
                        </v-form>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        data () {
          return {
              loading: false,
              yearsList: [],
              quartersList: [
                  { id: 1, name: "I" },
                  { id: 2, name: "II" },
                  { id: 3, name: "II" },
                  { id: 4, name: "IV" },
              ],
              filterItem: {
                  year: null,
                  quarter: null
              },
              rules: {
                  year: [
                      v => v !== null || 'Необходимо выбрать год',
                  ],
                  quarter: [
                      v => v !== null || 'Необходимо выбрать квартал',
                  ]
              }
          }
        },
        mounted () {
            this.loading = true;
            this.getYearsList();
        },
        methods: {
            async getYearsList() {
                await this.$axios
                    .get('/api/select/years')
                    .then(response => {
                        this.yearsList = response.data.years;
                    })
                    .finally(() => {
                        this.loading = false;
                    });
            },
            async applyFilter() {
                if (this.$refs.filterForm.validate()) {
                    this.loading = true;
                    // fetch ...
                    this.loading = false;
                }
            },
        }
    }
</script>

<style scoped>

</style>