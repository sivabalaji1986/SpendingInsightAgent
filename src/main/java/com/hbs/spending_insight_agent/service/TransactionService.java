package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.model.Transaction;
import com.hbs.spending_insight_agent.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepo;

    private static final int MAX_DAYS = 90;

    private static final int MAX_RESULTS = 500;

    public List<Transaction> getTransactions(String accountId, LocalDate from, LocalDate to) {
        // Guardrail - 1: Date range validation
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(from, to);
        if (daysBetween > MAX_DAYS) {
            throw new IllegalArgumentException("Date range too large. Max " + MAX_DAYS + " days.");
        }

        // Guardrail - 2: Result Limit
        List<Transaction> allTransactions = transactionRepo.findByAccountIdAndDateBetween(accountId, from, to);
        if (allTransactions.size() > MAX_RESULTS) {
            log.warn("Transaction count {} exceeds limit, truncating",
                    allTransactions.size());
            return allTransactions.subList(0, MAX_RESULTS);
        }

        return allTransactions;
    }
}

