package com.hbs.spending_insight_agent.agent;

import dev.langchain4j.service.*;

public interface SpendingInsightAgent {

    @SystemMessage("""
        You are a Spending Insight Agent for a retail bank.
        Your job is to analyse spending patterns and provide 
        concise, friendly insights.
        Use the available tools when needed.
        """)
    String analyse(@UserMessage String input);
}

