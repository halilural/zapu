#### Stage 1: Build the application
FROM openjdk:8-jdk-alpine as build

RUN apk --no-cache add curl

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .

COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline. 
# This is a separate step so the dependencies will be cached unless 
# the pom.xml file has changed.

RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","-jar","/app/target/zapu.jar"]

EXPOSE 8080