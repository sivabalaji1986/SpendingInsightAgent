package com.hbs.spending_insight_agent.agent;

import com.hbs.spending_insight_agent.model.Transaction;
import com.hbs.spending_insight_agent.service.AccountSummaryService;
import com.hbs.spending_insight_agent.service.TransactionService;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.P;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Slf4j
@Component
public class SpendingTools {

    private final AccountSummaryService summaryService;
    private final TransactionService txnService;

    public SpendingTools(AccountSummaryService summaryService,
                         TransactionService txnService) {
        this.summaryService = summaryService;
        this.txnService = txnService;
    }

    @Tool("Get the total amount spent for a given account and month")
    public BigDecimal getAccountSummary(
            @P("Account ID") String accountId,
            @P("Year") int year,
            @P("Month (1-12)") int month) {

        log.info("Tool Call: getAccountSummary(accountId={}, year={}, month={})",
                accountId, year, month);

        BigDecimal total = summaryService.getTotalSpent(accountId, YearMonth.of(year, month));

        log.info("Tool getAccountSummary Result: total={}", total);
        return total;
    }

    @Tool("Fetch transactions for an account within a date range (inclusive)")
    public List<Transaction> getRecentTransactions(
            @P("Account ID") String accountId,
            @P("From date (YYYY-MM-DD)") String fromDate,
            @P("To date (YYYY-MM-DD)") String toDate) {

        log.info("Tool Call: getRecentTransactions(accountId={}, from={}, to={})",
                accountId, fromDate, toDate);

        List<Transaction> transactions = txnService.getTransactions(
                accountId,
                LocalDate.parse(fromDate),
                LocalDate.parse(toDate)
        );

        log.info("Tool getRecentTransactions Result: {} transactions returned", transactions.size());
        return transactions;
    }
}
