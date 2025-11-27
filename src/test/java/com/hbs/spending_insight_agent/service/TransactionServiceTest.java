package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.model.Transaction;
import com.hbs.spending_insight_agent.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        // Make the test independent of data.sql
        transactionRepository.deleteAll();

        String accountId = "A123";

        // Insert the same 5 November transactions you expect
        transactionRepository.save(Transaction.builder()
                .accountId(accountId)
                .amount(new BigDecimal("120.50"))
                .category("Food")
                .merchant("GrabFood")
                .date(LocalDate.of(2025, 11, 1))
                .build());

        transactionRepository.save(Transaction.builder()
                .accountId(accountId)
                .amount(new BigDecimal("580.00"))
                .category("Travel")
                .merchant("Scoot Airlines")
                .date(LocalDate.of(2025, 11, 3))
                .build());

        transactionRepository.save(Transaction.builder()
                .accountId(accountId)
                .amount(new BigDecimal("320.00"))
                .category("Shopping")
                .merchant("Uniqlo")
                .date(LocalDate.of(2025, 11, 5))
                .build());

        transactionRepository.save(Transaction.builder()
                .accountId(accountId)
                .amount(new BigDecimal("1400.00"))
                .category("Travel")
                .merchant("Singapore Airlines")
                .date(LocalDate.of(2025, 11, 18))
                .build());

        transactionRepository.save(Transaction.builder()
                .accountId(accountId)
                .amount(new BigDecimal("210.00"))
                .category("Bills")
                .merchant("SP Services")
                .date(LocalDate.of(2025, 11, 20))
                .build());
    }

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
        assertThat(txns).hasSize(5);

        assertThat(txns)
                .allSatisfy(t -> {
                    assertThat(t.getAccountId()).isEqualTo("A123");
                    assertThat(t.getDate()).isBetween(from, to);
                });

        BigDecimal total = txns.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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

    @Test
    void shouldThrowExceptionWhenDateRangeExceedsMaxDays() {
        // Given: from/to more than MAX_DAYS apart (e.g. > 90 days)
        String accountId = "A123";
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to   = LocalDate.of(2025, 7, 1); // ~182 days > 90

        // When / Then
        assertThatThrownBy(() ->
                transactionService.getTransactions(accountId, from, to)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Date range too large");
    }

    @Test
    void shouldLimitResultsToMaxResultsWhenTooManyTransactions() {
        // Clean slate for this test
        transactionRepository.deleteAll();

        String accountId = "A123";
        LocalDate from = LocalDate.of(2025, 11, 1);
        LocalDate to   = LocalDate.of(2025, 11, 30);

        // Assume MAX_RESULTS = 500 in your service.
        // Insert > MAX_RESULTS transactions (e.g. 505)
        int totalRows = 505;
        for (int i = 1; i <= totalRows; i++) {
            transactionRepository.save(Transaction.builder()
                    .accountId(accountId)
                    .amount(new BigDecimal("10.00"))
                    .category("Test")
                    .merchant("Merchant " + i)
                    .date(from.plusDays(i % 10)) // keep dates within range
                    .build());
        }

        // When
        List<Transaction> txns =
                transactionService.getTransactions(accountId, from, to);

        // Then: branch allTransactions.size() > MAX_RESULTS is hit
        // and we get only the first MAX_RESULTS
        assertThat(txns).hasSize(500);  // adjust if your MAX_RESULTS != 500
    }

}
