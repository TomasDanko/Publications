# 📚 Publications REST API

A Spring Boot REST API for managing publications, authors, categories, users, and roles with JWT-based authentication and PostgreSQL database.

The project demonstrates a full backend system including CRUD operations, role-based security, JWT authentication, and database integration.

---

## 🚀 Features

### 👤 User Management
- User registration and login
- Retrieve current authenticated user
- Admin-only user listing
- Role-based authorization (USER / ADMIN)

### 🔐 Authentication & Security
- JWT-based authentication
- Spring Security configuration
- BCrypt password encryption
- Role-based access control (RBAC)

### 📚 Publications
- Create, update, delete publications
- Filter publications by:
  - Language
  - Publication type
- Full CRUD support

### 👨‍🏫 Authors
- Create, update, delete authors
- Retrieve author by ID or list all authors

### 🏷️ Categories
- Create, update, delete categories
- Retrieve category by ID or list all categories

### 🛡️ Roles
- Assign roles to users
- Remove roles from users
- Role-based access control

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.5.4
- Spring Security
- Spring Data JPA / Hibernate
- PostgreSQL
- JWT (JJWT 0.12.6)
- Maven

---

## 🗄️ Database Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres_tuke
spring.datasource.username=postgres
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
