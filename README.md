<a name="readme-top"></a>

# Proyecto Spring Boot con Keycloak y JWT

> Description.
Este proyecto es una aplicación backend desarrollada en Spring Boot que integra Keycloak como servidor de autenticación y autorización, junto con JWT para el manejo de tokens de acceso. La estructura incluye controladores, servicios, DTOs y configuraciones de seguridad.


## Características

-Spring Boot como framework principal.

-Keycloak para autenticación y autorización de usuarios.

-JWT (JSON Web Token) para manejar la autenticación basada en tokens.

-Controladores REST que utilizan DTOs para el intercambio de datos.

-Servicio encargado de la configuración y manejo de Keycloak.

-SecurityConfig que gestiona la configuración de seguridad, incluyendo la validación de tokens JWT.




### Prerequisites

-- Java 17 o superior.
-- Maven como herramienta de construcción.
-- Un servidor Keycloak en ejecución.
-- Base de datos configurada (si es necesario para la aplicación).



<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Usage
-La aplicación expone endpoints protegidos por JWT.

-Los usuarios se autentican a través de Keycloak y reciben un token JWT.

-El token debe ser enviado en las solicitudes posteriores a los endpoints protegidos como un Bearer Token.


## Authors

👤 **Author1**

- GitHub:https://github.com/RodrigoAvila56
- LinkedIn:https://www.linkedin.com/in/rodrigo-avila-76068729a/

## 🤝 Contributing

Si deseas contribuir a este proyecto, siéntete libre de hacer un fork, crear una rama con tus cambios y abrir un pull request.

## Show your support

Give a ⭐️ if you like this project!

