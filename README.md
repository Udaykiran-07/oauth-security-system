# OAuth Security System

## Project Overview

OAuth Security System is a Spring Boot based authentication and authorization platform implementing JWT Authentication, Role-Based Access Control (RBAC), Multi-Tenant Architecture, Refresh Token Management, Audit Logging, and Account Locking.

The system provides secure user authentication and fine-grained authorization using Spring Security and JWT tokens.

---

## Features

### Authentication

* User Registration
* User Login
* JWT Access Token Generation
* Refresh Token Generation
* Logout Support
* Token Validation

### Authorization

* Role Based Access Control (RBAC)
* Permission Based Access
* Method Level Security using @PreAuthorize

### Multi-Tenant Support

* Create Tenant
* Assign Users to Tenants
* Retrieve Users by Tenant

### Security Features

* Password Encryption using BCrypt
* Account Lock after Multiple Failed Attempts
* Refresh Token Revocation
* Global Exception Handling

### Audit Logging

* Login Tracking
* Logout Tracking
* Registration Tracking
* Refresh Token Tracking

---

## Technology Stack

Backend:

* Java 21
* Spring Boot 3.5.3
* Spring Security
* Spring Data JPA
* JWT (Nimbus)

Database:

* MySQL 8

Tools:

* Postman
* Swagger OpenAPI
* Maven

---

## Architecture

                    +------------------+
                    |     Postman      |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    |   REST APIs      |
                    |  Controllers     |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    |    Services      |
                    |------------------|
                    | AuthService      |
                    | AuditLogService  |
                    | AccountLockSvc   |
                    | JwtService       |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    | Spring Security  |
                    | JWT Validation   |
                    | RBAC             |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    | Repositories     |
                    | Spring Data JPA  |
                    +--------+---------+
                             |
                             v
                    +------------------+
                    | MySQL Database   |
                    +------------------+

Database Tables
---------------
Users
Roles
Permissions
User_Roles
Role_Permissions
Tenants
Refresh_Tokens
Audit_Logs
Password_Reset_Tokens

Client (Postman)
|
v
Controllers
|
v
Services
|
v
Spring Security + JWT
|
v
Repositories
|
v
MySQL Database

---

## Database Tables

1. users
2. roles
3. permissions
4. user_roles
5. role_permissions
6. tenants
7. refresh_tokens
8. audit_logs
9. password_reset_tokens

---

## API Endpoints

### Authentication

POST /api/auth/register

POST /api/auth/login

POST /api/auth/refresh

POST /api/auth/logout

### Tenant

POST /api/tenants

GET /api/tenants

### User

GET /api/users

GET /api/users/tenant/{tenantId}

### Audit

GET /api/audit

---

## Project Setup

### Clone Project

git clone <[repository-url](https://github.com/Udaykiran-07/oauth-security-system)>

cd oauth-security-system

---

### Create Database

CREATE DATABASE oauth_security_db;

---

### Configure application.yml

spring:
datasource:
url: jdbc:mysql://localhost:3306/oauth_security_db
username: root
password: your_password

---

### Run Project

mvn clean install

mvn spring-boot:run

or run OauthSecuritySystemApplication.java from IntelliJ.

---

## Database Schema

Database schema is provided in:

database/oauth_security_db_schema.sql

Import using MySQL Workbench.

---

## Postman Collection

Postman collection is available in:

postman/OAuthSecuritySystem.postman_collection.json

---

## Security Workflow

1. User Registers
2. User Logs In
3. JWT Access Token Generated
4. Refresh Token Generated
5. Protected APIs Require JWT Token
6. RBAC Validates Permissions
7. Audit Logs Stored in Database

---

## Implemented Security Controls

* JWT Authentication
* Refresh Token Rotation
* RBAC Authorization
* Password Encryption
* Multi-Tenant Isolation
* Audit Logging
* Account Locking
* Exception Handling

---

## Author

Shanmuga Sri Udaykiran M S
