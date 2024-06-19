FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/*.jar validation-service.jar

ENTRYPOINT ["java", "-jar", "validation-service.jar"]