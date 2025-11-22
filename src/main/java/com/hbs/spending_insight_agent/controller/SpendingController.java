package com.hbs.spending_insight_agent.controller;

import com.hbs.spending_insight_agent.agent.SpendingInsightAgent;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spend")
public class SpendingController {

    private final SpendingInsightAgent agent;

    public SpendingController(SpendingInsightAgent agent) {
        this.agent = agent;
    }

    @PostMapping("/analyse")
    public String analyse(@RequestBody AnalyseRequest req) {
        return agent.analyse(req.getQuery());
    }

    @Data
    public static class AnalyseRequest {
        private String query;
    }
}

