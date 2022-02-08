FROM openjdk:11.0.10-jre-slim-buster
VOLUME ["/tmp","/log"]
EXPOSE 8080
ARG JAR_FILE
COPY ./ConsultingTypeService.jar app.jar
ENTRYPOINT ["java"," -Djavax.net.debug=ssl,handshake","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
