package com.hbs.spending_insight_agent.agent;

import com.hbs.spending_insight_agent.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpendingToolsTest {

    @Autowired
    private SpendingTools spendingTools;

    @Test
    void monthlyTotalShouldMatchSeedDataForNovember() {
        BigDecimal total = spendingTools.monthlyTotal("A123", 2025, 11);
        assertThat(total).isEqualByComparingTo("2630.50");
    }

    @Test
    void fetchTransactionsShouldReturnFiveRowsForNovember() {
        List<Transaction> november = spendingTools.fetchTransactions(
                "A123",
                "2025-11-01",
                "2025-11-30"
        );

        assertThat(november).hasSize(5);
    }
}
