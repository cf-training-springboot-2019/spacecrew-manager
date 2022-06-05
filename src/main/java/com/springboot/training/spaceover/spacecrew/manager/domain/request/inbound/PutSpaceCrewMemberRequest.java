package com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutSpaceCrewMemberRequest {

    @JsonIgnore
    private Long id;

    @NotEmpty
    private String name;

    @PositiveOrZero
    private Long spaceShipId;

    @NotNull
    private SpaceCrewMemberStatus status;

    @NotNull
    private SpaceCrewMemberRole role;

    @PositiveOrZero
    private BigDecimal salary;

}
