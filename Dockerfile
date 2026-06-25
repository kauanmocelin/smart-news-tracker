# Start with a base image containing Java runtime
FROM eclipse-temurin:21-jre

# Add Maintainer Info
LABEL maintainer="kauanmocelin@gmail.com"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/smart-news-tracker-0.0.1-SNAPSHOT.jar

# Copy the application's jar to the container
COPY ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java","-jar","/app.jar"]