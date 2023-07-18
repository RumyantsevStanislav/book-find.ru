FROM eclipse-temurin:17-jdk-jammy
#FROM openjdk:17

LABEL maintainer="admin@book-find.ru"
ENV app.name="book-find_backend"

#WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# clean up the file
RUN sed -i 's/\r$//' mvnw
# run with the SH path
RUN /bin/sh mvnw dependency:resolve
#RUN ./mvnw dependency:resolve
COPY src ./src
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]

#MAINTAINER book-find.ru
#COPY target/server-0.3.0-SNAPSHOT.jar./mvnw spring-boot:run
