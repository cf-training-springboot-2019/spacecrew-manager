package com.springboot.training.spaceover.spacecrew.manager.service;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("spaceship-manager")
@ConditionalOnProperty(name = "spaceship-manager.client.provider", havingValue = "open-feign")
public interface OpenFeignSpaceShipClient extends SpaceShipClient {

    @GetMapping("spaceships/{id}")
    GetSpaceShipResponse findBydId(@PathVariable("id") Long id);


}