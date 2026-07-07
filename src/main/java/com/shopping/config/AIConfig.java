package com.shopping.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AIConfig {
    private String apiUrl;
    private String apiKey;
    private String secret;
    private String model;
    private Integer timeout = 30000;
    private Integer maxTokens = 2000;
}