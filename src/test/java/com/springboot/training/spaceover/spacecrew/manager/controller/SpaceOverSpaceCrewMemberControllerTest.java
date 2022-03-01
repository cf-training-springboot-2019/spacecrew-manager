package com.springboot.training.spaceover.spacecrew.manager.controller;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.APPLICATION_JSON_PATCH;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.ENTITY_NOT_FOUND_MSG;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.GET_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SERVICE_OPERATION_HEADER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.SPACE_CREW_MEMBER;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.TRACE_ID_HEADER;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.HttpHeaderEnrichmentInterceptor;
import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.MdcInitInterceptor;
import com.springboot.training.spaceover.spacecrew.manager.utils.properties.SpaceCrewManagerProperties;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

//LT10-3-Add integration test to the exposure layer
@ActiveProfiles("test")
class SpaceOverSpaceCrewMemberControllerTest {

  private MockMvc mockMvc;

  private SpaceCrewMemberService spaceCrewMemberService;

  private ModelMapper modelMapper;

  private PagedResourcesAssembler<SpaceCrewMember> pagedModelAssembler;

  private PaginationModelAssembler modelAssembler;

  private SpaceCrewManagerProperties spaceCrewManagerProperties;

  private HttpHeaderEnrichmentInterceptor httpHeaderEnrichmentInterceptor;

  private MdcInitInterceptor mdcInitInterceptor;

  private ExceptionHandlingController exceptionHandlingController;

  @Value("classpath:samples/requests/createSpaceCrewMember201.json")
  private Resource createSpaceCrewMember201Request;

  @Value("classpath:samples/requests/createSpaceCrewMember400.json")
  private Resource createSpaceCrewMember400Request;

  @Value("classpath:samples/requests/patchCrewMember200.json")
  private Resource patchSpaceCrewMember200Request;

  @Value("classpath:samples/requests/putSpaceCrewMember200.json")
  private Resource putSpaceCrewMember200Request;

  @Value("classpath:samples/requests/putSpaceCrewMember400.json")
  private Resource putSpaceCrewMember400Request;

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking GET /crewmembers, then reply 200 response")
  void getSpaceCrewMembersOk() {

    //Arrange
    SpaceCrewMember spaceCrewMemberOne = SpaceCrewMember.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    SpaceCrewMember spaceCrewMemberTwo = SpaceCrewMember.builder()
        .id(2L)
        .name("Rose Tico")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(2000L))
        .status(SpaceCrewMemberStatus.INACTIVE)
        .build();

    SpaceCrewMember spaceCrewMemberThree = SpaceCrewMember.builder()
        .id(3L)
        .name("Finn")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(1850L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    GetSpaceCrewMemberResponse spaceCrewMemberOneResponse = GetSpaceCrewMemberResponse.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    GetSpaceCrewMemberResponse spaceCrewMemberTwoResponse = GetSpaceCrewMemberResponse.builder()
        .id(2L)
        .name("Rose Tico")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(2000L))
        .status(SpaceCrewMemberStatus.INACTIVE)
        .build();

