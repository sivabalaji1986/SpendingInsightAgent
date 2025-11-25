# Spending Insight Agent (Java + Spring Boot + LangChain4j)
### Java + Spring Boot + LangChain4j (Production-grade Agentic Pattern)

This project demonstrates a real, production-style AI Agent built with:
* Java 21 
* Spring Boot 3.5.8 
* LangChain4j 1.8.0 
* H2 database

Autonomous tool-calling + reasoning 
* The agent explains a customer's monthly spending insight by analyzing:
* Monthly totals 
* Individual transactions 
* Spending patterns, spikes, anomalies

It autonomously decides which tools to call and when.

---

## ⭐ Summary
This project demonstrates how to build a real, production-aligned AI Agent using Java + Spring Boot + LangChain4j.

Instead of writing explicit “if/else” workflows, the agent learns to:
* interpret user intent
* plan multi-step actions 
* call domain tools autonomously 
* combine LLM reasoning with real transactional data 
* generate meaningful, auditable financial insights

It showcases a complete agentic pattern:
* LLM agent → prompts, autonomy, planning 
* Tools → controlled access to bank data 
* Service + Controller → enterprise Spring structure 
* H2 dataset → realistic financial transactions 
* ReAct + tool calling → explainable planning traces

This repository is a hands-on reference for Java engineers exploring agentic architectures without switching to Python.

---
## 🔍 Features

### ✔ AI Agent (LangChain4j)
- ReAct-style reasoning
- Tool calling
- Short-term memory (MessageWindowChatMemory)
- Natural-language query understanding

### ✔ Two Banking Tools
1. **getAccountSummary(accountId, year, month)**
2. **getRecentTransactions(accountId, fromDate, toDate)**

Agent decides whether to call 0, 1, or both.

### ✔ H2 In-Memory Database
Tables:
- `accounts`
- `transactions`

Seeded automatically using:
- `schema.sql`
- `data.sql`

### ✔ REST API
`GET /api/spending/insights?accountId=A123&year=2025&month=11`

Backend converts these parameters into a natural-language prompt passed to the agent.
This allows:
* Strong validation 
* Secure account authorization 
* Fully deterministic API contract

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
      HTTP GET (accountId, year, month)
                    ↓
     SpendingInsightController
        • Validates inputs
        • Builds internal Natural Language prompt
                    ↓
        SpendingInsightService
        • Logging, error handling
        • Calls the agent
                    ↓
        SpendingInsightAgent
        • Parses prompt
        • Autonomous planning
        • Decides tool usage
                    ↓
              SpendingTools
      • getAccountSummary(accountId, year, month)
      • getRecentTransactions(accountId, year, month)
                    ↓
         AccountSummaryService / TransactionService
                    ↓
              Insight Response
```

---

## 📦 Project Structure

````
src/main/java/com/hbs/spending_insight_agent/
│
├── agent/
│   ├── SpendingInsightAgent.java       # LLM agent interface
│   └── SpendingTools.java              # Tool functions
│
├── controller/
│   └── SpendingInsightController.java  # GET endpoint
│
├── service/
│   ├── SpendingInsightService.java     # builds prompt + calls agent
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
│   └── AgentConfig.java               # LangChain4j model setup
│
└── SpendingInsightAgentApplication.java
````

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
This prevents Hibernate from destroying schema and allows SQL scripts to load in order.

---

## 🧪 Test with curl

```bash
curl "http://localhost:8080/api/spending/insights?accountId=A123&year=2025&month=11"
```

---

## 📄 Example Insight Output

```
In November 2025, your total spending for account A123 was **$2,630.50**, a substantial increase from **$690.00** in October, marking a **280% increase**.

### Top Spending Categories:
1. **Travel**: $2,000.00 (76% of total)
   - Major transactions included $580 with Scoot Airlines and $1,400 with Singapore Airlines.
2. **Shopping**: $320.00 (12% of total)
   - A purchase at Uniqlo.
3. **Bills**: $210.00 (8% of total)
   - Payment to SP Services.
4. **Food**: $120.50 (5% of total)
   - A transaction with GrabFood.

### Spending Spikes:
- The overall increase is driven by travel expenses, particularly the **$1,400** transaction with Singapore Airlines, which constitutes **53%** of your monthly total.

This month reflects a significant focus on travel, leading to a notable rise in your spending. If you have any questions or need more insights, just let me know!
```

---

## 📘 About This Project
This project accompanies the Medium article:

**“AI Agents in Banking: Building a Real Autonomous Spending Insight Agent”**

Part 1 → Single-agent architecture  
Part 2 → Multi-agent collaboration (coming next)

