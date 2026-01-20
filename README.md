Order Management REST API
Spring Boot | MySQL | JPA | REST


🚀 Features
✅ Complete CRUD operations for Orders and Order Items
✅ Database-first approach with MySQL
✅ 7 RESTful API endpoints
✅ Proper validation and error handling
✅ Foreign key relationships and data integrity
✅ Composite primary keys
✅ Transaction management
✅ Cascade delete operations


📌 Project Overview

This project is a RESTful API for an e-commerce order management system developed using Spring Boot and MySQL.
It supports full CRUD operations for:

Customers

Contact Mechanisms (Addresses)

Products

Orders

Order Items

The project follows standard layered architecture:

Controller → Service → Repository → Database

🏗️ Technology Stack

Java 17+

Spring Boot

Spring Web

Spring Data JPA

Spring Validation

MySQL

Hibernate

Maven

Postman (for API testing)

Lombok (optional)

📂 Project Structure
src/main/java/com/hotwax
│
├── controller      # REST Controllers
├── service         # Business Logic
├── repository      # JPA Repositories
├── entity          # JPA Entities
└── Application.java


🔌 API Endpoints
Orders
POST /orders - Create a new order
GET /orders/{id} - Retrieve order details
PUT /orders/{id} - Update order information
DELETE /orders/{id} - Delete order (cascade deletes items)
Order Items
POST /orders/{id}/items - Add item to order
PUT /orders/{id}/items/{seq} - Update order item
DELETE /orders/{id}/items/{seq} - Delete order item
