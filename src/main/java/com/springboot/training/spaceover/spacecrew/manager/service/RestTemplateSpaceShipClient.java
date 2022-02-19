package com.springboot.training.spaceover.spacecrew.manager.service;

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
        return null; //LT1.1-Implement SpaceShipClient using RestTemplate
    }
}
