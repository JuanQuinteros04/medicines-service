FROM openjdk:8

WORKDIR /app

VOLUME /app/tmp

COPY ./mvnw .
COPY ./.mvn .mvn
COPY ./pom.xml .

RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package
CMD ["java","-Dcom.mysql.cj.disableAbandonedConnectionCleanup=true", "-jar",  "./target/medicines-service-0.0.1-SNAPSHOT.jar"]