# 🏗️ Proyecto: Sistema de Gestión de Proyectos de Interiorismo

## Descripción
Aplicación web para gestionar clientes, proyectos y facturación de una empresa de diseño de interiores. Permite crear y seguir proyectos, gestionar pagos por etapas, administrar empresas colaboradoras y mantener un control económico por proyecto.

Esta app está desarrollada con Java y Spring Boot, usa Thymeleaf para las vistas y JPA/Hibernate para persistencia.

## Objetivo (para reclutadores)
Propuesta profesional lista para demostraciones técnicas: explica las decisiones arquitectónicas, muestra pruebas automatizadas y cómo desplegarla localmente. Ideal para evaluar habilidades en backend Java, JPA, diseño de bases de datos relacionales y desarrollo full-stack con plantillas del lado servidor.

## Características principales
- Gestión de clientes (CRUD).
- Gestión de proyectos (presupuesto, fechas, estado, cliente asociado).
- Control de pagos por etapas dentro de cada proyecto.
- Gestión de empresas colaboradoras/proveedoras.
- Asociación y registro de facturas por proyecto para control económico.

## Stack tecnológico
- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Thymeleaf
- MariaDB (configurable en `application.properties`)
- Maven 

## Requisitos
- JDK 17 instalado
- MariaDB (o la base de datos que prefieras) en ejecución si no usas configuración embebida
- Windows PowerShell (se incluyen comandos para PowerShell)

## Configuración básica
Edita `src/main/resources/application.properties` para ajustar la conexión a la base de datos. Ejemplo mínimo para MariaDB:

```
spring.datasource.url=jdbc:mariadb://localhost:3306/proyectointeriorismo
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

Nota: Si prefieres usar H2 para pruebas locales, modifica la URL y dependencias según convenga.


## Estructura del proyecto (resumen)
- `src/main/java` - código fuente Java (controladores, servicios, repositorios, modelos).
- `src/main/resources` - plantillas Thymeleaf, `application.properties`, recursos estáticos.
- `src/test` - pruebas unitarias y de integración.
- `pom.xml` - configuración de Maven y dependencias.

## Notas de diseño
- Arquitectura basada en Spring Boot con capas: controlador → servicio → repositorio.
- Modelado relacional con JPA/Hibernate (ManyToOne/OneToMany donde aplica).
- Vistas server-side con Thymeleaf para una interfaz sencilla y demostrativa.

## Despliegue y Docker (opcional)
En proceso...




