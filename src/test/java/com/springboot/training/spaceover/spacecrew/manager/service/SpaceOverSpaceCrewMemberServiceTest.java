package com.springboot.training.spaceover.spacecrew.manager.service;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.springboot.training.spaceover.spacecrew.manager.domain.model.SpaceCrewMember;
import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberRole;
import com.springboot.training.spaceover.spacecrew.manager.enums.SpaceCrewMemberStatus;
import com.springboot.training.spaceover.spacecrew.manager.repository.SpaceCrewMemberRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
class SpaceOverSpaceCrewMemberServiceTest {

  @InjectMocks
  SpaceOverSpaceCrewMemberService service;

  @Mock
  SpaceShipClient spaceShipClient;

  @Mock
  SpaceCrewMemberRepository spaceCrewMemberRepository;


  private SpaceCrewMember spaceCrewMember1;
  private SpaceCrewMember spaceCrewMember2;
  private GetSpaceShipResponse spaceShip;

  @BeforeEach
  public void setUp() {
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

    when(spaceCrewMemberRepository
        .findAll(Example.of(spaceMissionSample, exampleMatcher), pageRequest))
        .thenReturn(new PageImpl<>(Collections.singletonList(spaceCrewMember1)));

    Page<SpaceCrewMember> response = service.findAll(spaceMissionSample, pageRequest);

    assertEquals(1, response.getTotalElements());
    assertEquals(1, response.getTotalPages());
    assertEquals(1, response.getContent().size());
    assertTrue(response.getContent().contains(spaceCrewMember1));
  }

  @Test
  void findAll() {

    List<SpaceCrewMember> listOfSpaceCrewMembersPersisted = new ArrayList<>();

    listOfSpaceCrewMembersPersisted.add(spaceCrewMember1);
    listOfSpaceCrewMembersPersisted.add(spaceCrewMember2);

    when(spaceCrewMemberRepository.findAll()).thenReturn(listOfSpaceCrewMembersPersisted);

    List<SpaceCrewMember> response = service.findAll();

    assertEquals(2, response.size());
    assertTrue(response.stream().anyMatch(crewMember -> crewMember.getName().equals("Han Solo")));
    assertTrue(response.stream().anyMatch(crewMember -> crewMember.getName().equals("Rose Tico")));
  }

  @Test
  void findBydId() {

    when(spaceCrewMemberRepository.findById(1L)).thenReturn(Optional.of(spaceCrewMember1));

    SpaceCrewMember response = service.findBydId(1L);

    assertEquals(1L, response.getId());
    assertEquals("Han Solo", response.getName());
    assertEquals(SpaceCrewMemberRole.PILOT_OFFICER, response.getRole());
    assertEquals(1234567L, response.getSpaceShipId());
    assertEquals(BigDecimal.valueOf(3000L), response.getSalary());
    assertEquals(SpaceCrewMemberStatus.ACTIVE, response.getStatus());
  }

  @Test
  void findBydId_EntityNotFound() {

    when(spaceCrewMemberRepository.findById(1L)).thenReturn(Optional.empty());

    EntityNotFoundException e = assertThrows(EntityNotFoundException.class,
        () -> service.findBydId(1L));

    assertEquals(String.format(ENTITY_NOT_FOUND_MSG, SPACE_CREW_MEMBER, 1L), e.getMessage());
  }

  @Test
  void save() {

    when(spaceShipClient.findBydId(1234567L)).thenReturn(spaceShip);

    spaceCrewMember2.setStatus(SpaceCrewMemberStatus.ACTIVE);
    when(spaceCrewMemberRepository.save(spaceCrewMember2)).thenReturn(spaceCrewMember2);

    SpaceCrewMember response = service.save(spaceCrewMember2);

    assertEquals(2L, response.getId());
    assertEquals("Rose Tico", response.getName());
    assertEquals(SpaceCrewMemberRole.ENGINEER_OFFICER, response.getRole());
    assertEquals(1234567L, response.getSpaceShipId());
    assertEquals(BigDecimal.valueOf(2000L), response.getSalary());
    assertEquals(SpaceCrewMemberStatus.ACTIVE, response.getStatus());
  }

  @Test
  void update() {

    when(spaceShipClient.findBydId(1234567L)).thenReturn(spaceShip);

    SpaceCrewMember spaceCrewMemberToPersist = SpaceCrewMember.builder()
        .id(2L)
        .name("Rose Tico Tico")
        .role(SpaceCrewMemberRole.COMMANDER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(4000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    when(spaceCrewMemberRepository.save(spaceCrewMemberToPersist))
        .thenReturn(spaceCrewMemberToPersist);

    SpaceCrewMember response = service.update(spaceCrewMemberToPersist);

    assertEquals(2L, response.getId());
    assertEquals("Rose Tico Tico", response.getName());
    assertEquals(SpaceCrewMemberRole.COMMANDER_OFFICER, response.getRole());
    assertEquals(1234567L, response.getSpaceShipId());
    assertEquals(BigDecimal.valueOf(4000L), response.getSalary());
    assertEquals(SpaceCrewMemberStatus.ACTIVE, response.getStatus());
  }

  @Test
  void deleteById() {

    doNothing().when(spaceCrewMemberRepository).deleteById(1L);

    service.deleteById(1L);

    verify(spaceCrewMemberRepository, times(1)).deleteById(1L);
  }
}