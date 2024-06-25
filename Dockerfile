FROM gradle:8.6-jdk17 as builder

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY ./gradle /app/gradle

RUN mkdir -p /app/src

COPY ./src /app/src

RUN gradle clean bootJar --no-daemon


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
