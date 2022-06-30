FROM maven:3.8.5-openjdk-18-slim AS builder

WORKDIR /usr/src/user-service

COPY . .

RUN mvn clean install -Dmaven.test.skip