    GetSpaceCrewMemberResponse spaceCrewMemberThreeResponse = GetSpaceCrewMemberResponse.builder()
        .id(3L)
        .name("Finn")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(1850L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    List<SpaceCrewMember> spaceCrewMemberList = Arrays
        .asList(spaceCrewMemberOne, spaceCrewMemberTwo, spaceCrewMemberThree);
    List<GetSpaceCrewMemberResponse> spaceMissionResponseList = Arrays
        .asList(spaceCrewMemberOneResponse, spaceCrewMemberTwoResponse,
            spaceCrewMemberThreeResponse);

    SpaceCrewMember spaceCrewMemberSample = SpaceCrewMember.builder().build();

    Pageable pageRequest = PageRequest.of(0, 20);

    Page<SpaceCrewMember> spaceCrewMemberPage = new PageImpl<>(spaceCrewMemberList, pageRequest, 5);

    PagedModel<GetSpaceCrewMemberResponse> response = PagedModel
        .of(spaceMissionResponseList, new PagedModel.PageMetadata(20, 0, 3, 1));

    when(spaceCrewMemberService.findAll(spaceCrewMemberSample, pageRequest)).thenReturn(spaceCrewMemberPage);

    when(pagedModelAssembler.toModel(eq(spaceCrewMemberPage), any(PaginationModelAssembler.class)));

    //Act & Assert
    mockMvc.perform(get("/crewmembers")
        .accept(MediaType.APPLICATION_JSON));
  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking GET /crewmembers/{id} with existent identifier, then reply 200 response")
  void getSpaceCrewMemberOk() {
    //Arrange
    SpaceCrewMember spaceCrewMember = SpaceCrewMember.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    GetSpaceCrewMemberResponse response = GetSpaceCrewMemberResponse.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    when(spaceCrewMemberService.findBydId(1L));
    when(modelMapper.map(spaceCrewMember, GetSpaceCrewMemberResponse.class));
    //Act & Assert
    mockMvc.perform(get("/crewmembers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON));

  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking GET /crewmembers/{id} with none-existent identifier, then reply 404 response")
  void getSpaceCrewMemberNotFound() {
    //Arrange
    when(spaceCrewMemberService.findBydId(1L));
    //Arrange
    mockMvc.perform(get("/crewmembers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON));

  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking POST /crewmembers with valid request, then reply 201 response")
  void createSpaceCrewMemberCreated() {
    //Arrange
    CreateSpaceCrewMemberRequest request = CreateSpaceCrewMemberRequest.builder()
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .build();

    SpaceCrewMember spaceCrewMember = SpaceCrewMember.builder()
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .build();

    SpaceCrewMember persistedSpaceCrewMember = SpaceCrewMember.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    String requestContent = FileCopyUtils
        .copyToString(new FileReader(createSpaceCrewMember201Request.getFile()));

    when(modelMapper.map(request, SpaceCrewMember.class));
    when(spaceCrewMemberService.save(spaceCrewMember));
    //Arrange
    mockMvc.perform(post("/crewmembers")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestContent));
  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking POST /crewmembers with invalid request payload, then reply 400 response")
  void createSpaceCrewMemberBadRequest() {
    //Arrange
    String requestContent = FileCopyUtils
        .copyToString(new FileReader(createSpaceCrewMember400Request.getFile()));
    //Act & Assert
    mockMvc.perform(post("/crewmembers")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestContent));
  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking PATCH /crewmembers/{id} with valid request, then reply 200 response")
  void patchSpaceMissionOk() {
    //Arrange
    SpaceCrewMember spaceCrewMember = SpaceCrewMember.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.PILOT_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(3000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    SpaceCrewMember patchedSpaceCrewMember = SpaceCrewMember.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.COMMANDER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(5000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    PatchSpaceCrewMemberResponse response = PatchSpaceCrewMemberResponse.builder()
        .id(1L)
        .name("Han Solo")
        .role(SpaceCrewMemberRole.COMMANDER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(5000L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    String requestContent = FileCopyUtils
        .copyToString(new FileReader(patchSpaceCrewMember200Request.getFile()));

    when(spaceCrewMemberService.findBydId(1L));
    when(spaceCrewMemberService.update(patchedSpaceCrewMember));
    when(modelMapper.map(patchedSpaceCrewMember, PatchSpaceCrewMemberResponse.class));
    //Act & Assert
    mockMvc.perform(patch("/crewmembers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(APPLICATION_JSON_PATCH)
        .content(requestContent));
  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking PUT /crewmembers/{id} with valid request, then reply 200 response")
  void putSpaceMissionOk() {
    //Arrange
    PutSpaceCrewMemberRequest request = PutSpaceCrewMemberRequest.builder()
        .id(3L)
        .name("Finn")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(1850L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    SpaceCrewMember spaceCrewMember = SpaceCrewMember.builder()
        .id(3L)
        .name("Finn")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(1850L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    PutSpaceCrewMemberResponse response = PutSpaceCrewMemberResponse.builder()
        .id(3L)
        .name("Finn")
        .role(SpaceCrewMemberRole.ENGINEER_OFFICER)
        .spaceShipId(1234567L)
        .salary(BigDecimal.valueOf(1850L))
        .status(SpaceCrewMemberStatus.ACTIVE)
        .build();

    String requestContent = FileCopyUtils
        .copyToString(new FileReader(putSpaceCrewMember200Request.getFile()));

    when(modelMapper.map(request, SpaceCrewMember.class));
    when(spaceCrewMemberService.update(spaceCrewMember));
    when(modelMapper.map(spaceCrewMember, PutSpaceCrewMemberResponse.class));
    //Act & Assert
    mockMvc.perform(put("/crewmembers/3")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestContent));

  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking PUT /crewmembers/{id} with invalid request body, then reply 400 response")
  void putSpaceMissionBadRequest() {
    //Arrange
    String requestContent = FileCopyUtils
        .copyToString(new FileReader(putSpaceCrewMember400Request.getFile()));
    //Act & Assert
    mockMvc.perform(put("/crewmembers/3")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestContent));
  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking DELETE /crewmembers/{id}, then reply 204 response")
  void deleteSpaceCrewMemberNoContent() {
    //No Arrange required
    //Act & Assert
    mockMvc.perform(delete("/crewmembers/1"));
  }
}