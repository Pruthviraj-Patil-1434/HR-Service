# 👥 HR Service

> A Spring Boot microservice for HR registration & authentication — part of the Startup CRM microservices ecosystem.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.2-6DB33F?logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Eureka](https://img.shields.io/badge/Service%20Discovery-Eureka-yellowgreen)

---

## 📖 Overview

**HR Service** handles HR onboarding and login for the Startup CRM platform. It registers itself with **Eureka** for service discovery and talks to the **`STARTUP-AUTHENTICATION-SERVICE`** via **OpenFeign** to issue JWT access & refresh tokens.

---

## ✨ Features

| Feature | Description |
|---|---|
| 📝 **HR Registration** | Register a new HR with company & personal details |
| 🔑 **Unique HR Code** | Auto-generates a unique code like `HR123456` |
| 🔐 **Secure Login** | Validates email, password & HR code |
| 🛡️ **Password Hashing** | BCrypt encoding for all stored passwords |
| 🎫 **Token Issuance** | Delegates JWT generation to the Auth Service via Feign |
| ⚠️ **Centralized Errors** | Global exception handling with consistent API responses |
| 🖼️ **Profile Photo Upload** | Stores and serves HR profile images |

---

## 🛠️ Tech Stack

- ☕ **Java 17**
- 🍃 **Spring Boot 3.5.14**
- ☁️ **Spring Cloud 2025.0.2** — Eureka Client, OpenFeign
- 🗄️ **Spring Data JPA**
- 🐬 **MySQL**
- 🧩 **Lombok**
- 🔒 **BCrypt**

---

## ✅ Prerequisites

- ☕ JDK 17+
- 📦 Maven 3.6+
- 🐬 MySQL running locally with a database named `startup_crm`
- 🧭 A running **Eureka Server** (default: `http://localhost:8761/eureka/`)
- 🔐 `STARTUP-AUTHENTICATION-SERVICE` registered with Eureka

---

## ⚙️ Configuration

Located at `src/main/resources/application.properties`:

```properties
spring.application.name=hr-service
server.port=8087

# Eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/startup_crm
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

> ⚠️ **Heads up:** `FileOps.java` uses a hardcoded local path for profile photo storage. Update it before running on another machine.

---

## 🚀 Running the Service

```bash
# 🔧 Build
./mvnw clean install

# ▶️ Run
./mvnw spring-boot:run
```

The service starts on **port `8087`** and auto-registers with Eureka. 🎉

---

## 📡 API Endpoints

Base path: `/api/hr`

### 📝 Register HR

```
POST /api/hr/register
```

**Request Body**

```json
{
  "companyName": "Acme Corp",
  "hrName": "Jane Doe",
  "email": "jane@acme.com",
  "mobile": "9876543210",
  "password": "secret123",
  "designation": "HR Manager",
  "department": "Human Resources",
  "address": "123 Main St",
  "country": "India",
  "state": "Maharashtra",
  "postalCode": "400001"
}
```

**Response ✅**

```json
{
  "message": "HR registered successfully",
  "hrCode": "HR123456"
}
```

---

### 🔑 Login HR

```
POST /api/hr/login
```

**Request Body**

```json
{
  "email": "jane@acme.com",
  "password": "secret123",
  "hrCode": "HR123456"
}
```

**Response ✅**

```json
{
  "accessToken": "...",
  "refreshToken": "...",
  "hrCode": "HR123456",
  "hrName": "Jane Doe"
}
```

---

## 📂 Project Structure

```
src/main/java/com/microservice/hr_service
├── 📁 config/        # BCrypt password encoder configuration
├── 📁 controller/     # REST controllers (HrController)
├── 📁 dto/            # Data transfer objects
├── 📁 entity/         # JPA entities (HrUser)
├── 📁 exception/      # Custom exceptions & global exception handler
├── 📁 feign/          # Feign client for Auth Service
├── 📁 repository/     # Spring Data JPA repositories
├── 📁 service/        # Business logic (Hrservice, FileOps)
└── 📁 util/           # Utility classes & message constants
```

---

## 🗄️ Database Schema

The **`hr_users`** table (schema: `startup_crm`) stores:

| Column | Description |
|---|---|
| `id` | Auto-generated primary key |
| `hr_code` | Unique HR identifier |
| `company_name` | HR's company |
| `hr_name` | Full name |
| `email` | Unique login email |
| `mobile` | Contact number |
| `password` | BCrypt-hashed password |
| `designation`, `department` | Job details |
| `address`, `country`, `state`, `postal_code` | Location details |
| `profile_photo` | Path to uploaded profile image |
| `is_active` | Account status |
| `created_at` | Auto-set timestamp |

---

## 🧭 Architecture Flow

```
Client → 🏢 HR Service (8087)
            │
            ├── 🗄️ MySQL (startup_crm)
            ├── 🧭 Eureka Discovery
            └── 🔐 STARTUP-AUTHENTICATION-SERVICE (via Feign) → 🎫 JWT Tokens
```

---

