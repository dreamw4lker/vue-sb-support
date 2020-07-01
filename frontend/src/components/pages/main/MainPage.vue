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
                editedItem: {
                    engineer: null,
                },
                rules: {
                    engineer: [
                        v => v !== null || 'Необходимо выбрать инженера',
                    ],
                }
            }
        },
        mounted() {
            this.getEngineersList();
        },
        methods: {
            async getEngineersList() {
                this.loading = true;
                await this.$axios
                        .get('/api/list/engineers')
                        .then(response => {
                            this.engineersList = response.data.engineers;
                        })/*.finally(() => {
                            this.loading = false;
                        });*/
            },
        }
    }
</script>

<style scoped>

</style>