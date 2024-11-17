# Build stage
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copy Gradle wrapper and configuration files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Grant execution permissions to the Gradle wrapper
RUN chmod +x gradlew

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew clean bootJar -x test --no-daemon --info

# List the contents of the build/libs directory
RUN ls -l build/libs

# Run stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/app.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
