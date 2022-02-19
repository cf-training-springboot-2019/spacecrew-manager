package com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound;

import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
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
public class CreateSpaceCrewMemberRequest {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @PositiveOrZero
    private Long spaceShipId;

    @NotNull
    private SpaceCrewMemberRole role;

    @NotNull
    @PositiveOrZero
    private BigDecimal salary;

}
