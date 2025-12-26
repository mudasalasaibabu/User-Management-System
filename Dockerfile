# ---------------- BUILD STAGE ----------------
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /build

# Copy pom and source
COPY pom.xml .
COPY src ./src

# Build the application (skip tests)
RUN mvn clean package -DskipTests


# ---------------- RUN STAGE ----------------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the WAR from build stage
COPY --from=build /build/target/UserManagement-0.0.1-SNAPSHOT.war app.war

# Expose backend port
EXPOSE 8081

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.war"]
