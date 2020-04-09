FROM openjdk:8u201-jdk-alpine3.9
COPY ./distribution/target/distribution-1.0-SNAPSHOT-jar-with-dependencies.jar .
COPY ./distribution/resources/core.properties .
#RUN java -jar distribution-1.0-SNAPSHOT-jar-with-dependencies.jar -p core.properties
CMD ["java", "-jar", "distribution-1.0-SNAPSHOT-jar-with-dependencies.jar", "-p", "core.properties"]

EXPOSE 8990