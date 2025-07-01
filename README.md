# 📌 Sistema de Seguimiento de Procesos de Selección

Este proyecto es una aplicación web backend desarrollada en Java con Spring Boot, cuyo propósito es permitir a los aspirantes a cargos laborales consultar de manera autónoma el estado de su proceso de selección. Surge como una solución personal y educativa, motivada por la experiencia real de búsqueda de empleo.

---

## 🎯 Objetivo

Ofrecer un sistema donde los candidatos puedan:

- Registrarse y postularse a ofertas laborales.
- Consultar el estado y progreso de su proceso de selección.
- Visualizar filtros completados y etapas pendientes.

---

## 🧱 Arquitectura

El proyecto está estructurado siguiendo una arquitectura en capas, con separación clara entre:

- `Controller`: expone los endpoints REST.
- `Service`: lógica de negocio.
- `Repository`: acceso a datos mediante Spring Data JPA.
- `DTOs`: transferencia segura de datos entre capas.
- `Entities`: mapeo de tablas en la base de datos.

---

## ✅ Funcionalidades Implementadas

### 👤 Módulo Candidatos

- Registro de aspirantes con datos personales.
- Exportación de la información a:
  - PDF
  - Excel
  - CSV
- Soporte de auditoría de entidades.
- Internacionalización de enums y mensajes.
- Exportación asíncrona con expiración configurable.
- Logging de solicitudes HTTP y métodos del backend.
- Validaciones estrictas en DTOs y enmascaramiento de datos sensibles en logs.

> ⚠️ Estas funcionalidades están disponibles a partir del commit publicado el **1 de julio de 2025** y se continuarán ampliando en futuras versiones.

---

## 🆕 Novedades de la versión actual (📅 1 de julio de 2025)

- ✅ Auditoría de entidades (creación y modificación).
- ✅ Exportación asíncrona completa y paginada con control de límites y expiración.
- ✅ Validaciones reforzadas en DTOs con anotaciones `@Sensitive` para proteger datos personales en logs.
- ✅ Logging automático de solicitudes HTTP con posibilidad de ocultar campos confidenciales.
- ✅ Aspecto AOP con anotaciones `@Loggable` y `@Sensitive`.
- ✅ Internacionalización de enums (`Genero`, `TipoDocumento`, `NivelEducativo`, `EstadoCandidato`, `ExportFormat`).
- ✅ Nuevos mensajes localizados (español/inglés) para errores, exportaciones y formatos.
- ✅ Utilidad `EnumLabelUtil` para obtener etiquetas traducidas con fallback automático.
- ✅ Documentación de servicios y controladores mejorada.

---

## 🔧 Funcionalidades Planeadas (MVP)

- Postulación a ofertas laborales.
- Consulta de estado y progreso del proceso de selección.
- Gestión de ofertas laborales.
- Gestión de filtros/etapas del proceso de selección.

---

## 🧪 Tecnologías Utilizadas

| Componente               | Tecnología                                  |
|-------------------------|---------------------------------------------|
| Lenguaje                | Java 17                                     |
| Framework principal     | Spring Boot                                 |
| Gestión de dependencias | Maven                                       |
| Base de datos           | PostgreSQL                                  |
| Exportaciones           | Apache POI, iText PDF                       |
| Concurrencia local      | ConcurrentHashMap (modo local/test)         |
| Asincronía              | Spring `@Async` + `CompletableFuture`       |
| Logging HTTP            | Spring Interceptors                         |
| Logging interno         | Spring AOP + Anotaciones personalizadas     |
| Auditoría de entidades  | Spring Data JPA Auditing                    |
| Internacionalización    | `MessageSource`, `messages*.properties`     |
| Logging avanzado        | Log4j2 + `log4j2-spring.xml`                |
| IDE                     | IntelliJ IDEA                               |
| Control de versiones    | Git                                         |

---

## ⚙️ Requisitos Previos

- Java 17
- Maven 3.8+
- PostgreSQL 14+

---

## 🚀 Ejecución del Proyecto

1. **Clona el repositorio**

    ```bash
    git clone https://github.com/tu-usuario/nombre-repo.git
    cd nombre-repo
    ```

2. **Configura la base de datos**

    Crea una base de datos PostgreSQL con el nombre deseado. Luego, edita el archivo `src/main/resources/application.properties` con tus credenciales:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_db
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Ejecuta la aplicación**

    ```bash
    mvn spring-boot:run
    ```

    La API estará disponible en: `http://localhost:8080`

---

## 📂 Estructura del Proyecto

<pre>
src
└── main
    ├── java
    │   └── com.reclutamiento.seguimientoSeleccion
    │       ├── config
    │       ├── controller
    │       ├── dto
    │       ├── enums
    │       ├── exception
    │       ├── export
    │       ├── logging
    │       ├── mapper
    │       ├── model
    │       ├── repository
    │       ├── service
    │       └── util
    └── resources
        ├── application.properties
        ├── application-dev.properties
        ├── application-prod.properties
        ├── application-test.properties
        ├── log4j2-spring.xml
        ├── messages.properties
        └── messages_en.properties
</pre>

---

## 🙋 Sobre el proyecto

Este proyecto fue desarrollado con fines de aprendizaje y para resolver una necesidad real detectada durante la búsqueda de empleo: la falta de visibilidad sobre el avance en procesos de selección.

Se diseñó para funcionar de forma ligera, utilizando estructuras en memoria (`ConcurrentHashMap`) en esta primera etapa, y preparado para escalar con herramientas externas (como notificaciones, autenticación o gestión de archivos).

---

## 👩‍💻 Desarrollado por

**Marcela Diaz**  
📧 marcela514@email.com  
🔗 [LinkedIn: marcela-diaz-05a3b415a](https://www.linkedin.com/in/marcela-diaz-05a3b415a/)
