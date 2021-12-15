FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/employee-app.jar

WORKDIR /opt/app

COPY ${JAR_FILE} employee.jar

ENTRYPOINT ["java","-jar","employee.jar"]