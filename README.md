# portfolio-aqa-demo

Небольшой Java-проект для портфолио AQA:
- Java 17
- Gradle
- JUnit 5
- OWNER
- JaCoCo
- SonarQube
- Jenkins Pipeline

## Что делает приложение
Приложение рассчитывает итоговую стоимость заказа:
- скидка для обычного клиента
- скидка для VIP
- налог
- стоимость доставки
- бесплатная доставка от порога

Все основные значения лежат в `application.properties` и читаются через OWNER.

## Где OWNER используется в тестах
В `src/test/resources/test.properties` лежат ожидаемые значения для тестов.
Это полезно, когда хочется управлять тестовыми ожиданиями централизованно через конфигурацию.

## Запуск локально
```bash
./gradlew clean test jacocoTestReport
```

## Проверка покрытия
```bash
./gradlew jacocoTestCoverageVerification
```

## SonarQube локально
```bash
export SONAR_HOST_URL=http://localhost:9000
export SONAR_TOKEN=your_token
./gradlew sonarqube
```

## Jenkins pipeline
В корне проекта уже есть `Jenkinsfile`.
