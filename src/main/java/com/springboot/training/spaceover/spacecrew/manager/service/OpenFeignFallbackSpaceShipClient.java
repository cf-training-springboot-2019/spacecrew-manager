package com.springboot.training.spaceover.spacecrew.manager.service;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "open-feign")
public class OpenFeignFallbackSpaceShipClient implements OpenFeignSpaceShipClient{

	@Override
	public GetSpaceShipResponse findBydId(Long id) {
		log.error("Failed to retrieve spaceship with id {}", id);
		return null;
	}
}
