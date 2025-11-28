package com.hbs.spending_insight_agent.repository;

import com.hbs.spending_insight_agent.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void shouldHaveSeededTransactionsForAccountA123() {
        List<Transaction> all = transactionRepository.findAll();
        assertThat(all).isNotEmpty();
        assertThat(all)
                .extracting(Transaction::getAccountId)
                .contains("A123");
    }

    @Test
    void shouldReturnNovemberTransactionsForA123() {
        LocalDate from = LocalDate.of(2025, 11, 1);
        LocalDate to = LocalDate.of(2025, 11, 30);

        List<Transaction> november = transactionRepository
                .findByAccountIdAndDateBetween("A123", from, to);

        assertThat(november).hasSize(4);
        assertThat(november)
                .allMatch(txn -> "A123".equals(txn.getAccountId()));
    }

    @Test
    void shouldReturnEmptyForUnknownAccount() {
        LocalDate from = LocalDate.of(2025, 11, 1);
        LocalDate to = LocalDate.of(2025, 11, 30);

        List<Transaction> november = transactionRepository
                .findByAccountIdAndDateBetween("NO_SUCH", from, to);

        assertThat(november).isEmpty();
    }
}
