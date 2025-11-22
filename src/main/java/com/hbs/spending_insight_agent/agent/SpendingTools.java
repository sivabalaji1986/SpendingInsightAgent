package com.hbs.spending_insight_agent.agent;

import com.hbs.spending_insight_agent.model.Transaction;
import com.hbs.spending_insight_agent.service.AccountSummaryService;
import com.hbs.spending_insight_agent.service.TransactionService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
public class SpendingTools {

    private final AccountSummaryService summaryService;
    private final TransactionService txnService;

    public SpendingTools(AccountSummaryService summaryService, TransactionService txnService) {
        this.summaryService = summaryService;
        this.txnService = txnService;
    }

    @Tool("Get the total amount spent for a given account and month")
    public BigDecimal monthlyTotal(String accountId, int year, int month) {
        return summaryService.getTotalSpent(accountId, YearMonth.of(year, month));
    }

    @Tool("Fetch transactions for an account within a date range")
    public List<Transaction> fetchTransactions(String accountId,
                                               String fromDate,
                                               String toDate) {
        return txnService.getTransactions(
                accountId,
                LocalDate.parse(fromDate),
                LocalDate.parse(toDate)
        );
    }
}
