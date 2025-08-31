FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app
COPY common /app/common
