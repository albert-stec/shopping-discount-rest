# Shopping Price Calculator

## Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
        - [Clone the Repository](#clone-the-repository)
        - [Build the Application](#build-the-application)
        - [Run with Docker Compose](#run-with-docker-compose)
- [Running Tests](#running-tests)
- [Pre-Initialized Test Records](#pre-initialized-test-records)
- [API Documentation](#api-documentation)
    - [Retrieve Product Information](#retrieve-product-information)
    - [Calculate Total Price](#calculate-total-price)
- [Additional Information](#additional-information)
- [Appendix](#appendix)

---

## About the Project

The **Shopping Price Calculator** is a RESTful service designed to calculate product prices based on configurable discount policies. Each product is uniquely identified by a UUID, and the service supports two types of discounts:

1. **Quantity-Based Discounts**: Discounts that increase as the quantity of items ordered increases.
2. **Percentage-Based Discounts**: A fixed percentage discount applied to the total price.


---

## Features

- **Retrieve Product Information**: Fetch detailed information about products using their UUIDs.
- **Calculate Total Price**: Compute the total price for a given product and quantity, applying appropriate discounts.
- **Configurable Discount Policies**: Easily adjust discount thresholds and rates without modifying the codebase.
- **Combined Discount Strategies**: Supports both quantity-based and percentage-based discounts, applying only the highest applicable discount to ensure optimal pricing strategies.
- **Robust Testing**: Comprehensive unit and integration tests to ensure reliability and correctness.

---

## Getting Started

### Prerequisites

Ensure you have the following installed on your machine:

- **Java Development Kit (JDK) 17** or higher
- **Docker** and **Docker Compose**
- **Git**

### Installation

#### Clone the Repository

```bash
git clone https://github.com/yourusername/shopping-price-calculator.git
cd shopping-price-calculator
```

#### Build the Application

Before running the application with Docker, ensure it's built locally.

- **Using Gradle:**

  ```bash
  ./gradlew clean build
  ```

This will generate the JAR file located in `./build/libs/`.

#### Run with Docker Compose

The project is containerized using Docker Compose, orchestrating both the PostgreSQL database and the Spring Boot application.

1. **Ensure Docker is Running**

   Start Docker on your machine.

2. **Start Services**

   To run the services in detached mode (in the background), use:

   ```bash
   docker-compose up -d --build
   ```

3. **Verify Services**

   Check if both containers are running:

   ```bash
   docker-compose ps
   ```

   You should see `shopping-postgres` and `shopping-app` listed as running.

4. **Access the Application**

   Open your browser or use an API client like **Postman** to interact with the service at:

   ```
   http://localhost:8080
   ```
   You can check it by using pre-initialized test data:
    ```
   http://localhost:8080/products/123e4567-e89b-12d3-a456-426614174001
   ```


---

## Running Tests

The project includes both unit and integration tests to ensure the service operates as expected.

### Execute All Tests

From the project root, run:

```bash
./gradlew test
```

This command will execute all tests and provide a summary of the results.

### Test Reports

After running the tests, a detailed report is available at:

```
./build/reports/tests/test/index.html
```

Open this file in your browser to view comprehensive test results.

---

## Pre-Initialized Test Records

Upon starting the PostgreSQL container, the database is initialized with pre-defined test records using the `init.sql` script located in the `./db/` directory.

### `init.sql` Content

```sql
-- init.sql

INSERT INTO products (id, name, description, price, currency)
VALUES
    ('123e4567-e89b-12d3-a456-426614174001', 'Smartphone', 'High-end smartphone with 128GB storage.', 699.99, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174002', 'Laptop', '14-inch laptop with 16GB RAM.', 1199.99, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174003', 'Headphones', 'Noise-cancelling over-ear headphones.', 300.00, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174033', 'Headphones2', 'Noise-cancelling over-ear headphones.', 300.00, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174004', 'Coffee Maker', 'Automatic coffee maker with grinder.', 129.99, 'USD');
```

### Accessing Test Records

After the containers are up and running, you can access the PostgreSQL database to view the pre-initialized products.

- **Using psql (PostgreSQL CLI):**

  ```bash
  docker exec -it shopping-postgres psql -U shopping_user -d shoppingdb
  ```

  Once inside the psql shell, run:

  ```sql
  SELECT * FROM products;
  ```

- **Using pgAdmin or Other GUI Tools:**

  Connect to the database using the following credentials:

    - **Host**: `localhost`
    - **Port**: `5432`
    - **Database**: `shoppingdb`
    - **Username**: `shopping_user`
    - **Password**: `shopping_password`

---

## API Documentation

### Overview

The Shopping Price Calculator provides the following RESTful endpoints:

1. **Retrieve Product Information**
2. **Calculate Total Price**

Each endpoint is described in detail below, including example requests and responses.

### Retrieve Product Information

- **Endpoint:** `GET /products/{uuid}`
- **Description:** Fetches detailed information about a specific product using its UUID.

#### Request

- **URL Parameters:**
    - `uuid` (String): The UUID of the product to retrieve.

- **Example:**

  ```http
  GET /products/123e4567-e89b-12d3-a456-426614174001 HTTP/1.1
  Host: localhost:8080
  ```

#### Response

- **Success (200 OK):**

  ```json
  {
    "name": "Smartphone",
    "description": "High-end smartphone with 128GB storage.",
     "price": 699.99,
     "currency": "USD",
     "id": "123e4567-e89b-12d3-a456-426614174001"
}
- **Error Handling:**

    - **Unknown UUID**

  ```json
  {
    "error": "NOT_FOUND",
    "message": "Product with UUID 3fa85f64-5717-4562-b3fc-2c963f66afa6 not found"
  }
  ```

### Calculate Total Price

- **Endpoint:** `GET /products/{uuid}/total-price`
- **Description:** Calculates the total price for a given product and quantity, applying the appropriate discounts.

#### Request

- **URL Parameters:**
    - `uuid` (String): The UUID of the product.

- **Query Parameters:**
    - `quantity` (Integer): The number of units to purchase. - REQUIRED 

- **Example:**

  ```http
  GET /products/123e4567-e89b-12d3-a456-426614174003/total-price?quantity=9 HTTP/1.1
  Host: localhost:8080
  ```

#### Response

- **Success (200 OK):**

  ```json
  {
  "product": {
    "name": "Headphones",
    "description": "Noise-cancelling over-ear headphones.",
    "price": 300,
    "currency": "USD",
    "id": "123e4567-e89b-12d3-a456-426614174003"
  },
  "discountedPrice": 2430
  }
   ```

- **Error Handling:**

    - **Invalid or missing `quantity` Value (e.g., zero or negative or missing, while missing by default is set to 0):**

        - **Status Code:** `400 Bad Request`
        - **Response Body:**

          ```json
          {
            "error": "BAD_REQUEST",
            "message": "Quantity must be greater than zero."
          }
          ```

  - **Unknown UUID**

    - **Error (404 Not Found):**

      ```json
      {
        "error": "NOT_FOUND",
        "message": "Product with UUID 3fa85f64-5717-4562-b3fc-2c963f66afa6 not found"
      }
      ```

---

## Additional Information

### Configuration

The application is configured via `application.yml`. Key configurations include:

- **Spring Datasource:**

  ```yaml
  spring:
    datasource:
      url: jdbc:postgresql://localhost:5432/shoppingdb
      username: shopping_user
      password: shopping_password
      driver-class-name: org.postgresql.Driver
  ```

- **JPA Hibernate:**

  ```yaml
  spring:
    jpa:
      hibernate:
        ddl-auto: none
      properties:
        hibernate:
          dialect: org.hibernate.dialect.PostgreSQLDialect
  ```

    - **Note:** The `ddl-auto` is set to `none`, indicating that Hibernate should not manage the database schema. Use tools like Flyway or Liquibase for schema migrations.

- **Discount Configuration:**

  ```yaml
  discount:
    quantity:
      thresholds:
        - min: 1
          max: 9
          rate: 0.00
        - min: 10
          max: 19
          rate: 0.05
        - min: 20
          max: 49
          rate: 0.10
        - min: 50
          max: -1  # -1 represents no upper limit
          rate: 0.15
    percentage:
      discountRate: 0.10
  ```

    - **Description:**
        - **Quantity-Based Discounts:** Defines discount rates based on quantity thresholds.
        - **Percentage-Based Discounts:** A fixed discount rate applied to the total price.
        - The application chooses the one that is bigger depending on quantity. Cumulative discounts aren't used.


# Appendix

## Application Endpoints Summary

| Method | Endpoint                                     | Description                          |
| ------ | -------------------------------------------- | ------------------------------------ |
| GET    | `/products/{uuid}`                           | Retrieve product information by UUID  |
| GET    | `/products/{uuid}/total-price?quantity={qty}`| Calculate total price with discounts  |

## Sample Data

### Products
The following products are pre-initialized in the database using the `init.sql` script.

| UUID                                   | Name         | Description                            | Price (USD) | Currency |
| -------------------------------------- | ------------ | -------------------------------------- | ----------- | -------- |
| `123e4567-e89b-12d3-a456-426614174001` | Smartphone   | High-end smartphone with 128GB storage. | 699.99      | USD      |
| `123e4567-e89b-12d3-a456-426614174002` | Laptop       | 14-inch laptop with 16GB RAM.           | 1199.99     | USD      |
| `123e4567-e89b-12d3-a456-426614174003` | Headphones   | Noise-cancelling over-ear headphones.   | 300.00      | USD      |
| `123e4567-e89b-12d3-a456-426614174033` | Headphones2  | Noise-cancelling over-ear headphones.   | 300.00      | USD      |
| `123e4567-e89b-12d3-a456-426614174004` | Coffee Maker | Automatic coffee maker with grinder.    | 129.99      | USD      |

> **Note:** These products are available for testing the API endpoints. You can retrieve their information or calculate prices using their respective UUIDs.

## Discount Policies

### Quantity-Based Discounts

- **1-9 units:** No discount (0%)
- **10-19 units:** 5% discount
- **20-49 units:** 10% discount
- **50+ units:** 15% discount

### Percentage-Based Discounts

- **Fixed Rate:** 10% off the total price, regardless of quantity.

### Combining Discounts

Both discount types are **mutually exclusive**. The final price is calculated by applying **only the highest applicable discount** between quantity-based and percentage-based discounts.

---

