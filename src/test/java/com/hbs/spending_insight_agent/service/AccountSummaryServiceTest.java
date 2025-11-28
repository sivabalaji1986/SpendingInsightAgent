package com.hbs.spending_insight_agent.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountSummaryServiceTest {

    @Autowired
    private AccountSummaryService accountSummaryService;

    @Test
    void shouldCalculateTotalSpentForNovember2025ForA123() {
        // Given
        YearMonth ym = YearMonth.of(2025, 11);

        // When
        BigDecimal total = accountSummaryService.getTotalSpent("A123", ym);

        // Then
        // From your data.sql:
        // 121 + 320.00 + 1400.00 + 210.00 = 2630.50
        assertThat(total).isEqualByComparingTo("2051");
    }

    @Test
    void shouldReturnZeroWhenNoTransactionsInThatMonth() {
        // Given: same account, but a month with no rows in data.sql
        YearMonth ym = YearMonth.of(2025, 1);

        // When
        BigDecimal total = accountSummaryService.getTotalSpent("A123", ym);

        // Then
        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }
}
