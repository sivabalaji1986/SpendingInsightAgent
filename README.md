# Spending Insight Agent (Java + Spring Boot + LangChain4j)

A production-grade example of a **single AI agent** built using **Java**, **Spring Boot**, and **LangChain4j**.  
This agent explains monthly spending for a banking customer by autonomously:

- Understanding a natural-language query
- Deciding what data it needs
- Calling two tools (`getAccountSummary` and `getRecentTransactions`)
- Reasoning over the results (hybrid: LLM + rules)
- Generating a clear financial insight explanation

---

## 🔍 Features

### ✔ AI Agent (LangChain4j)
- ReAct-style reasoning
- Tool calling
- Short-term memory
- Natural-language query understanding

### ✔ Two Banking Tools
1. **Monthly Total Summary**
2. **Recent Transactions (date range)**

### ✔ H2 In-Memory Database
Tables:
- `accounts`
- `transactions`

Seeded automatically using:
- `schema.sql`
- `data.sql`

### ✔ REST API
`POST /api/spend/analyse`

Example:
```json
{
  "query": "Analyse my spending for account A123 for November 2025"
}
```

---

## 🚀 How to Run

### Prerequisites
- Java 21
- Maven 3.9+

### Steps
```bash
mvn clean install
mvn spring-boot:run
```

### H2 Console
Visit:
```
http://localhost:8080/h2-console
```

JDBC URL:
```
jdbc:h2:mem:bankdb
```

---

## 🧠 Architecture

```
User Query
    ↓
SpendingInsightAgent
    • Parses query
    • Plans steps
    • Calls tools
    • Synthesizes insight
    ↓
AccountSummaryService / TransactionService
    ↓
Final insight response
```

---

## 📦 Project Structure

```
src/main/java/com/hbs/spending_insight_agent/
│
├── agent/
│   └── SpendingInsightAgent.java
│
├── controller/
│   └── SpendingController.java
│
├── service/
│   ├── AccountSummaryService.java
│   └── TransactionService.java
│
├── repository/
│   ├── AccountRepository.java
│   └── TransactionRepository.java
│
├── model/
│   ├── Account.java
│   └── Transaction.java
│
└── config/
    └── OpenAIConfig.java
```

---

## 🛠 Configuration

Ensure:
```properties
spring.jpa.hibernate.ddl-auto=none
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

This ensures:
- schema.sql loads
- data.sql loads
- Hibernate does NOT drop the tables afterwards

---

## 📄 Example Insight Output

```
Your spending for November 2025 increased by 28% compared to October.
The largest contributor was Travel (SGD 1980), mainly due to flights on Scoot & Singapore Airlines.
You also spent more on Shopping (+SGD 120).
No unusual or suspicious transactions detected.
```

---

## 🧪 Test with curl

```bash
curl -X POST http://localhost:8080/api/spend/analyse   -H "Content-Type: application/json"   -d '{"query": "Analyse my spending for account A123 for November 2025"}'
```

---

## 📘 About This Project
This project accompanies the Medium article:

**“AI Agents in Banking: Building a Real Autonomous Spending Insight Agent”**

Part 1 → Single-agent architecture  
Part 2 → Multi-agent collaboration (coming next)

