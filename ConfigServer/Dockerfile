FROM openjdk:8
ARG JAR_FILE=target/ConfigServer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ConfigServer-0.0.1-SNAPSHOT.jar
RUN apt-get update
RUN apt-get upgrade
RUN apt-get install curl
ENTRYPOINT ["java","-jar","/ConfigServer-0.0.1-SNAPSHOT.jar"]