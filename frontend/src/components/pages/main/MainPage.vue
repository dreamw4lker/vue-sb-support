<template>
    <v-container>
        <v-layout wrap>
            <v-flex xs12>
                <v-form ref="sendForm">
                    <v-card>
                        <v-card-title>
                            <span class="text-h5">Техническая поддержка LynxSoft</span>
                        </v-card-title>
                        <v-card-text>
                            <v-flex xs12>
                                <v-select
                                        :items="engineersList"
                                        v-model="editedItem.engineer"
                                        label="Инженер"
                                        item-text="fio"
                                        item-value="id"
                                        :rules="rules.engineer"
                                        no-data-text="Список инженеров пуст"
                                        outlined
                                ></v-select>
                            </v-flex>
                            <v-flex xs12 class="pt-4">
                                <v-menu
                                        v-model="menu"
                                        :close-on-content-click="false"
                                        :nudge-right="40"
                                        transition="scale-transition"
                                        offset-y
                                        min-width="290px"
                                >
                                    <template v-slot:activator="{ on, attrs }">
                                        <v-text-field
                                                v-model="formattedEventDate"
                                                label="Дата"
                                                append-icon="event"
                                                readonly
                                                v-bind="attrs"
                                                v-on="on"
                                                :rules="rules.eventDate"
                                                outlined
                                        ></v-text-field>
                                    </template>
                                    <v-date-picker color="green darken-1" v-model="editedItem.eventDate" @input="menu = false"></v-date-picker>
                                </v-menu>
                            </v-flex>
                            <v-flex xs12 class="pt-4">
                                <v-text-field
                                    v-model="editedItem.timeSpent"
                                    label="Затрачено времени (в минутах)"
                                    :rules="rules.timeSpent"
                                    type="number"
                                    hint="Менее 10 минут не учитываем"
                                    persistent-hint
                                    validate-on-blur
                                    outlined>
                                </v-text-field>
                            </v-flex>
                            <v-flex xs12 class="pt-4">
                                <v-select
                                        :items="eventTypeList"
                                        v-model="editedItem.eventType"
                                        label="Тип события"
                                        item-text="eventType"
                                        item-value="id"
                                        :rules="rules.eventType"
                                        hint="Переработка выставляется в случае производственной необходимости! Отработка выставляется в случае добровольной траты личного времени на производственные нужды"
                                        persistent-hint
                                        no-data-text="Список типов событий пуст"
                                        outlined>
                                </v-select>
                            </v-flex>
                            <v-flex xs12 class="pt-4">
                                <v-select
                                        :items="placesList"
                                        v-model="editedItem.place"
                                        label="Место"
                                        item-text="placeName"
                                        item-value="id"
                                        :rules="rules.place"
                                        no-data-text="Список мест пуст"
                                        outlined>
                                    </v-select>
                            </v-flex>
                            <v-flex xs12 class="pt-4">
                                <v-textarea
                                        v-model="editedItem.description"
                                        label="Описание (кратко)"
                                        :rules="rules.description"
                                        counter="255"
                                        outlined>
                                </v-textarea>
                            </v-flex>
                        </v-card-text>
                        <v-card-actions class="px-4 pb-4 text-right">
                            <lynx-submit-button
                                    :loading="loading"
                                    @submit="submit"
                                    text="Отправить"/>
                        </v-card-actions>
                    </v-card>
                </v-form>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import SubmitButton from "../../button/SubmitButton";

    export default {
        components: {
            'lynx-submit-button': SubmitButton
        },
        data () {
            return {
                engineersList: [],
                eventTypeList: [],
                placesList: [],

                menu: false,
                loading: false,

                editedItem: {
                    engineer: null,
                    eventDate: null,
                    timeSpent: null,
                    eventType: null,
                    place: null,
                    description: null,
                },

                rules: {
                    engineer: [
                        v => v !== null || 'Необходимо выбрать инженера',
                    ],
                    eventDate: [
                        v => v !== null || 'Необходимо указать дату'
                    ],
                    timeSpent: [
                        v => (v !== null && v !== '') || 'Необходимо указать затраченное время',
                        v => (v && v >= 10) || 'Необходимо указать 10 минут или более'
                    ],
                    eventType: [
                        v => v !== null || 'Необходимо выбрать тип события',
                    ],
                    place: [
                        v => v !== null || 'Необходимо выбрать место',
                    ],
                    description: [
                        v => (v !== null && v !== '') || 'Необходимо указать описание',
                        v => (v && v.length <= 255) || 'Максимальная длина описания: 255 символов'
                    ],
                }
            }
        },
        computed: {
            formattedEventDate () {
                if (!this.editedItem.eventDate) return null;

                const [year, month, day] = this.editedItem.eventDate.split('-')
                return `${day}.${month}.${year}`
            }
        },
        mounted() {
            this.loading = true;
            this.getEngineersList().then(() => {
                this.getEventTypeList().then(() => {
                    this.getPlacesList().then(() => {
                        this.loading = false;
                    })
                })
            });
        },
        methods: {
            async getEngineersList() {
                await this.$axios
                        .get('/api/select/engineers')
                        .then(response => {
                            this.engineersList = response.data.engineers;
                        });
            },
            async getEventTypeList() {
                await this.$axios
                    .get('/api/select/event_types')
                    .then(response => {
                        this.eventTypeList = response.data.eventTypes;
                    });
            },
            async getPlacesList() {
                await this.$axios
                    .get('/api/select/places')
                    .then(response => {
                        this.placesList = response.data.places;
                    });
            },

            submit() {
                if (this.$refs.sendForm.validate()) {
                    this.loading = true;
                    this.$axios
                        .post('/api/event/create', {
                            "engineerId": this.editedItem.engineer,
                            "eventDate": this.editedItem.eventDate,
                            "timeSpent": this.editedItem.timeSpent,
                            "eventTypeId": this.editedItem.eventType,
                            "placeId": this.editedItem.place,
                            "description": this.editedItem.description
                        })
                        .then((response) => {
                            if (response.data === true) {
                                alert("Ответ записан. Спасибо");
                                this.$eventBus.$emit('change-page', '/main');
                            } else {
                                alert("Ответ не записан. Повторите попытку позднее");
                                console.error(response);
                            }
                        })
                        .catch((err) => {
                            alert("Ответ не записан. Повторите попытку позднее");
                            console.error(err);
                        })
                        .finally(() => {
                            this.loading = false;
                        });
                }
            }
        }
    }
</script>

<style scoped>

</style>