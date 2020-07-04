# LynxSupport time manager

За основу взят проект [spring-boot-vuejs](https://github.com/jonashackt/spring-boot-vuejs)

Backend работает со **SpringBoot 2.2.1**.
Frontend - с **Vue.js 2.6.11** и **Vuetify 2.3.3**.

## Сборка

В системе должны быть установлены Java 8/11 и Apache Maven.

В главной директории проекта выполняем команду:

```mvn clean install```

На выходе получаем .jar-файл **lynxsupport-version-X.X.X.jar** в директории *backend/target*

## Запуск

Для запуска потребуется предварительно подготовленная база данных **PostgreSQL 9.2** или выше.

Настройки соединения с базой данных (и другие настройки) находятся в файле **[application.properties](https://github.com/betanets/vue-sb-support/blob/master/backend/src/main/resources/application.properties)**

Для запуска проекта выполяем команду:

```java -jar lynxsupport-version-X.X.X.jar -Dapplication.properties=application.properties```
