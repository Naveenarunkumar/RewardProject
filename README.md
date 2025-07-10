 Customer Rewards Program

This Spring Boot application calculates reward points for customers based on their transaction history. It provides RESTful APIs to retrieve monthly and total reward points for individual customers or all customers.


 Features
-  Calculates reward points based on transaction amount:
- 2 points for every dollar spent over $100
- 1 point for every dollar spent between $50 and $100
-  Monthly and total reward summaries
- RESTful API with GET endpoints
- In-memory mock data for demonstration
- Unit and integration tests with multiple customers and transactions
- Exception handling and input validation
- JavaDocs and clean code structure



 Technologies Used
- Java 17+
- Spring Boot 3.x
- Maven
- JUnit 5
- Spring Web MVC
- Spring Boot Test



Project Structure

- `controller/` – Exposes REST API endpoints (e.g., `RewardsController`)
- `service/` – Contains business logic for calculating reward points
- `repository/` – Provides access to transaction data (mocked for now)
- `model/` – Defines domain entities (e.g., `Transaction`)
- `dto/` – Data Transfer Objects used for API responses
- `exception/` – Custom exceptions and global exception handler
- `RewardsApplication.java` – Main Spring Boot application entry point
- `resources/` – Application configuration (e.g., `application.properties`)
- `test/` – Unit and integration tests for service and controller layers
- `pom.xml` – Maven build configuration
- `README.md` – Project documentation








Reward Calculation Logic

Reward points are calculated based on the amount spent in a single transaction:

- **If the purchase amount is ≤ $50**  
  → Earn **0 points**

- **If the purchase amount is between $51 and $100**  
  → Earn **1 point for every dollar spent over $50**

- **If the purchase amount is over $100**  
  → Earn:
  - **2 points for every dollar spent over $100**
  - **Plus 50 points** for the $51–$100 range

Example

- A purchase of **$120** earns:
  - 2 × (120 − 100) = 40 points
  - 1 × (100 − 50) = 50 points  
  - **Total = 90 points**




API Endpoints

Get rewards for a specific customer

GET /api/rewards?customerId={customerId}


Request

GET /api/rewards?customerId=C1



Response

{
  "customerId": "C1",
  "monthlyPoints": {
    "2025-06": 90,
    "2025-07": 250
  },
  "totalPoints": 340
}


Get rewards for all customers



GET /api/rewards


 Response

[
  {
    "customerId": "C1",
    "monthlyPoints": {
      "2025-06": 90,
      "2025-07": 250
    },
    "totalPoints": 340
  },
  {
    "customerId": "C2",
    "monthlyPoints": {
      "2025-06": 45,
      "2025-07": 110
    },
    "totalPoints": 155
  }
]

 Running Tests

Run all unit and integration tests:

mvn test


Notes

- This project uses in-memory mock data for demonstration.
- No database is required to run the app.
- Do not check in target/ or bin/ folders.
- JavaDocs are included at class and method levels.




Author

S. Naveenarunkumar


Java Backend Developer

