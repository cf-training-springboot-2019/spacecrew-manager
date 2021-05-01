package com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
public class GetSpaceShipResponse {

    private Long id;

    private String name;

    private String status;

    private BigInteger maxOccupancy;

    private String type;

}
