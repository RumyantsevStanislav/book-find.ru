FROM eclipse-temurin:17-jdk-jammy
LABEL maintainer=s.rumyantsev
WORKDIR /app
COPY libs libs/
COPY resources resources/
COPY classes classes/
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-Xmx2048m", "-cp", "/app/resources:/app/classes:/app/libs/*", "ru.bookfind.parsers.labirint.Application"]
EXPOSE 8188 8188
