package com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound;

import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CreateSpaceCrewMemberRequest {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @PositiveOrZero
    private Long spaceShipId;

    @NotNull
    private SpaceCrewMemberStatus status;

    @NotNull
    private SpaceCrewMemberRole role;

    @NotNull
    @PositiveOrZero
    private BigDecimal salary;

}
