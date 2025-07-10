🏆 Customer Rewards Program

This Spring Boot application calculates reward points for customers based on their transaction history. It provides RESTful APIs to retrieve monthly and total reward points for individual customers or all customers.

📌 Features
- ✅ Calculates reward points based on transaction amount:
- 2 points for every dollar spent over $100
- 1 point for every dollar spent between $50 and $100
- ✅ Monthly and total reward summaries
- ✅ RESTful API with GET endpoints
- ✅ In-memory mock data for demonstration
- ✅ Unit and integration tests with multiple customers and transactions
- ✅ Exception handling and input validation
- ✅ JavaDocs and clean code structure


🚀 Technologies Used
- Java 17+
- Spring Boot 3.x
- Maven
- JUnit 5
- Spring Web MVC
- Spring Boot Test

📂 Project Structure

src/
├── main/
│   ├── java/com/example/
│   │   ├── controller          # REST API endpoints
│   │   ├── dto/                # Response DTOs
│   │   ├── exception/          # Custom exceptions and handlers
│   │   ├── model/              # Domain models
│   │   ├── service             # Business logic
│   │   └── RewardsApplication  # Main Spring Boot app
│
├── test/
│   └── java/com/example/rewards/
│       ├── RewardsServiceTest      # Unit tests
│       └── RewardsControllerTest   # Integration tests


📈 Reward Calculation Logic

| Purchase Amount | Points Earned | 
| ≤ $50 | 0 | 
| $51–$100 | 1 point per $1 | 
| > $100 | 2 points per $1 over $100 + 50 points for $51–$100 | 


🧮 Example:

A $120 purchase earns:
→ 2 × (120 − 100) + 1 × 50 = 40 + 50 = 90 points


🔗 API Endpoints

#####Get rewards for a specific customer

GET /api/rewards?customerId={customerId}

📥 Request

GET /api/rewards?customerId=C1

📤 Response

{
  "customerId": "C1",
  "monthlyPoints": {
    "2025-06": 90,
    "2025-07": 250
  },
  "totalPoints": 340
}

#####Get rewards for all customers

GET /api/rewards

📤 Response

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

🧪 Running Tests

Run all unit and integration tests:

mvn test

⚠️ Notes

- This project uses in-memory mock data for demonstration.
- No database is required to run the app.
- Do not check in target/ or bin/ folders.
- JavaDocs are included at class and method levels.

##👨‍💻 Author

S. Naveenarunkumar


Java Backend Developer

