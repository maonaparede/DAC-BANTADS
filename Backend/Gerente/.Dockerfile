FROM openjdk:17
VOLUME /tmp
COPY target/gerente-0.0.1-SNAPSHOT.jar gerente.jar
CMD [ "java", "-Djava.security.egd=file:/dev/./urandom","-jar", "/gerente.jar" ]