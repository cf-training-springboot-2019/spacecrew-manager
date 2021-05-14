package com.springboot.training.spaceover.spacecrew.manager.service;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;

public interface SpaceShipClient {

    GetSpaceShipResponse findBydId(Long id);

}