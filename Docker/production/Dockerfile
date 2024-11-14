# Build stage
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Gradle wrapper and configuration files first for caching
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execution permissions to the Gradle wrapper
RUN chmod +x gradlew

# Download dependencies and build the application
RUN ./gradlew clean bootJar -x test --no-daemon

# Run stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/hospitalSM-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
