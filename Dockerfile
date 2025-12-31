# ---------------- BUILD STAGE ----------------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

# Copy pom and download dependencies first (cache optimization)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests


# ---------------- RUN STAGE ----------------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /build/target/UserManagement-0.0.1-SNAPSHOT.jar app.jar

# Expose backend port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java","-jar","app.jar"]
