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
* Uses LangChain4j’s tool-calling (ReAct-style) to let the LLM plan its calls and produce traceable logs of each tool invocation.

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
* A deterministic API contract

 In a real bank, this endpoint would be fronted by an authorization layer (e.g., Spring Security + OAuth2/JWT) to ensure account-level access control.

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
## Mapping the Agent Blueprint (11 Agent Components) to the Code
This section grounds the conceptual 11-component model of an AI Agent in the **actual Java classes** in this repository.

| # | Component | Implementation in Code | Location                                                                                                                                             |
|---|-----------|------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| **1** | **Goal / Purpose** | Defined in `@SystemMessage` annotation: *"Analyze customer spending for a given period, compare with previous periods, identify spikes and unusual patterns"* | `SpendingInsightAgent.java`                                                                                                                          |
| **2** | **Perception / Input** | REST endpoint receives `accountId`, `year`, `month` parameters and converts them to natural language query | `SpendingInsightController.java` → `SpendingInsightService.java`                                                                                     |
| **3** | **Reasoning Ability** | LLM-driven reasoning via LangChain4j's `AiServices`. Agent analyzes tool outputs to infer patterns and synthesize insights | `AgentConfig.java` (model setup)<br>`SpendingInsightAgent.java` (reasoning prompt)                                                                   |
| **4** | **Tools / Skills** | Two `@Tool` annotated methods:<br>• `getAccountSummary(accountId, year, month)`<br>• `getRecentTransactions(accountId, fromDate, toDate)` | `SpendingTools.java`                                                                                                                                 |
| **5** | **Memory** | **Short-term**: `MessageWindowChatMemory.withMaxMessages(20)` keeps conversation context during analysis<br>**Long-term**: Planned for Part 2 (vector memory) | `AgentConfig.java`                                                                                                                                   |
| **6** | **Actions / Execution** | Agent autonomously decides which tools to call, in what order, and when analysis is complete. Executed via LangChain4j's tool execution loop | Framework-handled (LangChain4j)                                                                                                                      |
| **7** | **Autonomy** | LLM decides:<br>• Which tool(s) to call<br>• How many times<br>• When enough data is gathered<br>• How to structure the response | Enabled by `AiServices.builder()` in `AgentConfig.java`                                                                                              |
| **8** | **Guardrails & Safety** | • Input validation (year/month ranges)<br>• Low temperature (0.2) for consistency<br>• Prompt constraints ("Never mention account IDs")<br>• 400-word response limit<br>• Tool parameter validation | `SpendingInsightController.java` (validation)<br>`SpendingInsightAgent.java` (prompt rules)<br>`AgentConfig.java` (temperature)                      |
| **9** | **Learning & Adaptation** | Currently: Logging for analysis<br>Planned: Feedback loops and vector memory (Part 2) | Future enhancement                                                                                                                                   |
| **10** | **Evaluation & Monitoring** | Comprehensive logging:<br>• Tool calls with timestamps<br>• Parameters and results<br>• Execution flow<br>• Debug-level agent reasoning | `SpendingTools.java` (tool logging)<br>`SpendingInsightService.java` (agent logging)<br>`logback-spring.xml` (configuration)                         |
| **11** | **HITL (Human-In-The-Loop)** | System-level concern: Agent outputs structured insights that can be reviewed before presenting to customers. Supports approval workflows via REST API | HITL is not implemented in code in this repo. The agent simply returns a String insight via the REST API. Architecture supports HITL at system level |

> 🔎 In short: Components **1–7** are concretely represented in this repo (at different depths), while **8–11** are partly or fully *architectural* — described in the article as production patterns that would be layered around this core agent in a real bank.
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
Here's a friendly analysis of your spending for November 2025, compared to October 2025.

### Category Breakdown for November 2025:
1. **Food**: $120.50
   - Merchant: GrabFood
2. **Travel**: $1,980.00
   - Merchants: Scoot Airlines ($580.00), Singapore Airlines ($1,400.00)
3. **Shopping**: $320.00
   - Merchant: Uniqlo
4. **Bills**: $210.00
   - Merchant: SP Services

### Top Spending Categories:
- **Travel**: $1,980.00
- **Shopping**: $320.00
- **Bills**: $210.00
- **Food**: $120.50

### Spending Comparison with October 2025:
In October, your spending was as follows:
- **Food**: $110.00
- **Travel**: $200.00
- **Shopping**: $200.00
- **Bills**: $180.00

### Notable Changes:
- **Total Spending**:
  - November: $2,730.50
  - October: $700.00
  - **Increase**: 289% (a significant spike!)

### Spending Spikes:
- **Travel**: Increased from $200.00 in October to $1,980.00 in November, a spike of 890%.
- This increase is primarily due to the large transaction with Singapore Airlines.

### Large Single Transactions:
- The transaction with Singapore Airlines ($1,400.00) is over 40% of your total spending for November, making it a significant expense.

### Summary:
Your spending in November saw a dramatic increase, particularly in travel, largely due to a significant flight expense. If you have any questions or need further insights, feel free to ask!
```

---

## 📘 About This Project
This project accompanies the Medium article:

**“AI Agents in Banking: Building a Real Autonomous Spending Insight Agent”**

Part 1 → Single-agent architecture  
Part 2 → Multi-agent collaboration (coming next)

