package com.springboot.training.spaceover.spacecrew.manager.domain.model;

import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceCrewMember extends Auditable<String> {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull
        @PositiveOrZero
        private Long spaceShipId;

        @NotNull
        @NotEmpty
        private String name;

        @NotNull
        private SpaceCrewMemberStatus status;

        @NotNull
        private SpaceCrewMemberRole role;

        @NotNull
        @PositiveOrZero
        private BigDecimal salary;

}
