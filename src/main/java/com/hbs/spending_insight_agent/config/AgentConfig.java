package com.hbs.spending_insight_agent.config;

import com.hbs.spending_insight_agent.agent.SpendingInsightAgent;
import com.hbs.spending_insight_agent.agent.SpendingTools;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public ChatLanguageModel model() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o-mini")
                .temperature(0.2)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    public SpendingInsightAgent spendingAgent(ChatLanguageModel model,
                                              SpendingTools tools) {
        return AiServices.builder(SpendingInsightAgent.class)
                .chatLanguageModel(model)
                .tools(tools)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .build();
    }
}

