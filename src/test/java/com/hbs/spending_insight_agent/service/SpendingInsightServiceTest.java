package com.hbs.spending_insight_agent.service;

import com.hbs.spending_insight_agent.agent.SpendingInsightAgent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SpendingInsightServiceTest {

    private SpendingInsightAgent agent;
    private SpendingInsightService service;

    @BeforeEach
    void setup() {
        agent = mock(SpendingInsightAgent.class);
        service = new SpendingInsightService(agent);
    }

    @Test
    void shouldReturnInsightWhenAgentSucceeds() {
        // Arrange
        String accountId = "A123";
        int year = 2025;
        int month = 11;

        when(agent.generateInsight(anyString())).thenReturn("Sample Insight");

        // Act
        String result = service.generateInsight(accountId, year, month);

        // Assert
        assertEquals("Sample Insight", result);
        verify(agent, times(1)).generateInsight(anyString());
    }

    @Test
    void shouldWrapExceptionWhenAgentThrowsError() {
        // Arrange
        String accountId = "A123";
        int year = 2025;
        int month = 11;

        when(agent.generateInsight(anyString()))
                .thenThrow(new RuntimeException("Agent failure"));

        // Act + Assert
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.generateInsight(accountId, year, month)
        );

        assertTrue(ex.getMessage().contains("Failed to analyze spending"));
        assertTrue(ex.getMessage().contains("Agent failure"));
        verify(agent, times(1)).generateInsight(anyString());
    }

    @Test
    void shouldBuildPromptUsingAccountIdYearAndMonth() {
        // Arrange
        String accountId = "A123";
        int year = 2025;
        int month = 11;

        when(agent.generateInsight(anyString())).thenReturn("OK");

        ArgumentCaptor<String> promptCaptor = ArgumentCaptor.forClass(String.class);

        // Act
        service.generateInsight(accountId, year, month);

        // Assert – verify query content
        verify(agent).generateInsight(promptCaptor.capture());
        String prompt = promptCaptor.getValue();

        assertNotNull(prompt);
        assertTrue(prompt.contains(accountId), "Prompt should contain accountId");
        assertTrue(prompt.contains(String.valueOf(year)), "Prompt should contain year");
        assertTrue(prompt.contains(String.format("%02d", month))
                        || prompt.contains(String.valueOf(month)),
                "Prompt should contain month (plain or zero-padded)");
    }
}
