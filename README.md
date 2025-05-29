# User Service

Este microservicio forma parte del sistema de eCommerce distribuido y se encarga de gestionar la información de los 
usuarios.

## 📦 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- MySQL
- Feign Client
- Eureka Client
- Lombok

## ⚙️ Endpoints principales

| Método | Ruta                  | Descripción                  |
|--------|-----------------------|------------------------------|
| POST   | /api/user             | Crear un nuevo usuario       |
| PUT    | /api/user/update/{id} | Actualizar usuario existente |
| GET    | /api/user/findUser    | Buscar un usuario            |
| DELETE | /api/user/delete/{id} | Eliminar usuario             |
| GET    | /api/user/{email}     | Buscar un usuario por email  |

## 🗃️ Base de datos

Este servicio utiliza una base de datos MySQL. Las entidades principales son:

- `Crear un nuevo User` (nombre, apellidos, email).
- `Actualizar un User` (nombre, apellidos, email), modifica el que necesites.
- `Buscar un User` por id (email en el body de Postman).
- `Eliminar un User` por id (id en la Url de Postman).
- `Buscar un User` por email (email en la Url de Postman, usa Feing Clients).

## 🔌 Comunicación con otros servicios

Este servicio es **cliente de Eureka** y se comunica con otros microservicios mediante **Feign Clients**.

## 🧪 Tests

Incluye:

- Pruebas unitarias con JUnit y Mockito.
- Validaciones de endpoints con MockMvc.
- Pruebas de integración con @SpringBootTest.
- Ejecuta los tests con Maven:
  ```bash
  mvn test
  ```

## 📫 Instrucciones para ejecutar el servicio
1. Clona el repositorio.
2. Configura el archivo `application.properties`  con los datos de conexión a la base de datos MySQL.
3. Asegúrate de que el servidor Eureka esté corriendo.
4. Asegúrate que el Gateway esté corriendo.
5. Asegúrate de que los demás micro servicios están corriendo.
5. Asegúrate de que la base de datos MySQL esté corriendo.
6. Ejecuta el servicio con tu IDE o usando Maven:
   ```bash
   mvn spring-boot:run
   ```


## 	📁 EndPoints
- Puedes ver todos los endPoints en `http://localhost:8081/api/user` una vez que el servicio esté corriendo.

 
## 🧪 Postman

- Usa Postman o cualquier cliente HTTP para probar los endpoints, Puedes acceder a las colección en el repositorio
Principal de la apilcacion `.github/static/e-commerce.postman_collection.json`.
- Tienes un Json para crear usuarios en Postman `src/main/resources/Json/NewUsers.json`.

## 🗂️ Repositorio Principal
- 🔗 [GitHub Organization](https://github.com/IronHackProject)

## 👨‍💻 Autor
-[DevJerryX](https://github.com/planetWeb252)