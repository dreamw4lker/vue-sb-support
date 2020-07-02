<template>
    <v-container>
        <v-layout wrap>
            <v-flex xs12>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Список событий</span>
                    </v-card-title>
                    <v-card-text>
                        <v-form ref="filterForm" @keyup.native.enter="applyFilter" onSubmit="return false;">
                            <v-layout wrap>
                                <v-flex xs12 sm4 class="pt-4">
                                    <lynx-date-picker
                                            :dense="true"
                                            :date="filterItem.dateFrom"
                                            label="Дата (от)"
                                            v-on:update="filterItem.dateFrom = $event"
                                            :rules="rules.date">
                                    </lynx-date-picker>
                                </v-flex>
                                <v-flex xs12 sm4 class="pt-4 pl-2">
                                    <lynx-date-picker
                                            :dense="true"
                                            :date="filterItem.dateTo"
                                            label="Дата (до)"
                                            v-on:update="filterItem.dateTo = $event"
                                            :rules="rules.date">
                                    </lynx-date-picker>
                                </v-flex>
                                <v-flex xs12 sm4 class="text-xs-center text-sm-right">
                                    <v-btn color="primary" @click="applyFilter" class="mt-4">
                                        Выбрать
                                    </v-btn>
                                </v-flex>
                            </v-layout>
                        </v-form>
                        <v-data-table
                                fixed-header
                                :headers="headers"
                                :items="events"
                                :loading="loading"
                                :footer-props="{
                                    itemsPerPageOptions: [-1],
                                    prevIcon: null,
                                    nextIcon: null,
                                }">
                            <template v-slot:item="props">
                                <tr>
                                    <td>{{ $moment(props.item.createDate).format("DD.MM.YYYY HH:mm:ss") }}</td>
                                    <td>{{ $moment(props.item.eventDate).format("DD.MM.YYYY") }}</td>
                                    <td>{{ props.item.fio }}</td>
                                    <td>{{ props.item.timeSpent }}</td>
                                    <td>{{ props.item.eventType }}</td>
                                    <td style="max-width: 500px">{{ props.item.description }}</td>
                                </tr>
                            </template>
                            <template v-slot:no-data>
                                <span>Нет событий</span>
                            </template>
                        </v-data-table>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import DatePicker from "../../date-picker/DatePicker";

    export default {
        data() {
            return {
                loading: false,
                filterItem: {
                    dateFrom: null,
                    dateTo: null,
                },
                rules: {
                    date: [
                        v => v !== null || 'Необходимо указать дату',
                        v => (v && v.replace(/\D/g, '').length === 8) || 'Укажите корректную дату'
                    ]
                },
                events: [],
                headers: [
                    {text: "Дата ответа", value: 'createDate', sortable: false},
                    {text: "Дата события", value: 'eventDate', sortable: false},
                    {text: "Инженер", value: 'fio', sortable: false},
                    {text: "Затраченное время", value: 'timeSpent', sortable: false},
                    {text: "Тип события", value: 'eventType', sortable: false},
                    {text: "Краткое описание", value: 'description', sortable: false},
                ]
            }
        },
        components: {
            'lynx-date-picker': DatePicker,
        },
        methods: {
            async applyFilter() {
                if (this.$refs.filterForm.validate()) {
                    this.loading = true;
                    await this.$axios
                        .get('/api/event/list', {
                            params: {
                                dateFrom: this.filterItem.dateFrom,
                                dateTo: this.filterItem.dateTo
                            }
                        })
                        .then(response => {
                            this.events = response.data.events;
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