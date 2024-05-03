FROM openjdk:17-oracle
VOLUME ["/tmp","/log"]
EXPOSE 8080
ARG JAR_FILE
COPY ./ConsultingTypeService.jar app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
