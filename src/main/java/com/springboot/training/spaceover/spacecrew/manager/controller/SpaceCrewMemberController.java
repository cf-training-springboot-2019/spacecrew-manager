package com.springboot.training.spaceover.spacecrew.manager.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.CreateSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.PutSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.GetSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.PatchSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.PutSpaceCrewMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface SpaceCrewMemberController {

    ResponseEntity<Page<GetSpaceCrewMemberResponse>> getSpaceCrewMembers(Pageable pageable, String name, String status, String role, Long spaceShipId);

    ResponseEntity<GetSpaceCrewMemberResponse> getSpaceCrewMember(Long id);

    ResponseEntity createSpaceCrewMember(CreateSpaceCrewMemberRequest request);

    ResponseEntity<PatchSpaceCrewMemberResponse> patchSpaceMission(Long id, JsonPatch patch);

    ResponseEntity<PutSpaceCrewMemberResponse> putSpaceMission(Long id, PutSpaceCrewMemberRequest request);

    ResponseEntity deleteSpaceCrewMember(Long id);

}
