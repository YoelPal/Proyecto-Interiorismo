# 🏗️ Sistema de Gestión de Proyectos de Interiorismo

## 📌 Descripción
Esta aplicación permite gestionar clientes y proyectos para una empresa de **diseño de interiores**.  
Controla proyectos en curso, presupuesto asignado, pagos por etapas y la asignación de clientes.

Desarrollada en **Java (Spring Boot)** con **Thymeleaf** en la capa de presentación. 

## 🚀 Tecnologías utilizadas
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Thymeleaf
- MariaDB 
- Maven
- Git


## 📂 Funcionalidades principales
- Gestión de **clientes** (crear/editar/eliminar/listar).
- Creación y seguimiento de **proyectos** (presupuesto, fecha de inicio, estado).
- Relación **ManyToOne** entre proyectos y clientes.
- Control de **pagos por etapas** para cada proyecto.
- Registro y gestión de **empresas colaboradoras/proveedoras** asociadas a proyectos.
- Asociación de **facturas** a cada proyecto para control económico.



## Próximos Pasos (Posibles)
* Considerar la implementación de seguridad como un microservicio con Spring Security.
* Mejorar el manejo de errores y las respuestas de la API.
