FROM openjdk:8-jdk-alpine
LABEL maintaier=huzhongkui
COPY ./target/service-inventory.jar service-inventory.jar
ENTRYPOINT ["java","-jar","/service-inventory.jar", "&"]