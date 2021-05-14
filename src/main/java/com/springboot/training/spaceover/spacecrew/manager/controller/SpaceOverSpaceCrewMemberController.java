package com.springboot.training.spaceover.spacecrew.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.CreateSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.PutSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.GetSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.PatchSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.PutSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import com.springboot.training.spaceover.spacecrew.manager.service.SpaceCrewMemberService;
import com.springboot.training.spaceover.spacecrew.manager.utils.assemblers.PaginationModelAssembler;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SPACE_CREW_MEMBERS_URI)
public class SpaceOverSpaceCrewMemberController extends SpaceOverGenericController implements SpaceCrewMemberController {

    private final SpaceCrewMemberService spaceCrewMemberService;

    private final ModelMapper modelMapper;

    private final PagedResourcesAssembler<SpaceCrewMember> pagedModelAssembler;

    private final PaginationModelAssembler modelAssembler;

    @Override
    @GetMapping
    public ResponseEntity<PagedModel<GetSpaceCrewMemberResponse>> getSpaceCrewMembers(Pageable pageable,
                                                                                      @RequestParam(name = NAME_FIELD, required = false) String name,
                                                                                      @RequestParam(name = STATUS_FIELD, required = false) String status,
                                                                                      @RequestParam(name = ROLE_FIELD, required = false) String role,
                                                                                      @RequestParam(name = SPACESHIP_ID_FIELD, required = false) Long spaceShipId) {
        SpaceCrewMember spaceMissionSample = SpaceCrewMember.builder()
                .name(name)
                .status(SpaceCrewMemberStatus.fromName(status))
                .role(SpaceCrewMemberRole.fromName(role))
                .spaceShipId(spaceShipId)
                .build();
        Page<SpaceCrewMember> spaceMissionPage = spaceCrewMemberService.findAll(spaceMissionSample, pageable);
        PagedModel<GetSpaceCrewMemberResponse> response = pagedModelAssembler.toModel(spaceMissionPage, modelAssembler);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping(ID_URI)
    public ResponseEntity<GetSpaceCrewMemberResponse> getSpaceCrewMember(@PathVariable("id") Long id) {
        GetSpaceCrewMemberResponse response = modelMapper.map(spaceCrewMemberService.findBydId(id), GetSpaceCrewMemberResponse.class);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping
    public ResponseEntity createSpaceCrewMember(@RequestBody CreateSpaceCrewMemberRequest request) {
        SpaceCrewMember spaceMission = spaceCrewMemberService.save(modelMapper.map(request, SpaceCrewMember.class));
        return ResponseEntity.created(getResourceUri(spaceMission.getId())).build();
    }

    @Override
    @PatchMapping(ID_URI)
    public ResponseEntity<PatchSpaceCrewMemberResponse> patchSpaceMission(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
        SpaceCrewMember entity = spaceCrewMemberService.findBydId(id);
        entity = spaceCrewMemberService.update(applyPatch(patch, entity));
        return ResponseEntity.ok(modelMapper.map(entity, PatchSpaceCrewMemberResponse.class));
    }

    @Override
    @PutMapping(ID_URI)
    public ResponseEntity<PutSpaceCrewMemberResponse> putSpaceMission(@PathVariable("id") Long id, @RequestBody PutSpaceCrewMemberRequest request) {
        request.setId(id);
        SpaceCrewMember entity = spaceCrewMemberService.update(modelMapper.map(request, SpaceCrewMember.class));
        return ResponseEntity.ok(modelMapper.map(entity, PutSpaceCrewMemberResponse.class));
    }

    @Override
    @DeleteMapping(ID_URI)
    public ResponseEntity deleteSpaceCrewMember(@PathVariable("id") Long id) {
        spaceCrewMemberService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
