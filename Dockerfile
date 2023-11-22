FROM openjdk:20-ea-1-jdk
ADD TARGET/taskManagement-docker.jar taskManagement-docker.jar
COPY TARGET/taskManagement-docker.jar /usr/app
WORKDIR user/app
ENTRYPOINT["java","jar","taskManagement-docker.jar"]