# Proyecto Spring Boot - Gestión de Personas

API REST desarrollada con Spring Boot para la gestión de personas.  
El proyecto permite crear, consultar, actualizar y eliminar personas, aplicando validaciones sobre los datos de entrada, documentación de endpoints y pruebas unitarias.

---

## Descripción

Este proyecto ha sido desarrollado como práctica de aprendizaje de Spring Boot, siguiendo una arquitectura por capas y aplicando buenas prácticas de validación, separación de responsabilidades y testing.

La aplicación gestiona personas a través de su DNI, permitiendo operaciones CRUD y comprobando que los datos enviados sean correctos.

---

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Jakarta Validation
- Maven
- Swagger / OpenAPI
- JUnit 5
- Mockito

---

## Funcionalidades principales

- Crear una persona
- Consultar todas las personas
- Consultar una persona por DNI
- Actualizar una persona por DNI
- Eliminar una persona por DNI
- Validar campos obligatorios
- Validar el formato del DNI
- Comprobar que la letra del DNI sea correcta
- Evitar DNIs duplicados
- Documentar la API con Swagger
- Realizar tests unitarios del proyecto

---

## Estructura del proyecto

El proyecto se organiza por capas:

- **controller**: gestiona las peticiones HTTP
- **business / service**: contiene la lógica de negocio
- **repository**: acceso a datos
- **dto**: objetos de transferencia de datos
- **entity**: entidades de la aplicación
- **validator**: validaciones personalizadas
- **exception**: gestión de errores

---

## Endpoints principales

### Crear persona
**POST** `/persons`

Crea una nueva persona en el sistema.

### Obtener todas las personas
**GET** `/persons`

Devuelve el listado completo de personas.

### Obtener persona por DNI
**GET** `/persons/{dni}`

Busca una persona concreta a partir de su DNI.

### Actualizar persona por DNI
**PUT** `/persons/{dni}`

Actualiza los datos de una persona existente.

### Eliminar persona por DNI
**DELETE** `/persons/{dni}`

Elimina una persona del sistema.

---

## Validaciones implementadas

Se han aplicado validaciones sobre los DTOs de entrada para garantizar que los datos introducidos sean correctos.

### Validaciones básicas
- Campos obligatorios con `@NotBlank`
- Campos no nulos con `@NotNull`

### Validación del DNI
La validación del DNI comprueba:

- que tenga el formato correcto: **8 números y 1 letra**
- que la letra corresponda realmente al número introducido

### Ejemplo de DNI válido
`12345678Z`

---

## Manejo de errores

La aplicación contempla situaciones de error habituales, por ejemplo:

- datos incompletos
- datos con formato incorrecto
- DNI inválido
- DNI duplicado
- persona no encontrada

Las respuestas HTTP esperadas pueden incluir:

- `404 Not Found`

---

## Ejecución del proyecto

### 1. Clonar el repositorio

```bash
git clone <https://github.com/miguelangel-mh/EjercicioPracticoSpringBoot.git>