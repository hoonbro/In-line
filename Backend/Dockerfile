FROM openjdk:8-jdk-alpine

ADD . ./data

WORKDIR ./data

RUN chmod +x gradlew
RUN ./gradlew bootJar
ENV JAVA_OPTS=""

ENTRYPOINT ["java", "-jar", "/data/build/libs/demo-0.0.1-SNAPSHOT.jar"]
