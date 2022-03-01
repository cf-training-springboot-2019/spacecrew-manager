package com.springboot.training.spaceover.spacecrew.manager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@Sql("/scripts/import_spacecrew.sql")
//LT10-2-Add integration test to the persistence layer
public class SpaceCrewMemberRepositoryTests {

  private SpaceCrewMemberRepository spaceCrewMemberRepository;

  @Test
  @DisplayName("Given no arguments, when invoking findAll method, then return SpaceCrewMember List")
  void findAllList() {

    List<SpaceCrewMember> spaceCrewList = spaceCrewMemberRepository.findAll();

    //Assert collection

    //Assert first space crew member

    //Assert second space crew member

    //Assert third space crew member

  }

  @Test
  @DisplayName("Given SpaceCrewMember and Pageable arguments, when invoking findAll method, then return SpaceCrewMember Page")
  void findAllPage() {

    SpaceCrewMember spaceCrewMemberSample = SpaceCrewMember.builder().build();

    ExampleMatcher exampleMatcher = ExampleMatcher.matching()
        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
        .withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact())
        .withMatcher("spaceShipId", ExampleMatcher.GenericPropertyMatchers.exact());

    Pageable pageRequest = PageRequest.of(1, 1);

    Page<SpaceCrewMember> spaceCreMemberPage = spaceCrewMemberRepository
        .findAll(Example.of(spaceCrewMemberSample, exampleMatcher), pageRequest);

    //Assert collection

    //Assert space crew member

  }

  @Test
  @DisplayName("Given SpaceCrewMember identifier, when invoking findById method, then return SpaceCrewMember Optional")
  void findById() {

    Optional<SpaceCrewMember> spaceCrewMember = spaceCrewMemberRepository.findById(1L);

    //Assert spaceship

  }

  @Test
  @DisplayName("Given none-existent SpaceCrewMember identifier, when invoking findById method, then return empty SpaceCrewMember Optional")
  void findByIdReturnsOptional() {

    Optional<SpaceCrewMember> spaceCrewMember = spaceCrewMemberRepository.findById(10L);

    //Assert spaceship
  }

  @Test
  @DisplayName("Given SpaceCrewMember, when invoking save method, then return SpaceCrewMember")
  void save() {

    SpaceCrewMember spaceCrewMember4 = SpaceCrewMember.builder()
        .id(4L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.INACTIVE)
        .build();

    spaceCrewMember4 = spaceCrewMemberRepository.save(spaceCrewMember4);

    //Assert space crew member


    //Assert collection
  }

  @Test
  @DisplayName("Given pre-existent SpaceCrewMember, when invoking save method, then return SpaceCrewMember")
  void saveUpdate() {

    SpaceCrewMember spaceCrewMember = SpaceCrewMember.builder()
        .id(1L)
        .name("Finn")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(4000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    spaceCrewMember = spaceCrewMemberRepository.save(spaceCrewMember);

    //Assert space crew member


    //Assert collection
  }

  @Test
  @DisplayName("Given SpaceCrewMember identifier, when invoking deleteById method, then expect reduced collection size")
  void delete() {

    spaceCrewMemberRepository.deleteById(1L);

    //Assert collection
  }
}