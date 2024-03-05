# jwt-token-spring
Creating and Returning a Jwt Token using O.Auth2.0

# Spring Boot JWT Authentication Example

This project demonstrates a simple implementation of JWT-based authentication using Spring Boot. It showcases user registration, user login, and secured endpoints that can only be accessed with valid JWT tokens.

## Features

- User registration and login.
- JWT token generation and validation.
- Secured REST API endpoints.
- Custom `UserDetailsService` to load user details from a database.
- Password encoding with Spring Security's `PasswordEncoder`.

- ##Endpoints
- "/register" - POST 
- I used on Postman {
  "email": "daniel@gmail.com",
  "password": "password@1234"
} in Json Format

-"/login" - POST
-You login in with the registered user then get a JWT as response;
Copy that JWT and Paste it in Authorization
Using OAuth 2.0 as type under the token field with Bearer prefeix so you can use the logged only feauters
"/movies" 
You get response as Json 
"{"title":"The Godfather","director":"Francis Ford Coppola"},{"title":"The Shawshank Redemption","director":"Frank Darabont"},{"title":"The Dark Knight","director":"Christopher Nolan"},{"title":"The Godfather","director":"Francis Ford Coppola"},{"title":"The Shawshank Redemption","director":"Frank Darabont"}"

Good Luck!:) 


- 

## Technologies
- Gradle
- Spring Boot
- Spring Security for authentication and authorization
- JWT for handling secure token generation and validation
- Hibernate with Spring Data JPA for database operations
- MySql for database

- ## Enviroment Variables
- You can find them in application.yaml
- ${MYSQL_USER}
- ${MYSQL_PASSWORD}
- JWT_SECRET: "You can set it up as whatever you like default is "secret"";
