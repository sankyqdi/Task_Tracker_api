
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /application

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /application

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /application/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]