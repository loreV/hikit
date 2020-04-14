FROM openjdk:8u201-jdk-alpine3.9
COPY ./distribution/target/hikitService.jar .
COPY ./distribution/resources/core.properties .
CMD ["java", "-jar", "hikitService.jar", "-p", "core.properties"]

EXPOSE 8990