
# Sales Management API

This API provides functionality for managing clients, products, and sales within a sales management system. It includes endpoints for creating, fetching, and updating clients, products, and sales.


## Class diagram

<img width="700" alt="Screenshot 2024-03-25 at 12 14 01 PM" src="https://github.com/andreh111/salesmanagement/assets/36291999/411b9771-780d-4930-bebc-79ccd59d9497">


## Swagger
The API documentation is available through Swagger UI. You can access and interact with the API documentation by navigating to the following URL in your web browser:
`http://localhost:8080/swagger-ui/index.html#/`

## Client API

### Fetch All Clients

- **GET** `/clients`
- Retrieves a list of all clients.

### Create a Client

- **POST** `/clients`
- Creates a new client.
- **Body**:
  ```json
  {
    "name": "John",
    "lastName": "Doe",
    "mobile": "1234567890"
  }
  ```

### Update a Client

- **PUT** `/clients/{id}`
- Updates an existing client.
- **Parameters**:
  - `id`: Client ID.
- **Body**:
  ```json
  {
    "name": "Jane",
    "lastName": "Doe",
    "mobile": "0987654321"
  }
  ```

## Product API

### Fetch All Products

- **GET** `/products`
- Retrieves a list of all products.

### Create a Product

- **POST** `/products`
- Creates a new product.
- **Body**:
  ```json
  {
    "name": "Laptop",
    "description": "High-end gaming laptop.",
    "category": "Electronics"
  }
  ```

### Update a Product

- **PUT** `/products/{id}`
- Updates an existing product.
- **Parameters**:
  - `id`: Product ID.
- **Body**:
  ```json
  {
    "name": "Gaming Laptop",
    "description": "High-end gaming laptop with updated specs.",
    "category": "Electronics"
  }
  ```

## Sale API

### Fetch All Sales

- **GET** `/sales`
- Retrieves a list of all sales.

### Create a Sale

- **POST** `/sales`
- Creates a new sale with multiple transactions.
- **Body**:
  ```json
  {
    "clientId": 1,
    "sellerId": 2,
    "transactions": [
      {
        "productId": 1,
        "quantity": 5,
        "price": 10.0
      },
      {
        "productId": 2,
        "quantity": 3,
        "price": 15.0
      }
    ]
  }
  ```

### Update a Sale

- **PATCH** `/sales/{id}`
- Partially updates a sale, such as editing quantities and prices of transactions.
- **Parameters**:
  - `id`: Sale ID.
- **Body**:
  ```json
  {
    "transactions": [
      {
        "transactionId": 1,
        "quantity": 10,
        "price": 12.5
      },
      {
        "transactionId": 2,
        "quantity": 5,
        "price": 20.0
      }
    ]
  }
  ```
  

## Running Unit Tests for Controllers
To execute the unit tests for your controllers, you can use Maven's test command. Navigate to the root directory of your project (where your pom.xml file is located) and run the following command in your terminal:
`mvn test`

This command will run all the tests in your project, including the unit tests for your controllers. If you want to run tests for a specific controller, you can use the -Dtest option followed by the class name of the test:

`mvn test -Dtest=YourControllerTest`

Make sure to replace YourControllerTest with the actual name of your test class for the controller. This section will guide users on how to run the unit tests for their controllers using Maven.

