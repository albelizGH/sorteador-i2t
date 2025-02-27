# Sistema de Gestión de Reemplazos - Backend 📅

Este es el backend para el Sistema de Gestión de Reemplazos, desarrollado con Spring Boot.

## Descripción 🪪

Este sistema automatiza la gestión de reemplazos de empleados dentro de un marco de sorteos y asignaciones. Permite a
autoridades y auxiliares gestionar reemplazos de manera eficiente, con control de disponibilidad, validación de
solicitudes y notificaciones en tiempo real.

## Funcionalidades Principales📒

* **Gestión de Asignaciones**:
    * Automatización de asignaciones basadas en grupos.
    * Configuración de sorteos categorizados (Quiniela Mañana, Tarde, Quini6, Brinco).
    * División de sorteos por semana y rotación de grupos.
    * Creación, modificación y eliminación de asignaciones.
    * Confirmación del estado de asignación por coordinador.
    * Notificaciones automáticas a empleados sobre sus asignaciones.
* **Gestión de Solicitudes de Reemplazo**:
    * Visualización de asignaciones y solicitud de reemplazo por empleado.
    * Selección de fecha y empleado sustituto (mismo grupo, mismo rol).
    * Validación de reemplazos por coordinador.
    * Registro de ID de asignación, empleados y asignación de devolución.
    * Filtro para visualizar asignaciones y reemplazos actualizados.
* **Configuración de Sorteos y Categorías**:
    * Información de sorteos: fecha, estado, día y hora.
    * Definición de categorías tope (límite de autoridades y auxiliares).
    * Creación automática de grupos si se alcanza el límite.
    * Rotación semanal de grupos.
    * Asignación basada en la última asignación registrada.
    * Cálculo del período de asignación.
* **Validaciones Lógicas**:
    * Evitar múltiples reemplazos en la misma fecha.
    * Crear nuevo grupo si la categoría tope se alcanza.
    * Evitar solapamiento de reemplazos con otras asignaciones.
    * Restringir la cantidad máxima de reemplazos por autoridad.
    * Verificar reemplazos dentro de sorteos predefinidos.
    * Reemplazo solo si el empleado reemplazante tiene disponibilidad.
* **Notificaciones**:
    * Notificar asignaciones a integrantes del grupo.
    * Alertas cuando se aprueba un reemplazo.
    * Comunicación automática de cambios en asignaciones.

## Arquitectura

* **Spring Boot**: Framework principal para el backend.
* **Base de Datos**: MySQL
* **Dependencias**: Spring Data JPA, Spring Security, Flyway, Lombok

## Endpoints 📎

Una vez que el proyecto esté en ejecución, puedes acceder a la documentación generada por Springdoc OpenAPI en la siguiente ruta:

* Formato general: http://{host}:{port}/documentacion
* Ejemplo común: http://localhost:8080/api/v1/documentacion

Esta interfaz te permitirá explorar y probar los endpoints de la API de manera interactiva. 🚀

## Cómo ejecutar⚙️

1. **Requisitos**:
    * JDK 17 (o la versión de Java especificada en el proyecto)
    * Maven (para la gestión de dependencias y compilación)
    * Base de datos compatible (PostgreSQL, MySQL, etc.)
    * IDE de desarrollo (opcional, pero recomendado: IntelliJ IDEA, Eclipse)
2. **Configuración**
    * Configura las variables de entorno ubicadas en el archivo application.properties:
        * DB_USER
        * DB_PASSWORD
3. **Compilación**:
    * Abre una terminal en la raíz del proyecto y ejecuta:
    ```bash
    mvn clean install
    ```

4. **Ejecución**:
    * Desde el IDE: puedes ejecutar la aplicación directamente desde él (busca la clase principal con el
      método main).
    * Desde la terminal: ejecuta
   ``` bash
   mvn spring-boot:run
   ```

5. **Verificación**
    * Una vez que la aplicación se haya iniciado, deberías ver un mensaje en la consola indicando que está en
      funcionamiento.
    * Puedes probar la API accediendo a los endpoints con herramientas como Postman.


