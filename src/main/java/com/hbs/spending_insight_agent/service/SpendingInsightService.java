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
                Explain the spending for account %s in %d-%02d.
                Compare it with the previous month.
                Highlight:
                - Top spending categories
                - Any spending spikes (more than 20%% increase)
                - Any single transaction above 40%% of the monthly total
                Keep the explanation clear, concise, and friendly.
                """, accountId, year, month);

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
