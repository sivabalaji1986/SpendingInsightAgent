package com.hbs.spending_insight_agent.controller;

import com.hbs.spending_insight_agent.service.SpendingInsightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/spending")
@Slf4j
public class SpendingInsightController {

    private final SpendingInsightService insightService;

    public SpendingInsightController(SpendingInsightService insightService) {
        this.insightService = insightService;
    }

    @GetMapping("/insights")
    public ResponseEntity<String> insights(@RequestParam String accountId,
                                           @RequestParam int year,
                                           @RequestParam int month) {

        log.info("Request: /api/spending/insights accountId={}, year={}, month={}",
                accountId, year, month);

        // Minimal validation (you can expand this in repo / prod)
        if (year < 2020 || year > LocalDate.now().getYear()) {
            return ResponseEntity.badRequest().body("Invalid year");
        }
        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().body("Invalid month");
        }

        String insight = insightService.generateInsight(accountId, year, month);
        return ResponseEntity.ok(insight);
    }
}
