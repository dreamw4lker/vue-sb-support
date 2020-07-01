<template>
    <v-container>
        <v-layout wrap>
            <v-flex xs12>
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
                                    outlined
                                    dense
                            ></v-select>
                        </v-flex>
                        <v-flex xs12>
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
                                            dense
                                    ></v-text-field>
                                </template>
                                <v-date-picker color="green darken-1" v-model="editedItem.eventDate" @input="menu = false"></v-date-picker>
                            </v-menu>
                        </v-flex>
                        <v-flex xs12>
                            <v-text-field
                                v-model="editedItem.timeSpent"
                                label="Затрачено времени (в минутах)"
                                :rules="rules.timeSpent"
                                type="number"
                                hint="Менее 10 минут не учитываем"
                                persistent-hint
                                validate-on-blur
                                outlined
                                dense>
                            </v-text-field>
                        </v-flex>
                        <v-flex xs12>
                            <v-select
                                    :items="placesList"
                                    v-model="editedItem.place"
                                    label="Место"
                                    item-text="placeName"
                                    item-value="id"
                                    :rules="rules.place"
                                    outlined
                                    dense
                            ></v-select>
                        </v-flex>
                        <v-flex xs12>
                            <v-textarea
                                    v-model="editedItem.description"
                                    label="Описание (кратко)"
                                    :rules="rules.description"
                                    counter="255"
                                    outlined
                                    dense>
                            </v-textarea>
                        </v-flex>
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        data() {
            return {
                engineersList: [],
                eventTypeList: [],
                placesList: [],

                menu: false,

                editedItem: {
                    engineer: null,
                    eventDate: null,
                    timeSpent: null,
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
                this.getPlacesList().then(() => {
                    this.loading = false;
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

            async getPlacesList() {
                await this.$axios
                    .get('/api/select/places')
                    .then(response => {
                        this.placesList = response.data.places;
                    });
            },
        }
    }
</script>

<style scoped>

</style>