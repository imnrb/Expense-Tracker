# Use Amazon Corretto JDK 21 as the base image
FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory to the container
COPY target/et-user-auth-service-0.0.1-SNAPSHOT.jar app.jar

# Command to run the JAR file with the specified Spring profile
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
