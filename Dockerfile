FROM gradle:8.6-jdk17 as builder

WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY ./gradle /app/gradle

VOLUME /app/src

RUN gradle clean build --no-daemon


FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
