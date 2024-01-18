FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR .
COPY ./src ./src
COPY ./pom.xml ./pom.xml
COPY ./mvnw ./mvnw
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
EXPOSE 1111
COPY --from=build ./target/cities-0.0.1-SNAPSHOT.jar ./cities.jar
ENTRYPOINT ["java", "-jar","cities.jar"]