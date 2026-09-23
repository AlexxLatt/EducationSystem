# ===== Этап 1: Сборка приложения =====
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# 1. Сначала копируем только pom.xml (кеширование зависимостей)
COPY pom.xml .

# 2. Скачиваем зависимости (слой кешируется, если pom.xml не менялся)
RUN mvn dependency:go-offline -B

# 3. Копируем исходный код
COPY src ./src

# 4. Собираем JAR (пропускаем тесты для скорости)
RUN mvn clean package -DskipTests

# ===== Этап 2: Запуск приложения =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 5. Создаём непривилегированного пользователя (безопасность)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# 6. Копируем только JAR из этапа сборки
COPY --from=build /app/target/*.jar app.jar

# 7. Меняем владельца файла
RUN chown -R appuser:appgroup /app

# 8. Переключаемся на непривилегированного пользователя
USER appuser

# 9. Открываем порт
EXPOSE 8080

# 10. Переменные окружения для JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# 11. Команда запуска
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]