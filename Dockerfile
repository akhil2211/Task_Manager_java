FROM openjdk:20-ea-1-jdk
ADD TARGET/taskManagement-docker.jar taskManagement-docker.jar
ENTRYPOINT["java","jar","taskManagement-docker.jar"]