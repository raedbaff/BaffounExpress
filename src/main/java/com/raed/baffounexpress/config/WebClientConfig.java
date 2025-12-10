package com.raed.baffounexpress.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${mailgun.api.key}")
    private String apiKey;
    @Value("${mailgun.domain.key}")
    private String domain;

    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        String baseUrl = "https://api.eu.mailgun.net/v3/" + domain;
        return builder.baseUrl(baseUrl).defaultHeaders(header -> {
            String basicAuth = "Basic " + Base64.getEncoder().encodeToString(("api:" + apiKey).getBytes());
            header.add("Authorization", basicAuth);
        }).build();

    }

}
