package com.learnguide.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class OpenAIConfig {
    
    @Value("${openai.api.key}")
    private String openaiApiKey;
    
    @Value("${openai.timeout:60}")
    private Integer timeoutSeconds;
    
    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(openaiApiKey, Duration.ofSeconds(timeoutSeconds));
    }
} 