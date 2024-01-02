FROM openjdk:8-jdk-alpine
LABEL maintaier=huzhongkui
COPY ./target/service-outbound.jar service-outbound.jar
ENTRYPOINT ["java","-jar","/service-outbound.jar", "&"]