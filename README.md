# Notificaciones Lib - Demo Docker 🚀

Esta es una librería de notificaciones desarrollada en Java 21 que utiliza una arquitectura robusta y escalable para el envío de mensajes.
El proyecto está completamente Dockerizado para facilitar su despliegue y pruebas.

---

## 👤 Información del Autor
* **Desarrollador:** Oscar Rodriguez
* **Contacto:** oscarpino711@gmail.com

---

## 🚀 Instalación y Configuración

Siga estos pasos para obtener una copia local del proyecto y ejecutarlo en su entorno de desarrollo.

### 1. Clonación del Repositorio
Para obtener el código fuente, clone el repositorio utilizando Git. Por defecto, el proyecto se encuentra en la rama principal (**main**):

git clone https://github.com/oscarpinog/notificaciones-lib-java.git

## 📋 Requisitos

* Docker Desktop (activo)
* Java 21 (si deseas ejecutarlo localmente)
* Maven (para gestión de dependencias)

---

## 🐳 Guía de Docker

El proyecto incluye un **Dockerfile** optimizado que garantiza un entorno estandarizado, eliminando conflictos de versiones locales.
Para validar la librería y sus estrategias, ejecute los siguientes comandos:

### 1. CREAR LA IMAGEN

Abra una terminal y sitúese en la raíz del proyecto (donde se encuentra el archivo pom.xml y el Dockerfile).
Compila, ejecuta tests y empaqueta la aplicación:
docker build -t notificaciones-lib-java .

### 2. EJECUTAR LA IMAGEN
Lanza la aplicación con la configuración predeterminada (EMAIL):
docker run --rm notificaciones-lib-java

### 3. PRUEBAS DE EJECUCIÓN PARA LOS DIFERENTES CANALES
Usa la variable de entorno NOTI_CANAL:

* EMAIL:
  docker run --rm -e NOTI_CANAL=EMAIL notificaciones-lib-java
* SMS:
  docker run --rm -e NOTI_CANAL=SMS notificaciones-lib-java
* PUSH:
  docker run --rm -e NOTI_CANAL=PUSH notificaciones-lib-java
* SLACK:
  docker run --rm -e NOTI_CANAL=SLACK notificaciones-lib-java

### 4. PRUEBA DE EJECUCIÓN CANAL NO VÁLIDO
docker run -e NOTI_CANAL=WHATSAPP notificaciones-lib-java

### 5. ELIMINAR LA IMAGEN ESPECÍFICA
docker rmi -f notificaciones-lib-java

---

## 🛠️ Tecnologías y Buenas Prácticas

El desarrollo se fundamenta en altos estándares de calidad de software y arquitectura:

* **Patrones de Diseño Implementados:**
    - **Strategy:** Permite intercambiar los algoritmos de envío (Email, SMS, Slack, etc.) en tiempo de ejecución.
    - **Facade:** Proporciona una interfaz unificada y simplificada para interactuar con el complejo sistema de notificaciones.
    - **Builder:** Facilita la construcción paso a paso de objetos complejos, mejorando la legibilidad.
    - **Factory:** Centraliza la lógica de creación de las distintas instancias de canales.

* **Calidad de Software:**
    - **Principios SOLID:** Código desacoplado, extensible y con Responsabilidad Única.
    - **Clean Code:** Código autodocumentado, nombres semánticos y funciones de propósito único.
    - **Logs (SLF4J/Logback):** Trazabilidad profesional para monitoreo de eventos y errores.
    - **JavaDoc:** Documentación técnica estructurada en todo el código fuente.
    - **Unit Testing:** Cobertura de pruebas unitarias robustas con JUnit 5 y Mockito.
---

## ⚙️ Configuración del Proyecto

* Paquete Base: com.notificacion.libreria
* Clase Principal: NotificacionesApplication
* Nombre del JAR: notificaciones-app.jar

---

## 🧹 Limpieza de Docker
# Eliminar contenedores huérfanos
docker container prune -f

# Eliminar cache de construcción
docker builder prune -f


## 🏛️ Estructura y Filosofía Arquitectónica

El proyecto implementa una **Arquitectura en Capas** bajo principios de **Arquitectura Limpia (Clean Architecture)**, garantizando que el núcleo de negocio sea independiente de los servicios externos.

