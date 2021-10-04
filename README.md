# Spring Boot Docker Test Containers
This project demonstrates usage of docker test containers for integration testing.
Easy and fast to use.

Testcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common 
databases, Selenium web browsers, or anything else that can run in a Docker container.

## Tech stack
- Spring Boot
- Java 11
- Maven
- MySQL 5.7.X

## Useful links
- https://www.testcontainers.org/
- https://rieckpil.de/initialization-strategies-with-testcontainers-for-integration-tests/

## Test configuration
```
@Container
// TODO: pass docker image name if you need greater version of MySQL
public static final MySQLContainer CONTAINER = new MySQLContainer<>()
    .withUsername("root") // DB username
    .withPassword("root") // DB password
    .withDatabaseName("test-containers") // DB name
    .withInitScript("database/init.sql"); // initialization script (optional)

@DynamicPropertySource
static void properties(DynamicPropertyRegistry registry)
{
    registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", CONTAINER::getUsername);
    registry.add("spring.datasource.password", CONTAINER::getPassword);
}
```
By default, MySQL test container use MySQL Docker Image version **5.7.31**. If you want new version of
MySQL, you can specify it in MySQLContainer constructor.

## Contribution/Suggestions
If someone is interested for contribution or have some suggestions please contact me on e-mail `hedzaprog@gmail.com`.

## Author
Heril Muratović  
Software Engineer  
<br>
**Mobile**: +38269657962  
**E-mail**: hedzaprog@gmail.com  
**Skype**: hedza06  
**Twitter**: hedzakirk  
**LinkedIn**: https://www.linkedin.com/in/heril-muratovi%C4%87-021097132/  
**StackOverflow**: https://stackoverflow.com/users/4078505/heril-muratovic