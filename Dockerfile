FROM openjdk:8-jdk-alpine

ENV spring.datasource.url="jdbc:mysql://docker-mysql:3306/employeedb?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true"
ENV spring.datasource.username="root"
ENV spring.datasource.password="root"

ARG JAR_FILE=target/employee-app.jar

WORKDIR /opt/app

COPY ${JAR_FILE} employee.jar

ENTRYPOINT ["java","-jar","employee.jar"]