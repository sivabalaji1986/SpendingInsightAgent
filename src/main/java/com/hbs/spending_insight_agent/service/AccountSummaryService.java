package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.model.Transaction;
import com.hbs.spending_insight_agent.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountSummaryService {

    private final TransactionRepository transactionRepo;

    public BigDecimal getTotalSpent(String accountId, YearMonth ym) {

        List<Transaction> txns = transactionRepo.findByAccountIdAndDateBetween(
                accountId,
                ym.atDay(1),
                ym.atEndOfMonth()
        );

        return txns.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
