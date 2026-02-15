# 1. Imagen base con JDK 21 y Alpine Linux (ligera y funcional)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# 2. Instalar Maven (necesario para compilar dentro del contenedor)
RUN apk add --no-cache maven

# 3. Copiar todos los archivos del proyecto (pom.xml y carpeta src)
COPY . .

# 4. Compilar, EJECUTAR PRUEBAS UNITARIAS y empaquetar
# Esto genera el archivo target/notificaciones-app.jar definido en tu pom.
RUN mvn clean package

# Si quiero evitar las pruebas unitarias
# RUN mvn clean package -DskipTests

# 5. Configuración de ejemplo ejecutable
# Valor por defecto que puedes sobrescribir con 'docker run -e NOTI_CANAL=...'
ENV NOTI_CANAL=EMAIL

# 6. Ejecutar la demo (Clase principal de ejemplos)
# Usamos el classpath (-cp) apuntando al JAR con el <finalName> de tu pom.
CMD ["java", "-cp", "target/notificaciones-app.jar", "com.notificacion.libreria.NotificacionesApplication"]