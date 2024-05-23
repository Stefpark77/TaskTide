FROM openjdk:21
WORKDIR /opt
ENV PORT 8081
EXPOSE 8081
COPY target/*.jar /opt/app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]
