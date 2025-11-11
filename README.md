# 🧠 Research Tracker - Backend API

A Spring Boot–based backend system for managing **academic and institutional research projects**, including authentication, authorization, project tracking, milestones, and document management.

This backend is secured using **JWT authentication** and role-based access control (Admin, PI, Member, Viewer).

---

## ⚙️ Project Overview

The **Research Tracker** helps institutions manage research projects, assign members, monitor milestones, and handle related documents securely.

### 🔐 Roles and Permissions

| Role | Description | Permissions |
|------|--------------|-------------|
| **ADMIN** | Full system access | Manage users, projects, milestones, and documents |
| **PI** | Principal Investigator | Manage own projects and associated milestones/documents |
| **MEMBER** | Researcher/assistant | Add milestones, upload documents |
| **VIEWER** | Read-only user | View public project data only |

---

## 🧩 Technologies Used

- **Java 21**
- **Spring Boot 3+**
- **Spring Security (JWT)**
- **Hibernate / JPA**
- **MySQL 8+**
- **Maven**
- **Lombok**

---

## 🗂 Folder Structure



---

## ⚙️ Setup & Configuration

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/SachiraJayadewa/ResearchTrackerBackEnd.git
cd ResearchTrackerBackEnd


###2️⃣ Configure MySQL

Configure the appliaction properties file using your MYSQL username and password.
The app automatically creates the database and tables.

###3️⃣ application.properties

Already included:

server.port=8080
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/research_tracker?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

logging.level.org.springframework=DEBUG

app.jwt.secret=MySuperSecureSecretKeyForJWT1234567890
app.jwt.expiration=86400000


###4️⃣ Run the Project

🗄 Database Setup

The app automatically creates all tables on first run.
1. Paste and copy contents of the data.sql file to the MYSQL command line after running the project the once.
2. Restart the project

👥 Default User Accounts

| Role   | Full Name              | Username             | Password |
| ------ | ---------------------- | -------------------- | -------- |
| ADMIN  | Admin User             | `admin@example.com`  | `12345`  |
| ADMIN  | Bruce Wayne            | `wayne@example.com`  | `98765`  |
| ADMIN  | John Doe               | `john@example.com`   | `12345`  |
| PI     | Principal Investigator | `pi@example.com`     | `12345`  |
| MEMBER | Member Researcher      | `member@example.com` | `12345`  |
| MEMBER | Tony Stark             | `ts@example.com`     | `54321`  |
| VIEWER | Viewer User            | `viewer@example.com` | `12345`  |


🔐 Authentication Flow

1. Signup → Create a new user (/api/auth/signup)
2. Login → Obtain JWT token (/api/auth/login)

3. In Postman, set:
           Authorization → Bearer <token>
4. Access secured endpoints based on your role.


📦 API Modules

| Module         | Description                      | Example Endpoints                                                  |
| -------------- | -------------------------------- | ------------------------------------------------------------------ |
| **Auth**       | User registration & login        | `/api/auth/signup`, `/api/auth/login`                              |
| **Users**      | Manage system users (Admin only) | `/api/users`, `/api/users/{id}`                                    |
| **Projects**   | Manage projects                  | `/api/projects`, `/api/projects/{id}`, `/api/projects/{id}/assign` |
| **Milestones** | Track project progress           | `/api/milestones`, `/api/milestones/{id}`                          |
| **Documents**  | Manage uploaded research files   | `/api/documents`, `/api/documents/{id}`                            |


📡 Postman Collection

All endpoints are pre-configured in the Postman collection:

📄 Research Tracker API.postman_collection.json

Includes Auth, Users, Projects, Milestones, and Documents.

Contains environment variables:

{{token}}
{{userId}}
{{projectId}}
{{milestoneId}}

To use:

1. Import the JSON file(ResearchTrackerAPI.postman_collection.json.txt) into Postman.
2. Login → copy JWT token → set it in {{token}}.

