package com.springboot.training.spaceover.spacecrew.manager.utils.assemblers;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.FRONT_SLASH_DELIMITER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIP;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIPS;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.springboot.training.spaceover.spacecrew.manager.controller.SpaceOverSpaceCrewMemberController;
import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.GetSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaginationModelAssembler implements RepresentationModelAssembler<SpaceCrewMember, GetSpaceCrewMemberResponse> {

    private final ModelMapper modelMapper;

    private final SpaceCrewManagerProperties spaceCrewManagerProperties;


    @Override
    public GetSpaceCrewMemberResponse toModel(SpaceCrewMember entity) {
        GetSpaceCrewMemberResponse getSpaceMissionResponse = modelMapper.map(entity, GetSpaceCrewMemberResponse.class);
        getSpaceMissionResponse.add(linkTo(methodOn(SpaceOverSpaceCrewMemberController.class).getSpaceCrewMember(entity.getId()))
                .withSelfRel());
        String spaceShipUrl = String.join(FRONT_SLASH_DELIMITER, spaceCrewManagerProperties.getSpaceshipManagerBaseUrl(),
                SPACESHIPS,
                String.valueOf(entity.getSpaceShipId()));
        getSpaceMissionResponse.add(Link.of(spaceShipUrl, SPACESHIP));
        return getSpaceMissionResponse;
    }

    @Override
    public CollectionModel<GetSpaceCrewMemberResponse> toCollectionModel(Iterable<? extends SpaceCrewMember> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}