package com.springboot.training.spaceover.spacecrew.manager.service;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIPS;

@RequiredArgsConstructor
public class RestTemplateSpaceShipClient implements SpaceShipClient {

    private final RestTemplate restTemplate;

    private final SpaceCrewManagerProperties spaceCrewManagerProperties;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        return restTemplate
                .getForEntity(String.join(FRONT_SLASH_DELIMITER, spaceCrewManagerProperties.getSpaceshipManagerBaseUrl(), SPACESHIPS, String.valueOf(id)),
                        GetSpaceShipResponse.class).getBody();
    }
}
