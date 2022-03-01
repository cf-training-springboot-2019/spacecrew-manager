package com.springboot.training.spaceover.spacecrew.manager.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import com.springboot.training.spaceover.spacecrew.manager.repository.SpaceCrewMemberRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

//LT10-2-Add unit test to the service layer
class SpaceOverSpaceCrewMemberServiceTest {

  SpaceOverSpaceCrewMemberService service;

  SpaceShipClient spaceShipClient;

  SpaceCrewMemberRepository spaceCrewMemberRepository;


  private SpaceCrewMember spaceCrewMember1;
  private SpaceCrewMember spaceCrewMember2;
  private GetSpaceShipResponse spaceShip;

  @BeforeEach
  public void setUp() {
    //Arrange
    spaceCrewMember1 = SpaceCrewMember.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    spaceCrewMember2 = SpaceCrewMember.builder()
        .id(2L)
        .name("Rose Tico")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(2000L))
        .status(SpaceCrewMemberStatus.INACTIVE)
        .build();

    spaceShip = GetSpaceShipResponse.builder()
        .id(1234567L)
        .name("Millennium Falcon")
        .status("ACTIVE")
        .type("Light freighter")
        .maxOccupancy(BigInteger.valueOf(1000L))
        .build();
  }

  @Test
  @DisplayName("Given a SpaceCrewMember and Pageable arguments, when invoking findAll method, then return SpaceCrewMember Page")
  void findAll_Paged() {

    SpaceCrewMember spaceMissionSample = SpaceCrewMember.builder()
        .name("Han")
        .spaceShipId(1234567L)
        .build();

    ExampleMatcher exampleMatcher = ExampleMatcher.matching()
        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
        .withMatcher("status", ExampleMatcher.GenericPropertyMatchers.exact())
        .withMatcher("role", ExampleMatcher.GenericPropertyMatchers.exact())
        .withMatcher("spaceShipId", ExampleMatcher.GenericPropertyMatchers.exact());

    Pageable pageRequest = PageRequest.of(0, 20, Sort.by(Direction.ASC, "id"));

    //Arrange
    when(spaceCrewMemberRepository
        .findAll(Example.of(spaceMissionSample, exampleMatcher), pageRequest));

    //Act
    Page<SpaceCrewMember> response = service.findAll(spaceMissionSample, pageRequest);

    //Assert

  }

  @Test
  @DisplayName("Given no arguments, when invoking findAll method, then return SpaceMission List")
  void findAll() {

    //Arrange
    List<SpaceCrewMember> listOfSpaceCrewMembersPersisted = new ArrayList<>();

    listOfSpaceCrewMembersPersisted.add(spaceCrewMember1);
    listOfSpaceCrewMembersPersisted.add(spaceCrewMember2);

    //Act
    when(spaceCrewMemberRepository.findAll());

    List<SpaceCrewMember> response = service.findAll();

    //Assert

  }

  @Test
  @DisplayName("Given a valid SpaceCrewMember identifier argument, when invoking findById method, then return SpaceCrewMember")
  void findBydId() {

    //Arrange
    when(spaceCrewMemberRepository.findById(1L));

    //Act
    SpaceCrewMember response = service.findBydId(1L);

    //Assert

  }

  @Test
  @DisplayName("Given an invalid SpaceCrewMember identifier argument, when invoking findById method, then throw EntityNotFoundException")
  void findBydId_EntityNotFound() {

    //Arrange
    when(spaceCrewMemberRepository.findById(1L));

    //ACt & Assert
    assertThrows(null, () -> service.findBydId(1L), "");

  }

  @Test
  @DisplayName("Given a SpaceCrewMember, when invoking save method, then return SpaceCrewMember")
  void save() {

    //Arrange
    when(spaceShipClient.findBydId(1234567L));

    spaceCrewMember2.setStatus(SpaceCrewMemberStatus.ACTIVE);

    when(spaceCrewMemberRepository.save(spaceCrewMember2));

    //Act
    SpaceCrewMember response = service.save(spaceCrewMember2);

    //Assert

  }

  @Test
  @DisplayName("Given a SpaceCrewMember, when invoking update method, then return SpaceCrewMember")
  void update() {

    //Arrange
    when(spaceShipClient.findBydId(1234567L));

    SpaceCrewMember spaceCrewMemberToPersist = SpaceCrewMember.builder()
        .id(2L)
        .name("Rose Tico Tico")
        .role(SpaceCrewMemberRole.COMMANDER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(4000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    when(spaceCrewMemberRepository.save(spaceCrewMemberToPersist));

    //Act
    SpaceCrewMember response = service.update(spaceCrewMemberToPersist);

    //Assert

  }

  @Test
  @DisplayName("Given a SpaceCrewMember identifier, when invoking delete method, then invoke repository.deleteById")
  void deleteById() {

    //Arrange
    doNothing().when(spaceCrewMemberRepository).deleteById(1L);

    //Act
    service.deleteById(1L);

    //Assert
  }
}