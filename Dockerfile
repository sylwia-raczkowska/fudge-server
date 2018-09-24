FROM openjdk:8-jdk-alpine
WORKDIR /
COPY target/fudge-backend.jar fudge-backend.jar
EXPOSE 8090
CMD ["java","-jar","fudge-backend.jar"]