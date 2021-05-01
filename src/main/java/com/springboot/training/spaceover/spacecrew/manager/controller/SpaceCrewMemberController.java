package com.springboot.training.spaceover.spacecrew.manager.controller;

import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.CreateSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.request.inbound.UpdateSpaceCrewMemberRequest;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.GetSpaceCrewMemberResponse;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.outbound.UpdateSpaceCrewMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpaceCrewMemberController {


    ResponseEntity<Page<GetSpaceCrewMemberResponse>> getSpaceCrewMembers(Pageable page, String name, String status, List<Long> ids);

    ResponseEntity<GetSpaceCrewMemberResponse> getSpaceCrewMember(Long id);

    ResponseEntity createSpaceCrewMember(CreateSpaceCrewMemberRequest request);

    ResponseEntity<UpdateSpaceCrewMemberResponse>  UpdateSpaceCrewMember(UpdateSpaceCrewMemberRequest request);

    ResponseEntity deleteSpaceCrewMember(Long id);
    
}
