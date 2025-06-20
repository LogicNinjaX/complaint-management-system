# 🛠️ Complaint Management System

A role-based Spring Boot application for managing civic complaints. Citizens can report issues, departments can resolve them, and admins can oversee the entire process. Built with robust security, caching, validation, and email capabilities.

---

## 🚀 Features

### 👤 Citizen
- Register & login securely
- Submit new complaints with location, category, and image
- Track status of submitted complaints

### 🧑‍💼 Department Officer
- Access complaints assigned to their department
- Update complaint status: `SUBMITTED → PENDING → RESOLVED`
- View complaint history

### 🛡️ Admin
- Manage departments and complaint categories
- View all complaints system-wide
- Add/edit/remove users, departments, and categories

---

## 🧱 Tech Stack

| Layer             | Technology                          |
|------------------|--------------------------------------|
| Backend           | Spring Boot                         |
| Security          | Spring Security + JWT               |
| ORM               | Spring Data JPA + Hibernate         |
| Database          | MySQL                               |
| Caching           | Redis                               |
| Validation        | Spring Boot Starter Validation      |
| Object Mapping    | ModelMapper                         |
| Mail Notifications| Spring Boot Mail + Thymeleaf        |
| Token Handling    | JJWT (Java JWT Library)             |
| API Testing       | Spring Boot Test                    |

---

## 📦 Maven Dependencies Used

```xml
<!-- Spring Core -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- JPA & MySQL -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

<!-- Redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

<!-- ModelMapper -->
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.2.1</version>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Mail + Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```
## ⚙️ Configuration (application.yaml)
```xml
spring:
  application:
    name: complaint-management-system

  datasource:
    url: ${db-url}
    username: ${db-uname}
    password: ${db-pass}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true

  cache:
    type: redis

  data:
    redis:
      host: ${redis-host}
      password: ${redis-pass}
      port: ${redis-port}
      timeout: 60000

  mail:
    host: ${smtp-host}
    port: ${smtp-port}
    username: ${smtp-uname}
    password: ${smtp-pass}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

```

## 🔒 Security
- JWT-based stateless authentication

- Role-based access for CITIZEN, OFFICER, ADMIN

- Passwords securely hashed using BCrypt


## 📧 Email
- Email confirmation on complaint submission

- Uses Thymeleaf for rich HTML templates

- SMTP configuration via application.yaml

---
## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/LogicNinjaX/complaint-management-system.git
   cd complaint-management-system
   ```

2. Set up your .env or environment variables for:

- db-url, db-uname, db-pass

- redis-host, redis-pass, redis-port

- smtp-host, smtp-port, smtp-uname, smtp-pass

3. Run the project:

```bash
./mvnw spring-boot:run
```

## 📄 License

This project is licensed under the MIT License.


## 📧 Contact

For questions or contributions, reach out via [GitHub Issues](https://github.com/LogicNinjaX/complaint-management-system/issues).

   
