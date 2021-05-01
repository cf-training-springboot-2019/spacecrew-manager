package com.springboot.training.spaceover.spacecrew.manager.repository;

import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceCrewMemberRepository extends JpaRepository<SpaceCrewMember, Long> {
}
