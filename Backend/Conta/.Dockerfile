FROM openjdk:17
VOLUME /tmp
COPY target/conta-0.0.1-SNAPSHOT.jar conta.jar
CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/conta.jar" ]