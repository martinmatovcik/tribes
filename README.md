# Tribes - kingdom game
Tribes is a game, where you can build and develop your kingdom. Build barracks, and mines and collect some resources.  
The project was developed in Java and Spring Boot. As a build tool, it is using Maven and it has only a back end.  
_This is only example project for sharpening and demonstrating my skills._
### Here is how it works:
As a user, you can easily register and log in using Spring Validation. After successful login, you can receive generated JWT token. This process is made possible with Spring Security. Every endpoint apart from login and register is secured and requires authentication and authorization. There are two types of user roles - USER and ADMIN. Specific features of each roles will be updated. User can communicate with app through different endpoints. Sometimes it is necessary to provide input paramaters as @RequestBody in JSON format. Data from application are stored in NoSQL - Mongo Database. Testing of endpoints was made in Postman. The code has unit tests only at this point. It was done using jUnit 5, Mockito, and MockMVC. Version control of this project was done using Git and GitHub. The development environment was IntelliJ IDEA.  
### Technology used:  
+ Java 17, Spring Boot 3.0.6
+ Maven
+ Spring Security
+ JWT token
+ Spring Validation
+ JSON
+ NoSQL - MongoDB
+ Spring Data MongoDB
+ Postman, jUnit 5, Mockito, Mock MVC
+ Git, GitHub
+ IntelliJ IDEA
