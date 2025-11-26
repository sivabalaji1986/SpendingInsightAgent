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

### Set the OpenAI API Key
This project uses LangChain4j with OpenAI.
You must export the openai_key environment variable before running or building:

1. Sign in to OpenAI

    Go to: https://platform.openai.com/

2. Navigate to https://platform.openai.com/settings/organization/api-keys

3. Create a New Secret Key

    Click "Create new secret key"

    Give it a name (e.g., spending-insight-agent)

4. Copy the Key

    You’ll get a key that looks like:

    sk-abc123*************************

5. Set it as an Environment Variable

    You must set this before running or building the project.

#### macOS / Linux (bash/zsh)
```bash
export openai_key=sk-abc123*************************
````

#### Windows (PowerShell)
```bash
setx openai_key "sk-abc123*************************"
```

6. Verify It Works

Run:

echo $openai_key


You should see your key.

### Steps
```bash
# 1. Set API key
export openai_key=YOUR_OPENAI_API_KEY

# 2. Build
mvn clean install

# 3. Run
mvn spring-boot:run
```

### H2 Console
Visit:
```
http://localhost:8688/h2-console
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
      • getRecentTransactions(accountId, fromDate, toDate)
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

**In `application.yml`:**

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: none
      defer-datasource-initialization: true
  sql:
    init:
      mode: always
```

**Why these settings?**
- `ddl-auto: none` - Prevents Hibernate from auto-creating schema
- `defer-datasource-initialization: true` - Allows SQL scripts to run after JPA setup
- `mode: always` - Ensures `schema.sql` and `data.sql` execute on startup

**For production:**
- Disable SQL script auto-execution (`mode: never`)
- Use PostgreSQL or MySQL instead of H2
- Manage schema with Flyway or Liquibase
---

## 🧪 Test with curl

```bash
curl "http://localhost:8688/api/spending/insights?accountId=A123&year=2025&month=11"
```

---

## 📄 Example Insight Output

```
In November 2025, your total spending was $2,630.50, a significant increase from October's $690.00.

**Top Spending Categories:**
1. **Travel**: $2,000 (76% of total) - This includes a $580 flight with Scoot Airlines and a $1,400 flight with Singapore Airlines.
2. **Shopping**: $320 (12% of total) - A purchase at Uniqlo.
3. **Food**: $120.50 (5% of total) - A meal from GrabFood.
4. **Bills**: $210 (8% of total) - Payment to SP Services.

**Spending Spikes:**
- Your spending increased by approximately 280% compared to October, which is a significant spike.

**Single Transaction:**
- The $1,400 transaction with Singapore Airlines accounted for over 40% of your monthly total.

If you have any further questions or need more insights, feel free to ask!
```

---

## 📘 About This Project
This project accompanies the Medium article:

**“AI Agents in Banking: Building a Real Autonomous Spending Insight Agent”**

Part 1 → Single-agent architecture  
Part 2 → Multi-agent collaboration (coming next)

