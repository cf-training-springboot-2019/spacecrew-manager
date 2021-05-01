package com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound;

import lombok.*;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSpaceShipResponse {

    private Long id;

    private String name;

    private String status;

    private BigInteger maxOccupancy;

    private String type;

}
