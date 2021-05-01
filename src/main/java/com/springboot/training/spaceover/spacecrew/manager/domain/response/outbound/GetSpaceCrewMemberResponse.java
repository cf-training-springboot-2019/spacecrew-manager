package com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound;

import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSpaceCrewMemberResponse {

    private Long id;

    private String name;

    private Long spaceShipId;

    private SpaceCrewMemberStatus status;

    private SpaceCrewMemberRole role;

    private BigDecimal salary;

}
