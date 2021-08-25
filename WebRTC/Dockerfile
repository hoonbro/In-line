FROM openjdk:8-jdk-alpine
#FROM ubuntu:20.04

ADD . ./data

WORKDIR ./data

#RUN apt-get update
#RUN apt-get upgrade -y
#RUN apt-get install -y openjdk-8-jdk

#RUN apt-get update && apt-get install --no-install-recommends --yes gnupg
#RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 5AFA7A83
#RUN source /etc/lsb-release
#RUN tee tee "/etc/apt/sources.list.d/kurento.list" >/dev/null <<EOF
#RUN apt-get update && apt-get install --no-install-recommends --yes kurento-media-server
RUN chmod +x gradlew
RUN ./gradlew bootJar
RUN echo $DOMAIN
ENV JAVA_OPTS=""

ENTRYPOINT java -Dkms.url=ws://i5d207.p.ssafy.io:8888/kurento -jar /data/build/libs/demo-0.0.1-SNAPSHOT.jar
