FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/taskmanager-0.0.1-SNAPSHOT.jar taskmanager.jar
ENTRYPOINT ["java","-jar","/taskmanager.jar"]