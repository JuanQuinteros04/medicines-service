FROM openjdk:8

WORKDIR /app

VOLUME /app/tmp

COPY ./mvnw .
COPY ./.mvn .mvn
COPY ./pom.xml .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package
CMD ["java", "-jar", "./target/medicines-service-0.0.1-SNAPSHOT.jar"]