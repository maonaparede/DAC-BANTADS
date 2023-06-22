FROM openjdk:17
VOLUME /tmp
COPY target/cliente-0.0.1-SNAPSHOT.jar cliente.jar
CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/cliente.jar" ]