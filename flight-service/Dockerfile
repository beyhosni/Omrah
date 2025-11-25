FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copier le pom parent et le pom du service
COPY ../pom.xml ../pom.xml
COPY pom.xml .
COPY src ./src

# Build l'application
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copier le jar depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Exposer le port
EXPOSE 8082

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
