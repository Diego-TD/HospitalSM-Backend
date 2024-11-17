# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and configuration files first
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execution permissions to the Gradle wrapper
RUN chmod +x gradlew

# Expose the application port
EXPOSE 8080

# Command to run the application with hot reloading
CMD ["./gradlew", "bootRun"]
