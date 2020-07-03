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
                                            return-object
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
                        <v-row no-gutters>
                            <v-col
                                    v-for="(item, i) in results"
                                    :key="i"
                                    cols="12"
                                    md="6"
                                    class="pa-1"
                            >
                                <v-card tile outlined>
                                    <v-card-title>
                                        {{ item.engineerFio }}
                                    </v-card-title>
                                    <v-card-text>
                                        <v-simple-table>
                                            <template v-slot:default>
                                                <thead>
                                                <tr>
                                                    <th class="text-left">Тип события</th>
                                                    <th class="text-left">Время в минутах</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <tr v-for="it in item.eventTypes" :key="it.eventTypeId">
                                                        <td>{{ it.eventTypeName }}</td>
                                                        <td>{{ it.sum }}</td>
                                                    </tr>
                                                    <tr :style="{ background: item.quarterSum < 0 ? '#ffa4a2' : '#e0f2ce'}">
                                                        <td>Итого</td>
                                                        <td>{{ item.quarterSum }}</td>
                                                    </tr>
                                                </tbody>
                                            </template>
                                        </v-simple-table>
                                    </v-card-text>
                                </v-card>
                            </v-col>
                        </v-row>
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
              results: [],
              quartersList: [
                  { id: 1, name: "I" },
                  { id: 2, name: "II" },
                  { id: 3, name: "III" },
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
                    await this.$axios
                        .get('/api/results/list', {
                            params: {
                                year: Number.parseInt(this.filterItem.year.name),
                                quarter: this.filterItem.quarter
                            }
                        })
                        .then(response => {
                            this.results = response.data.results;
                        })
                        .finally(() => {
                            this.loading = false;
                        });
                }
            },
        }
    }
</script>

<style scoped>

</style>