package com.springboot.training.spaceover.spacecrew.manager.configuration;

import com.springboot.training.spaceover.spacecrew.manager.service.RestTemplateSpaceShipClient;
import com.springboot.training.spaceover.spacecrew.manager.service.SpaceShipClient;
import com.springboot.training.spaceover.spacecrew.manager.service.WebClientSpaceShipClient;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class SpaceShipClientConfiguration {

    private final SpaceCrewManagerProperties spaceCrewManagerProperties;

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "rest-template", matchIfMissing = true)
    public SpaceShipClient restTemplateSpaceShipClient(final RestTemplate restTemplate, final SpaceCrewManagerProperties properties) {
        return new RestTemplateSpaceShipClient(restTemplate, spaceCrewManagerProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "web-client")
    public SpaceShipClient webClientSpaceShipClient(final WebClient webClient) {
        return new WebClientSpaceShipClient(webClient);
    }

    @Bean
    @ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "web-client")
    public WebClient webClient() {
        return WebClient.builder().baseUrl(spaceCrewManagerProperties.getSpaceshipManagerBaseUrl()).build();
    }

}