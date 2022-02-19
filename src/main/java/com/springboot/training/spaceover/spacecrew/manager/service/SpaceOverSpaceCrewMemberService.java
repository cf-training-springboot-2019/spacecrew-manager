package com.springboot.training.spaceover.spacecrew.manager.service;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBER;

import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import com.springboot.training.spaceover.spacecrew.manager.repository.SpaceCrewMemberRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceOverSpaceCrewMemberService implements SpaceCrewMemberService {

    private final SpaceCrewMemberRepository spaceCrewMemberRepository;

    private final SpaceShipClient spaceShipClient;

    @Override
    //LT3.3-Include request pagination
    //LT3.4-Include example matching
    public Page<SpaceCrewMember> findAll(SpaceCrewMember entitySample, Pageable pageRequest) {
        return null;
    }

    @Override
    public List<SpaceCrewMember> findAll() {
        return spaceCrewMemberRepository.findAll();
    }

    @Override
    public SpaceCrewMember findBydId(Long id) {
        return spaceCrewMemberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_NOT_FOUND_MSG, SPACE_CREW_MEMBER, id)));
    }

    @Override
    //LT3.2-Modify save method to be transactional
    public SpaceCrewMember save(SpaceCrewMember entity) {
        entity.setStatus(SpaceCrewMemberStatus.ACTIVE);
        entity = spaceCrewMemberRepository.save(entity);
        spaceShipClient.findBydId(entity.getSpaceShipId());
        return entity;
    }

    @Override
    public SpaceCrewMember update(SpaceCrewMember entity) {
        spaceShipClient.findBydId(entity.getSpaceShipId());
        return spaceCrewMemberRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        spaceCrewMemberRepository.deleteById(id);
    }
}
