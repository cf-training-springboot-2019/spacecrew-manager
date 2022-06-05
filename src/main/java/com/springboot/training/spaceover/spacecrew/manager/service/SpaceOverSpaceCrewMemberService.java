package com.springboot.training.spaceover.spacecrew.manager.service;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBER;

import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import com.springboot.training.spaceover.spacecrew.manager.repository.SpaceCrewMemberRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpaceOverSpaceCrewMemberService implements SpaceCrewMemberService {

    private final SpaceCrewMemberRepository spaceCrewMemberRepository;

    private final SpaceShipClient spaceShipClient;

    @Override
    public Page<SpaceCrewMember> findAll(SpaceCrewMember entitySample, Pageable pageRequest) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("spaceShipId", ExampleMatcher.GenericPropertyMatchers.exact());
        return spaceCrewMemberRepository.findAll(Example.of(entitySample, exampleMatcher), pageRequest);
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
    public SpaceCrewMember save(SpaceCrewMember entity) {
        spaceShipClient.findBydId(entity.getSpaceShipId());
        entity.setStatus(SpaceCrewMemberStatus.ACTIVE);
        return spaceCrewMemberRepository.save(entity);
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
