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

## ✅ Funcionalidades Principales (MVP)

### 👤 Módulo Candidatos
- Registro de aspirantes con datos personales.
- Postulación a ofertas laborales.
- Consulta del estado actual del proceso:
  - Etapa actual.
  - Porcentaje de avance.
  - Filtros completados vs. pendientes.

### 📝 Módulo Ofertas Laborales
- Gestión de cargos disponibles.
- Asociación de cada oferta con filtros o etapas del proceso.

### 📊 Módulo Filtros / Etapas
- Registro de etapas del proceso (Ej: entrevista, prueba técnica).
- Orden y peso configurable por etapa (para cálculo de avance).
- Registro de avance por candidato.

---

## 🧪 Tecnologías Utilizadas

| Componente | Tecnología |
|-----------|------------|
| Lenguaje | Java 17 |
| Framework principal | Spring Boot |
| Gestión de dependencias | Maven |
| Base de datos | PostgreSQL |
| Concurrencia | ConcurrentHashMap (modo local/test) |
| Asincronía | Spring `@Async` + `CompletableFuture` |
| IDE | IntelliJ IDEA |
| Control de versiones | Git |

---

## ⚙️ Requisitos Previos

- Java 17
- Maven 3.8+
- PostgreSQL 14+

---

## 🚀 Ejecución del Proyecto

1. **Clona el repositorio:**

```bash
git clone https://github.com/tu-usuario/nombre-repo.git
cd nombre-repo

---

2. **Configura la base de datos:**

Crea una base de datos PostgreSQL con el nombre deseado. Luego, configura tus credenciales en src/main/resources/application.properties.

spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

---

3. **Compila y ejecuta:**

mvn spring-boot:run

La API estará disponible en: http://localhost:8080

---

📂 Estructura del Proyecto
src
├── controller        # Endpoints HTTP
├── service           # Lógica de negocio
├── repository        # Interfaces de acceso a datos
├── dto               # Objetos de transferencia de datos
├── model             # Entidades JPA
├── config            # Configuraciones asincronía, etc.
└── export            # Módulo de exportaciones (CSV/Excel)

🙋‍♀️ Motivación Personal
Este proyecto nace como iniciativa individual para practicar backend Java, aplicando conceptos modernos y buenas prácticas de desarrollo. Elegí como temática el seguimiento a procesos de selección porque estoy actualmente en búsqueda laboral y noté la falta de visibilidad que tienen los candidatos durante este proceso.

📜 Licencia
Este proyecto se distribuye bajo la licencia MIT. Puedes usarlo con fines personales o educativos.

✉️ Contacto
Desarrollado por: Marcela Diaz
Correo: marcela514@email.com
LinkedIn: https://www.linkedin.com/in/marcela-diaz-05a3b415a/
