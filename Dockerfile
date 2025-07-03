# Usa una imagen oficial de Maven para construir el proyecto
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia los archivos de dependencias primero para aprovechar el cache de Docker
COPY pom.xml .
COPY src ./src

# Compila el proyecto y empaqueta el jar
RUN mvn clean package -DskipTests

# Usa una imagen ligera de Java para ejecutar la app
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia el jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]