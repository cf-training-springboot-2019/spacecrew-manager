package com.springboot.training.spaceover.spacecrew.manager.configuration;

import com.springboot.training.spaceover.spacecrew.manager.service.RestTemplateSpaceShipClient;
import com.springboot.training.spaceover.spacecrew.manager.service.SpaceShipClient;
import com.springboot.training.spaceover.spacecrew.manager.service.WebClientSpaceShipClient;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpaceShipClientConfiguration {

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public SpaceShipClient restTemplateSpaceShipClient(final RestTemplate restTemplate, final SpaceCrewManagerProperties properties) {
        return new RestTemplateSpaceShipClient(restTemplate, properties);
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "web-client")
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "web-client")
    public SpaceShipClient webClientSpaceShipClient(final WebClient.Builder webClientBuilder, final SpaceCrewManagerProperties properties) {
        return new WebClientSpaceShipClient(webClientBuilder, properties);
    }

}
