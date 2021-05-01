package com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSpaceCrewMemberRequest {

    @JsonIgnore
    private Long id;

    private String name;

    private Long spaceShipId;

    private SpaceCrewMemberStatus status;

    private SpaceCrewMemberRole role;

    private BigDecimal salary;

}
