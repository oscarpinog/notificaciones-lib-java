# ETAPA 1: Compilación (Builder)
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
RUN apk add --no-cache maven
COPY . .
# Compilamos y generamos el JAR
RUN mvn clean package

# ETAPA 2: Ejecución (Runtime) - Esta es la que queda final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos SOLO el JAR generado en la etapa anterior
COPY --from=build /app/target/notificaciones-app.jar app.jar

# Configuración de entorno
ENV NOTI_CANAL=EMAIL

# Ejecutamos el JAR directamente
ENTRYPOINT ["java", "-jar", "app.jar"]