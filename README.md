# Gestión de Pedidos API

API REST desarrollada con **Spring Boot** para la gestión de pedidos en un entorno de terminales (tipo restaurante / cocina).  
Permite gestionar terminales, categorías, prdouctos y pedidos con sus líneas de productos y control de estados.

---

## Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Swagger / SpringDoc OpenAPI

---

## Arquitectura del proyecto

El proyecto sigue una arquitectura en capas:

Controller -> Service -> Repository -> Database

Además se utilizan:

- DTOs para la comunicación entre capas
- Entidades JPA para el modelo de datos
- Mapeos manuales Model <-> DTO
- Manejo global de excepciones

---

## Entidades principales

- **Terminal** -> Punto de atención donde se generan pedidos
- **Categoría** -> Clasificación de productos
- **Producto** -> Artículos disponibles para vender
- **Pedido** -> Ticket de compra
- **PedidoProducto** -> Línea de pedido (relación Pedido - Producto)
- **EstadoPedido** -> Flujo del pedido

---

## Estados del pedido

Los pedidos siguen este flujo:

CREADO -> PREPARACION -> LISTO -> PAGADO -> ENTREGADO

No se permiten saltos de estado.

---

##  Funcionalidades principales

###  Terminales
- Crear terminal
- Listar terminales
- Obtener terminal por ID

###  Categorías
- Crear categoría
- Listar categorías
- Obtener categoría por ID

###  Productos
- Crear producto
- Listar productos (con filtros y ordenación)
- Actualizar producto
- Activar / desactivar producto

###  Pedidos
- Crear pedido
- Añadir productos a pedido
- Eliminar productos (total o parcial)
- Cambiar estado del pedido
- Obtener pedido por código
- Listar pedidos (con filtro por estado)

---

## Endpoints principales

### Terminales

POST /api/terminales
GET /api/terminales
GET /api/terminales/{id}

### Categorías

POST /api/categorias
GET /api/categorias
GET /api/categorias/{id}

### Productos

POST /api/productos
GET /api/productos
PUT /api/productos/{id}
PATCH /api/productos/{id}/estado

### Pedidos

POST /api/pedidos
POST /api/pedidos/{pedidoId}/productos
DELETE /api/pedidos/{pedidoId}/productos/{productoId}
PATCH /api/pedidos/{pedidoId}/estado
GET /api/pedidos/codigo/{codigo}
GET /api/pedidos

---

## Datos de prueba

El proyecto utiliza `data.sql` para insertar datos iniciales:

- Terminales
- Categorías
- Productos
- Pedidos (opcional)

---

## ⚙️ Configuración del proyecto

En `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_pedidos_bd?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
---
Documentación API (Swagger)

Una vez ejecutado el proyecto:
http://localhost:8080/doc/swagger-ui.html

Cómo ejecutar el proyecto
Clonar repositorio
Crear base de datos MySQL (o dejar que Spring la cree)

Ejecutar el proyecto con:
mvn spring-boot:run

Probar endpoints con Postman o Swagger

Autor:
Laura Arias, Daniel Norbert y Rafael Porcel
Proyecto desarrollado como parte de un bootcamp de programación full stack.