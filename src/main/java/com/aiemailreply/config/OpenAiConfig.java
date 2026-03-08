package com.aiemailreply.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * CONFIGURATION CLASS — Sets up shared beans used across the application.
 *
 * @Configuration → tells Spring this class provides beans
 * @Bean          → Spring manages the lifecycle of the returned object
 * @Value         → reads values from application.properties
 *
 * RestTemplate is used to make HTTP calls to the OpenAI API.
 */
@Configuration
public class OpenAiConfig {

    // These values are read from application.properties
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.max-tokens}")
    private int maxTokens;

    /**
     * RestTemplate is Spring's HTTP client.
     * We configure a 10-second connection and read timeout.
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);  // 10 seconds
        factory.setReadTimeout(30000);     // 30 seconds (AI can be slow)
        return new RestTemplate(factory);
    }

    // Getters used by the service layer
    public String getApiKey() { return apiKey; }
    public String getApiUrl() { return apiUrl; }
    public String getModel() { return model; }
    public int getMaxTokens() { return maxTokens; }
}
