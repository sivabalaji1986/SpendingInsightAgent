package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.model.Transaction;
import com.hbs.spending_insight_agent.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepo;

    public List<Transaction> getTransactions(String accountId, LocalDate from, LocalDate to) {
        return transactionRepo.findByAccountIdAndDateBetween(accountId, from, to);
    }
}

