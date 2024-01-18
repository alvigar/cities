FROM maven:3.8.4-openjdk-8-slim AS build
WORKDIR .
COPY ./src ./src
COPY ./pom.xml ./pom.xml
COPY ./mvnw ./mvnw
RUN mvn package spring-boot:repackage

FROM openjdk:8-jdk-alpine
EXPOSE 1111
COPY --from=build ./target/cities-0.0.1-SNAPSHOT.jar ./cities.jar
ENTRYPOINT ["java", "-jar","cities.jar"]