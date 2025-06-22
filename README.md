## Spring Boot Inventory Management Application

This is a Spring Boot–based Inventory Management System that allows you to:
- Create and update item supply
- Reserve and cancel items
- Check item availability
- Use Redis
- Use H2 

---

##  Technology Stack

- Java 17+
- Spring Boot 3.x
- Spring Web, Spring Data JPA
- Redis (for caching)
- H2
- Maven

---
## Prerequisites

- Java 17+
- Maven
- Redis (Docker)

---

### Run Redis via Docker
docker run --name redis-inventory -p 6379:6379 -d redis
