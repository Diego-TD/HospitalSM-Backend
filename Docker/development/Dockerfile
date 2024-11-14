# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle wrapper and configuration files first for better caching
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execution permissions to the Gradle wrapper
RUN chmod +x gradlew

# Download dependencies without running the build
RUN ./gradlew build -x test --no-daemon

# Copy the rest of the application code
COPY src src

# Expose the application port
EXPOSE 8080

# Set environment variable for Spring profiles
ENV SPRING_PROFILES_ACTIVE=dev

# Command to run the application with hot reloading
CMD ["./gradlew", "bootRun"]
