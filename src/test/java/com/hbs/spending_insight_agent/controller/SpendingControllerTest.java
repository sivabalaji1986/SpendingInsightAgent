package com.hbs.spending_insight_agent.controller;

import com.hbs.spending_insight_agent.agent.SpendingInsightAgent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpendingController.class)
class SpendingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpendingInsightAgent spendingInsightAgent;

    @Test
    void shouldReturnInsightResponse() throws Exception {

        when(spendingInsightAgent.analyse("Test query"))
                .thenReturn("Sample Insight");

        mockMvc.perform(post("/api/spend/analyse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "query": "Test query"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("Sample Insight"));
    }
}
