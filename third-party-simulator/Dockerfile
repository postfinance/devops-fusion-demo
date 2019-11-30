FROM openjdk:11-slim
MAINTAINER PostFinance AG

CMD ["java", "-jar", "simulator.jar"]

RUN useradd -ms /bin/bash simulator

EXPOSE 8080
EXPOSE 8090

USER simulator
WORKDIR /home/simulator

ARG JAR_FILE
ADD target/${JAR_FILE} ./simulator.jar
