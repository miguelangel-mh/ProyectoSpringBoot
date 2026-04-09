
---

# `CHANGELOG.md`

```md
# Changelog

Todos los cambios importantes realizados en este proyecto se documentan en este archivo.

---

## [Versión final] - 2026-04-09

### Añadido
- Documentación del proyecto mediante `README.md`
- Registro de cambios mediante `CHANGELOG.md`
- Explicación general de tecnologías, estructura y ejecución del proyecto
- Descripción de endpoints y validaciones implementadas

### Mejorado
- Presentación general del proyecto para facilitar su comprensión y entrega
- Claridad en la documentación técnica y funcional

---

## [1.0] - 2026-04-08

### Añadido
- Tests unitarios del proyecto con JUnit 5 y Mockito
- Cobertura de pruebas sobre lógica de negocio y controladores
- Verificación del comportamiento de los endpoints principales

### Mejorado
- Fiabilidad del proyecto mediante pruebas automáticas
- Calidad del código y detección de errores en fases tempranas

### Corregido
- Revisión de casos de error en operaciones `POST` y `PUT`
- Ajustes en validaciones y en el tratamiento de respuestas incorrectas

---

## [Versión inicial] - 2026-04-07

### Añadido
- Creación inicial del proyecto Spring Boot
- Estructura por capas: controller, service/business, repository, dto, entity y validator
- Endpoints CRUD para la gestión de personas
- Integración de validaciones básicas en los DTOs
- Validación del DNI por formato y letra correcta
- Comprobación de duplicidad de DNI
- Documentación Swagger/OpenAPI

### Mejorado
- Organización del proyecto y separación de responsabilidades
- Base funcional completa para la gestión de personas

### Corregido
- Ajustes iniciales en lógica de validación y funcionamiento general de la API