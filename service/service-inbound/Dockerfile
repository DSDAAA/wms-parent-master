FROM openjdk:8-jdk-alpine
LABEL maintaier=huzhongkui
COPY ./target/service-inbound.jar service-inbound.jar
ENTRYPOINT ["java","-jar","/service-inbound.jar", "&"]