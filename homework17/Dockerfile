FROM maven:3.8.1 as build
COPY pom.xml /app/
COPY src /app/src
COPY frontend /app/frontend
RUN mvn -f /app/pom.xml clean package

FROM bellsoft/liberica-openjdk-alpine:11.0.17-7
COPY --from=build /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

