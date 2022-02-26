package com.springboot.training.spaceover.spacecrew.manager.service;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIPS;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RestTemplateSpaceShipClient implements SpaceShipClient {

    private final RestTemplate restTemplate;

    private final SpaceCrewManagerProperties spaceCrewManagerProperties;

    @Override
    public GetSpaceShipResponse findBydId(Long id) {
        String url = String.join(FRONT_SLASH_DELIMITER, spaceCrewManagerProperties.getSpaceshipManagerBaseUrl(),
            SPACESHIPS,
            String.valueOf(id));
        return restTemplate.getForEntity(url, GetSpaceShipResponse.class).getBody();    }
}
