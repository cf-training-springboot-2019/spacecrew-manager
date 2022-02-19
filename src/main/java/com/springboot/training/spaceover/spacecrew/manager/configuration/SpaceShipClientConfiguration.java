package com.springboot.training.spaceover.spacecrew.manager.configuration;

import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
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
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(spaceCrewManagerProperties.getSpaceshipManagerBaseUrl()).build();
    }

}