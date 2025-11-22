package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void shouldFetchAllNovemberTransactionsForA123() {
        // Given
        String accountId = "A123";
        LocalDate from = LocalDate.of(2025, 11, 1);
        LocalDate to   = LocalDate.of(2025, 11, 30);

        // When
        List<Transaction> txns =
                transactionService.getTransactions(accountId, from, to);

        // Then
        // From your data.sql, there are 5 November rows for A123
        assertThat(txns).hasSize(5);

        // All belong to A123 and are within the date range
        assertThat(txns)
                .allSatisfy(t -> {
                    assertThat(t.getAccountId()).isEqualTo("A123");
                    assertThat(t.getDate()).isBetween(from, to);
                });

        // Optionally assert total amount for extra safety:
        BigDecimal total = txns.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 120.50 + 580.00 + 320.00 + 1400.00 + 210.00 = 2630.50
        assertThat(total).isEqualByComparingTo("2630.50");
    }

    @Test
    void shouldReturnEmptyListWhenNoTransactionsInRange() {
        // Given: a month with no data for A123 (e.g., Jan 2025)
        String accountId = "A123";
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to   = LocalDate.of(2025, 1, 31);

        // When
        List<Transaction> txns =
                transactionService.getTransactions(accountId, from, to);

        // Then
        assertThat(txns).isEmpty();
    }
}
