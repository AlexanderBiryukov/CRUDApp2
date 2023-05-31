# CRUD Console Application
## Описание задачи
Необходимо реализовать консольное CRUD приложение для управления сущностями разработчиков, их навыками и специальностями. Приложение должно взаимодействовать с базой данных и позволять выполнять операции создания, чтения, обновления и удаления над сущностями.

## Структура проекта
Проект следует структуре MVC (Model-View-Controller). Ниже представлена структура пакетов:

- model - содержит POJO-классы, представляющие сущности разработчиков, навыков и специальностей.
- repository - содержит интерфейсы репозиториев для работы с базой данных.
- service - содержит сервисный слой, реализующий бизнес-логику приложения.
- controller - содержит классы-контроллеры, обрабатывающие запросы от пользователя.
- view - содержит классы для взаимодействия с пользователем через консоль.
## Используемые технологии
- Java
- MySQL
- JDBC
- Maven
- Liquibase
- JUnit
- Mockito
## Инструкции по локальному запуску проекта
1. Установите и настройте MySQL на вашем компьютере.
2. Склонируйте репозиторий на свою локальную машину:
`git clone https://github.com/AlexanderBiryukov/CRUDApp2.git`
3. Перейдите в каталог проекта:
`cd CRUDApp2`
4. Откройте файл src/main/java/com/alexb/crudapp2/config/MyDataSource и настройте соединение с вашей базой данных MySQL.
5. Откройте файл src/main/resources/liquibase.properties и укажите конфигурацию вашей базы данных для использования миграций с помощью Liquibase.
6. Соберите проект с помощью Maven:
`mvn clean package`
7. Запустите приложение:
`java -jar target/crudapp2.jar`
### Приложение будет запущено и вы увидите меню для взаимодействия с приложением через консоль.
