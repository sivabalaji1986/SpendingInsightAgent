package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.agent.SpendingInsightAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpendingInsightService {

    private final SpendingInsightAgent agent;

    public SpendingInsightService(SpendingInsightAgent agent) {
        this.agent = agent;
    }

    public String generateInsight(String accountId, int year, int month) {
        log.info("Agent: Received request for accountId={}, year={}, month={}",
                accountId, year, month);

        String query = String.format("""
                Analyze spending for account %s for %d-%02d.
                Compare with the previous month.
                
                Provide:
                - Category breakdown showing where money was spent
                - Top spending categories with amounts
                - Any spending spikes (>20%% increase)
                - Any large single transactions (>40%% of monthly total)
                
                IMPORTANT: Never mention the account ID (%s) in your response.
                Refer to it as "your account" instead.
                
                Keep the explanation clear, concise, and friendly.
                """, accountId, year, month, accountId);

        log.debug("Agent user query: {}", query);

        try {
            log.info("Agent: Planning - analyzing spending patterns...");
            String result = agent.analyse(query);
            log.info("Agent: Insight generated successfully");
            log.debug("Agent result length: {} characters", result.length());
            return result;
        } catch (Exception e) {
            log.error("Agent: Failed to generate insight", e);
            throw new RuntimeException("Failed to analyze spending: " + e.getMessage(), e);
        }
    }
}
