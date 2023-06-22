FROM openjdk:17
VOLUME /tmp
COPY target/saga-0.0.1-SNAPSHOT.jar saga.jar
CMD [ "java","-Djava.security.egd=file:/dev/./urandom","-jar","/saga.jar" ]