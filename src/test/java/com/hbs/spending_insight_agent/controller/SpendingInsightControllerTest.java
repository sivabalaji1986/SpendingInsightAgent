package com.hbs.spending_insight_agent.controller;

import com.hbs.spending_insight_agent.service.SpendingInsightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpendingInsightController.class)
class SpendingInsightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpendingInsightService spendingInsightService;

    @Test
    void shouldReturnInsightResponse() throws Exception {

        when(spendingInsightService.generateInsight(anyString(), anyInt(), anyInt()))
                .thenReturn("Sample Insight");

        mockMvc.perform(get("/api/spending/insights")
                        .param("accountId", "A123")
                        .param("year", "2025")
                        .param("month", "11")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Sample Insight"));
    }

    @Test
    void shouldReturnBadRequestForYearLessThan2020() throws Exception {
        mockMvc.perform(get("/api/spending/insights")
                        .param("accountId", "A123")
                        .param("year", "2019")   // < 2020
                        .param("month", "11")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid year"));

        // Service should not be called when validation fails
        verifyNoInteractions(spendingInsightService);
    }

    @Test
    void shouldReturnBadRequestForYearGreaterThanCurrentYear() throws Exception {
        int nextYear = LocalDate.now().getYear() + 1;

        mockMvc.perform(get("/api/spending/insights")
                        .param("accountId", "A123")
                        .param("year", String.valueOf(nextYear)) // > current year
                        .param("month", "11")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid year"));

        verifyNoInteractions(spendingInsightService);
    }

    @Test
    void shouldReturnBadRequestForMonthLessThanOne() throws Exception {
        mockMvc.perform(get("/api/spending/insights")
                        .param("accountId", "A123")
                        .param("year", "2025")
                        .param("month", "0")   // < 1
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid month"));

        verifyNoInteractions(spendingInsightService);
    }

    @Test
    void shouldReturnBadRequestForMonthGreaterThanTwelve() throws Exception {
        mockMvc.perform(get("/api/spending/insights")
                        .param("accountId", "A123")
                        .param("year", "2025")
                        .param("month", "13")   // > 12
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid month"));

        verifyNoInteractions(spendingInsightService);
    }

}
