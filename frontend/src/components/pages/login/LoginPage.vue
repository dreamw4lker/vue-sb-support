<template>
    <div class="ma-auto">
        <lynx-title title="Техническая поддержка LynxSoft - Авторизация"></lynx-title>
        <div style="position: fixed; top: 0; bottom: 0; left: 0; right: 0; justify-content: center; display: flex;">
            <div style="width: 320px; align-self: center;">
                <v-toolbar dark class="primary">
                    <v-toolbar-title><h4>Авторизация</h4></v-toolbar-title>
                </v-toolbar>
                <v-card>
                    <v-form
                            @keyup.native.enter="submit"
                            ref="loginForm">
                        <v-card-text>
                            <v-flex xs12>
                                <v-text-field
                                        :prepend-icon="icons.mdiAccount"
                                        v-model="loginItem.login"
                                        label="Логин"
                                        placeholder=" "
                                        :rules="rules.login"
                                        outlined
                                        dense
                                ></v-text-field>
                            </v-flex>
                            <v-flex xs12>
                                <v-text-field
                                        :prepend-icon="icons.mdiLock"
                                        v-model="loginItem.password"
                                        label="Пароль"
                                        placeholder=" "
                                        :rules="rules.password"
                                        @click:append="passwordVisible = !passwordVisible"
                                        :append-icon="passwordVisible ? icons.mdiEye : icons.mdiEyeOff"
                                        :type="passwordVisible ? 'password' : 'text'"
                                        outlined
                                        dense
                                ></v-text-field>

                            </v-flex>
                            <v-flex xs12 class="text-center pb-4" v-if="loginError">
                                <span class="h5 red--text"> {{ loginError }}</span>
                            </v-flex>
                            <v-flex xs12 class="text-center">
                                <lynx-submit-button
                                        :loading="loading"
                                        @submit="submit"
                                        text="Вход"/>
                            </v-flex>
                        </v-card-text>
                    </v-form>
                </v-card>
            </div>
        </div>
    </div>
</template>

<script>
    import SubmitButton from "../../button/SubmitButton";
    import { mdiAccount, mdiLock, mdiEye, mdiEyeOff } from '@mdi/js'

    export default {
        components: {
            'lynx-submit-button': SubmitButton
        },
        data() {
            return {
                icons: {
                    mdiAccount,
                    mdiLock,
                    mdiEye,
                    mdiEyeOff
                },

                loginItem: {
                    login: null,
                    password: null
                },

                passwordVisible: true,
                loading: false,
                loginError: null,

                rules: {
                    login: [
                        v => !!v || 'Необходимо указать логин',
                        v => (v && v.length <= 255) || 'Максимальная длина логина: 255 символов'
                    ],
                    password: [
                        v => !!v || 'Необходимо указать пароль'
                    ],
                },
            };
        },
        created: function () {
            let session = this.$cookie.get(this.$sessionCookieName);
            if (session) {
                this.$router.push({path: '/main'});
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                vm.prevRoute = from
            })
        },
        methods: {
            submit() {
                if (this.$refs.loginForm.validate()) {
                    this.loading = true;
                    this.loginError = null;
                    this.$axios
                        .post('/api/login', {
                            "login": this.loginItem.login,
                            "password": this.loginItem.password
                        })
                        .then((response) => {
                            if (response.data === 'user.notFound') {
                                this.loginError = 'Неверный логин или пароль';
                                return;
                            }
                            let jwtObject = this.$jwt.decode(response.data);
                            if (jwtObject && jwtObject.sub && jwtObject.login && (jwtObject.sub === jwtObject.login) && (jwtObject.login === this.loginItem.login)) {
                                this.$cookie.set(this.$sessionCookieName, response.data);
                                if (document.referrer !== "" && document.location.href !== "" &&
                                    document.location.href.split('/')[2].split(':')[0] === document.referrer.split('/')[2].split(':')[0]) {
                                    window.location.href = document.referrer;
                                } else {
                                    this.$router.push({path: '/main'});
                                }
                            } else {
                                this.loginError = 'Не удалось пройти авторизацию';
                            }
                        })
                        .finally(() => {
                            this.loading = false;
                        })
                }
            }
        }
    }
</script>

<style scoped>

</style>