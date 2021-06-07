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

@DataJpaTest
@ActiveProfiles("test")
@Sql("/scripts/import_spacecrew.sql")
public class SpaceCrewMemberRepositoryTests {

  @Autowired
  private SpaceCrewMemberRepository spaceCrewMemberRepository;

  @Test
  void findAllList() {

    List<SpaceCrewMember> spaceCrewList = spaceCrewMemberRepository.findAll();

    //Assert collection
    assertNotNull(spaceCrewList);
    assertEquals(3, spaceCrewList.size());

    //Assert first space crew member
    assertNotNull(spaceCrewList.get(0));
    assertEquals(1L, spaceCrewList.get(0).getId());
    assertEquals(123456789L, spaceCrewList.get(0).getSpaceShipId());
    assertEquals("Finn", spaceCrewList.get(0).getName());
    assertEquals(SpaceCrewMemberStatus.ACTIVE, spaceCrewList.get(0).getStatus());
    assertEquals(SpaceCrewMemberRole.ENGINEER_OFFICER, spaceCrewList.get(0).getRole());
    assertEquals(0, BigDecimal.valueOf(3000L).compareTo(spaceCrewList.get(0).getSalary()));


    //Assert second space crew member
    assertNotNull(spaceCrewList.get(1));
    assertEquals(2L, spaceCrewList.get(1).getId());
    assertEquals(123456789L, spaceCrewList.get(1).getSpaceShipId());
    assertEquals("Princess Leia", spaceCrewList.get(1).getName());
    assertEquals(SpaceCrewMemberStatus.INACTIVE, spaceCrewList.get(1).getStatus());
    assertEquals(SpaceCrewMemberRole.COMMANDER_OFFICER, spaceCrewList.get(1).getRole());
    assertEquals(0, BigDecimal.valueOf(4000L).compareTo(spaceCrewList.get(1).getSalary()));

    //Assert third space crew member
    assertNotNull(spaceCrewList.get(2));
    assertEquals(3L, spaceCrewList.get(2).getId());
    assertEquals(123456789L, spaceCrewList.get(2).getSpaceShipId());
    assertEquals("Rose Tico", spaceCrewList.get(2).getName());
    assertEquals(SpaceCrewMemberStatus.INACTIVE, spaceCrewList.get(2).getStatus());
    assertEquals(SpaceCrewMemberRole.ENGINEER_OFFICER, spaceCrewList.get(2).getRole());
    assertEquals(0, BigDecimal.valueOf(3000L).compareTo(spaceCrewList.get(2).getSalary()));
  }

  @Test
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
    assertNotNull(spaceCreMemberPage);
    assertEquals(1, spaceCreMemberPage.getNumberOfElements());
    assertEquals(3, spaceCreMemberPage.getTotalElements());
    assertNotNull(spaceCreMemberPage.getContent());
    assertTrue(spaceCreMemberPage.hasPrevious());
    assertTrue(spaceCreMemberPage.hasNext());

    //Assert space crew member
    assertNotNull(spaceCreMemberPage.getContent().get(0));
    assertEquals(2L, spaceCreMemberPage.getContent().get(0).getId());
    assertEquals(123456789L, spaceCreMemberPage.getContent().get(0).getSpaceShipId());
    assertEquals("Princess Leia", spaceCreMemberPage.getContent().get(0).getName());
    assertEquals(SpaceCrewMemberStatus.INACTIVE,
        spaceCreMemberPage.getContent().get(0).getStatus());
    assertEquals(SpaceCrewMemberRole.COMMANDER_OFFICER,
        spaceCreMemberPage.getContent().get(0).getRole());
    assertEquals(0, BigDecimal.valueOf(4000L).compareTo(spaceCreMemberPage.getContent().get(0).getSalary()));
  }

  @Test
  void findById() {

    Optional<SpaceCrewMember> spaceCrewMember = spaceCrewMemberRepository.findById(1L);

    //Assert spaceship
    assertTrue(spaceCrewMember.isPresent());
    assertNotNull(spaceCrewMember.get());
    assertEquals(1L, spaceCrewMember.get().getId());
    assertEquals(123456789L, spaceCrewMember.get().getSpaceShipId());
    assertEquals("Finn", spaceCrewMember.get().getName());
    assertEquals(SpaceCrewMemberStatus.ACTIVE, spaceCrewMember.get().getStatus());
    assertEquals(SpaceCrewMemberRole.ENGINEER_OFFICER, spaceCrewMember.get().getRole());
    assertEquals(0, BigDecimal.valueOf(3000L).compareTo(spaceCrewMember.get().getSalary()));
  }

  @Test
  void findByIdReturnsOptional() {

    Optional<SpaceCrewMember> spaceCrewMember = spaceCrewMemberRepository.findById(10L);

    //Assert spaceship
    assertFalse(spaceCrewMember.isPresent());
  }

  @Test
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
    assertEquals(4L, spaceCrewMember4.getId());
    assertEquals(1234567L, spaceCrewMember4.getSpaceShipId());
    assertEquals("Han Solo", spaceCrewMember4.getName());
    assertEquals(SpaceCrewMemberStatus.INACTIVE, spaceCrewMember4.getStatus());
    assertEquals(SpaceCrewMemberRole.PILOT_OFFICER, spaceCrewMember4.getRole());
    assertEquals(BigDecimal.valueOf(3000L), spaceCrewMember4.getSalary());

    //Assert collection
    assertEquals(4, spaceCrewMemberRepository.count());
  }

  @Test
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
    assertEquals(1L, spaceCrewMember.getId());
    assertEquals(1234567L, spaceCrewMember.getSpaceShipId());
    assertEquals("Finn", spaceCrewMember.getName());
    assertEquals(SpaceCrewMemberStatus.ACTIVE, spaceCrewMember.getStatus());
    assertEquals(SpaceCrewMemberRole.PILOT_OFFICER, spaceCrewMember.getRole());
    assertEquals(BigDecimal.valueOf(4000L), spaceCrewMember.getSalary());

    //Assert collection
    assertEquals(3, spaceCrewMemberRepository.count());
  }

  @Test
  void delete() {

    spaceCrewMemberRepository.deleteById(1L);

    //Assert collection
    assertEquals(2, spaceCrewMemberRepository.count());
  }
}