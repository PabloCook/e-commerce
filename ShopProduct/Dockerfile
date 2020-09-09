FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ShopProduct-1.0-DEFINITIVO.jar
ENTRYPOINT ["java","-jar","/ShopProduct-1.0-DEFINITIVO.jar"]