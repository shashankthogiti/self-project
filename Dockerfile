FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the Spring Boot JAR
COPY build/libs/*.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Run the application with docker profile
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app/app.jar"]

