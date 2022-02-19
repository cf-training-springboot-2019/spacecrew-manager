package com.springboot.training.spaceover.spacecrew.manager.controller;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.GET_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.GET_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.ID_URI;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.NAME_FIELD;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.ROLE_FIELD;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACESHIP_ID_FIELD;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBERS;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBERS_URI;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBER_API_DESCRIPTION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.STATUS_FIELD;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.CreateSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.PutSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.GetSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.PatchSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.PutSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.service.SpaceCrewMemberService;
import com.springboot.training.spaceover.spacecrew.manager.utils.annotatations.ServiceOperation;
import com.springboot.training.spaceover.spacecrew.manager.utils.assemblers.PaginationModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(SPACE_CREW_MEMBERS_URI)
@Tag(name = SPACE_CREW_MEMBERS, description = SPACE_CREW_MEMBER_API_DESCRIPTION)
public class SpaceOverSpaceCrewMemberController extends SpaceOverGenericController implements SpaceCrewMemberController {

    private final SpaceCrewMemberService spaceCrewMemberService;

    private final ModelMapper modelMapper;

    private final PagedResourcesAssembler<SpaceCrewMember> pagedModelAssembler;

    private final PaginationModelAssembler modelAssembler;

    @Override
    @GetMapping
    @PageableAsQueryParam
    @ServiceOperation(GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION)
    @Operation(summary = GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION, description = GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<Page<GetSpaceCrewMemberResponse>> getSpaceCrewMembers(@Parameter(hidden = true) Pageable pageable,
                                                                                      @RequestParam(name = NAME_FIELD, required = false) String name,
                                                                                      @RequestParam(name = STATUS_FIELD, required = false) String status,
                                                                                      @RequestParam(name = ROLE_FIELD, required = false) String role,
                                                                                      @RequestParam(name = SPACESHIP_ID_FIELD, required = false) Long spaceShipId) {
        //LT3.3-Include request pagination
        //LT3.4-Include example matching
        Page<SpaceCrewMember> spaceMissionPage = null;
        Page<GetSpaceCrewMemberResponse> response = null;
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(ID_URI)
    @ServiceOperation(GET_SPACE_CREW_MEMBER_SERVICE_OPERATION)
    @Operation(summary = GET_SPACE_CREW_MEMBER_SERVICE_OPERATION, description = GET_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<GetSpaceCrewMemberResponse> getSpaceCrewMember(@PathVariable("id") Long id) {
        GetSpaceCrewMemberResponse response = modelMapper.map(spaceCrewMemberService.findBydId(id), GetSpaceCrewMemberResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ServiceOperation(CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION)
    @Operation(summary = CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION, description = CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity createSpaceCrewMember(@RequestBody @Valid CreateSpaceCrewMemberRequest request) {
        SpaceCrewMember spaceCrewMember = spaceCrewMemberService.save(modelMapper.map(request, SpaceCrewMember.class));
        return ResponseEntity.created(getResourceUri(spaceCrewMember.getId())).build();
    }

    @Override
    @PatchMapping(ID_URI)
    @ServiceOperation(PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION)
    @Operation(summary = PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION, description = PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PatchSpaceCrewMemberResponse> patchSpaceMission(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        SpaceCrewMember entity = spaceCrewMemberService.findBydId(id);
        entity = spaceCrewMemberService.update(applyPatch(patch, entity));
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceCrewMemberResponse.class));
    }

    @Override
    @PutMapping(ID_URI)
    @ServiceOperation(PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION)
    @Operation(summary = PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION, description = PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity<PutSpaceCrewMemberResponse> putSpaceMission(@PathVariable("id") Long id, @RequestBody @Valid PutSpaceCrewMemberRequest request) {
        request.setId(id);
        SpaceCrewMember entity = spaceCrewMemberService.update(modelMapper.map(request, SpaceCrewMember.class));
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceCrewMemberResponse.class));
    }

    @Override
    @DeleteMapping(ID_URI)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ServiceOperation(DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION)
    @Operation(summary = DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION, description = DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION_DESCRIPTION)
    public ResponseEntity deleteSpaceCrewMember(@PathVariable("id") Long id) {
        spaceCrewMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
