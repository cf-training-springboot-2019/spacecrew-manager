package com.springboot.training.spaceover.spacecrew.manager.service;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIPS;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;


@RequiredArgsConstructor
public class WebClientSpaceShipClient implements SpaceShipClient {

    private final WebClient webClient;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        return webClient.get()
            .uri(String.join(FRONT_SLASH_DELIMITER, SPACESHIPS, String.valueOf(id)))
            .retrieve()
            .bodyToMono(GetSpaceShipResponse.class)
            .block();
    }
}
