package com.springboot.training.spaceover.spacecrew.manager.domain.model;

import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//LT3.1-Include domain model auditing
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

    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

}
