FROM openjdk:11-slim
MAINTAINER PostFinance AG

CMD ["java", "-jar", "service.jar"]

RUN useradd -ms /bin/bash service

EXPOSE 8080
EXPOSE 8090

USER service
WORKDIR /home/service

ARG JAR_FILE
ADD target/${JAR_FILE} ./service.jar
