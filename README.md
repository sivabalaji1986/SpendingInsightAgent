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
│   └── SpendingTools.java
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
├── config/
│   └── OpenAIConfig.java
│
├── SpendingInsightAgentApplication.java
```

---

## 🛠 Configuration

Ensure:
```properties
spring:
  jpa:
    hibernate:
        ddl-auto: none
        defer-datasource-initialization: true
  sql:
    init:
        mode: always
```

This ensures:
- schema.sql loads
- data.sql loads
- Hibernate does NOT drop the tables after wards

---

## 🧪 Test with curl

```bash
curl --location 'http://localhost:8080/api/spend/analyse' \
--header 'Content-Type: application/json' \
--data '{
  "query": "Analyse my spending for account A123 for November 2025 and compare it againt October 2025 spendings. Tell me in detail which categories I spent most"
}'
```

---

## 📄 Example Insight Output

```
Here's a detailed analysis of your spending for account **A123** in **October 2025** and **November 2025**:

### Total Spending
- **October 2025**: $690.00
- **November 2025**: $2,630.50

### Spending Categories Breakdown

#### October 2025 Transactions:
1. **Food**: 
   - **GrabFood**: $110.00
2. **Shopping**: 
   - **Shopee**: $200.00
3. **Bills**: 
   - **SP Services**: $180.00
4. **Travel**: 
   - **Bus Ticket**: $200.00

**Total for October**: $690.00

#### November 2025 Transactions:
1. **Food**: 
   - **GrabFood**: $120.50
2. **Travel**: 
   - **Scoot Airlines**: $580.00
   - **Singapore Airlines**: $1,400.00
3. **Shopping**: 
   - **Uniqlo**: $320.00
4. **Bills**: 
   - **SP Services**: $210.00

**Total for November**: $2,630.50

### Comparison of Spending Patterns
- **Overall Increase**: Your spending increased significantly from **$690.00 in October** to **$2,630.50 in November**.
- **Major Categories**:
  - **Travel** saw a substantial increase, with a total of **$2,000.00** in November compared to **$200.00** in October.
  - **Shopping** also increased from **$200.00** in October to **$320.00** in November.
  - **Food** and **Bills** saw a slight increase, but they were not the main contributors to the overall increase.

### Summary
Your spending in November was heavily influenced by travel expenses, particularly with significant purchases from airlines. If you have any further questions or need more insights, feel free to ask!
```

---

## 📘 About This Project
This project accompanies the Medium article:

**“AI Agents in Banking: Building a Real Autonomous Spending Insight Agent”**

Part 1 → Single-agent architecture  
Part 2 → Multi-agent collaboration (coming next)

