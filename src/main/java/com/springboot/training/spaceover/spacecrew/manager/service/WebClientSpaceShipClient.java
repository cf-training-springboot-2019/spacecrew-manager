package com.springboot.training.spaceover.spacecrew.manager.service;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIPS;


@RequiredArgsConstructor
public class WebClientSpaceShipClient implements SpaceShipClient {

    private final WebClient.Builder webClientBuilder;

    private final SpaceCrewManagerProperties spaceCrewManagerProperties;


    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        WebClient webClient = webClientBuilder.baseUrl(spaceCrewManagerProperties.getSpaceshipManagerBaseUrl())
                .build();

        return webClient.get()
                .uri(SPACESHIPS)
                .retrieve()
                .bodyToMono(GetSpaceShipResponse.class)
                .block();
    }
}
