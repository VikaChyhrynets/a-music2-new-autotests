FROM maven:3.8.5-openjdk-18-slim AS builder

WORKDIR /usr/src/test

COPY . .

RUN mvn clean compile