### 1. Descripción de las Capas
* **Capa de Aplicación (Entry Point):** `NotificacionesApplication` orquestra el inicio del contexto y lee configuraciones (variables de entorno).
* **Capa de Fachada (Facade):** Simplifica la interacción del cliente con el sistema, centralizando la lógica en una interfaz única.
* **Capa de Dominio (Core):** Define las **Estrategias** y el **Modelo**. Es el corazón del sistema, donde reside la lógica de procesamiento.
* **Capa de Infraestructura (Adaptadores):** Implementaciones concretas de canales (Email, Slack, etc.). Funciona bajo la filosofía **Hexagonal**, donde cada canal es un "adaptador" de la interfaz del dominio.

---

## 🔬 Atributos de Calidad del Software

Esta arquitectura fue elegida para maximizar los siguientes indicadores:

### 🔗 Bajo Acoplamiento y Alta Cohesión
* **Acoplamiento Bajo:** Gracias al uso intensivo de **interfaces** y el patrón **Factory**, los componentes no dependen de implementaciones concretas. Esto permite cambiar un proveedor de servicios sin afectar al resto del sistema.
* **Cohesión Alta:** Aplicamos el principio de responsabilidad única (SRP); cada capa y cada clase tiene un propósito claro y delimitado, facilitando su comprensión y mantenimiento.

### 🧪 Facilidad de Testeo (Testability)
La división en capas y la inyección de dependencias (vía Factory) permiten realizar **pruebas unitarias puras**. Es posible testear la lógica de negocio simulando (mocking) los canales de envío, lo que garantiza pruebas rápidas y fiables sin necesidad de conexiones reales a internet o APIs externas.


### 📈 Mantenibilidad y Evolución
La **separación de intereses** permite que el sistema crezca de forma orgánica. Un desarrollador puede añadir un nuevo canal o modificar la lógica de validación en la fachada sin riesgo de efectos secundarios en otras áreas del código.

### 🛡️ Desacoplamiento mediante Interfaces (Filosofía Hexagonal)
Aunque el proyecto es ligero, aplica la base de la **Arquitectura Hexagonal**. Al definir la interfaz `NotificacionStrategy`, el "Core" de la aplicación queda protegido. Si el servicio de mensajería cambia, la lógica de negocio permanece intacta; solo se sustituye o actualiza el "adaptador" correspondiente.

---

## 🧠 Informe de Desarrollo Colaborativo (Humano - IA)

Este proyecto se desarrolló bajo un modelo de asistencia inteligente,
donde el desarrollador lideró el diseño y la implementación,
 utilizando la IA como una herramienta estratégica para optimizar tiempos de configuración,codificacion y estandarización.

### 1. Herramientas Utilizadas
Se utilizó Gemini (Google) como copiloto técnico para agilizar el ciclo de vida de desarrollo y la resolución de problemas de infraestructura.

### 2. Proceso de Trabajo y Estrategia
El enfoque se centró en la agilización técnica mediante las siguientes acciones:
* Consultoría de Diseño: Solicité colaboración para profundizar en la teoría del patrónes, lo que me permitió realizar una implementación alineada con los requerimientos del sistema.
* Soporte en Infraestructura: El apoyo de la IA fue fundamental para diagnosticar y solucionar errores, configuraciónes del proyecto,configuraciones en Docker de forma rápida, evitando cuellos de botella en el despliegue.
* Documentación Técnica: Utilicé capacidades de procesamiento de lenguaje para estructurar y profesionalizar los borradores de la documentación técnica definida por mi autoría.

### 3. Toma de Decisiones vs. Apoyo de IA
* Liderazgo del Desarrollador: Diseño integral de la arquitectura, escritura del código fuente, implementación de la lógica de los patrones de diseño y validación funcional en entorno local.
* Rol de la IA: Actuó como consultora para la optimización de codigo,consulta de errores,informacion sobre Dockerfile y como soporte para la redacción de javadoc,logs entre otros.

### 4. Balance de Valor y Productividad
* Beneficios: Reducción significativa en los tiempos de investigación,codificacion y depuración (debugging) de errores de contenedorización.
* Supervisión: El control total, la ejecución de pruebas y la arquitectura final dependieron exclusivamente de mi(Oscar),
asegurando que las sugerencias de la IA se ajustaran a la realidad técnica del proyecto.
