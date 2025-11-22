package com.hbs.spending_insight_agent.repository;

import com.hbs.spending_insight_agent.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByAccountIdAndDateBetween(String accountId, LocalDate from, LocalDate to);
}
