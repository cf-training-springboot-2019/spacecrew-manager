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

@WebMvcTest
@ActiveProfiles("test")
class SpaceOverSpaceCrewMemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SpaceCrewMemberService spaceCrewMemberService;

  @MockBean
  private ModelMapper modelMapper;

  @MockBean
  private PagedResourcesAssembler<SpaceCrewMember> pagedModelAssembler;

  @MockBean
  private PaginationModelAssembler modelAssembler;

  @MockBean
  private SpaceCrewManagerProperties spaceCrewManagerProperties;

  @Autowired
  private HttpHeaderEnrichmentInterceptor httpHeaderEnrichmentInterceptor;

  @Autowired
  private MdcInitInterceptor mdcInitInterceptor;

  @Autowired
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
    when(spaceCrewMemberService.findAll(spaceCrewMemberSample, pageRequest))
        .thenReturn(spaceCrewMemberPage);
    when(pagedModelAssembler.toModel(eq(spaceCrewMemberPage), any(PaginationModelAssembler.class)))
        .thenReturn(response);

    //Act & Assert
    mockMvc.perform(get("/crewmembers")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, GET_SPACE_CREW_MEMBERS_SERVICE_OPERATION))
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[0].id").value(1))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[0].name")
            .value("Han Solo"))
        .andExpect(
            jsonPath("$._embedded.getSpaceCrewMemberResponses[0].role").value("PILOT_OFFICER"))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[0].status").value("ACTIVE"))
        .andExpect(
            jsonPath("$._embedded.getSpaceCrewMemberResponses[0].spaceShipId").value(1234567L))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[0].salary").value(3000L))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[1].id").value(2))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[1].name")
            .value("Rose Tico"))
        .andExpect(
            jsonPath("$._embedded.getSpaceCrewMemberResponses[1].role").value("ENGINEER_OFFICER"))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[1].status").value("INACTIVE"))
        .andExpect(
            jsonPath("$._embedded.getSpaceCrewMemberResponses[1].spaceShipId").value(1234567L))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[1].salary").value(2000L))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[2].id").value(3))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[2].name")
            .value("Finn"))
        .andExpect(
            jsonPath("$._embedded.getSpaceCrewMemberResponses[2].role").value("ENGINEER_OFFICER"))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[2].status").value("ACTIVE"))
        .andExpect(
            jsonPath("$._embedded.getSpaceCrewMemberResponses[2].spaceShipId").value(1234567L))
        .andExpect(jsonPath("$._embedded.getSpaceCrewMemberResponses[2].salary").value(1850L))
        .andExpect(jsonPath("$.page.number").value(0))
        .andExpect(jsonPath("$.page.size").value(20))
        .andExpect(jsonPath("$.page.totalElements").value(3))
        .andExpect(jsonPath("$.page.totalPages").value(1));
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

    when(spaceCrewMemberService.findBydId(1L)).thenReturn(spaceCrewMember);
    when(modelMapper.map(spaceCrewMember, GetSpaceCrewMemberResponse.class)).thenReturn(response);
    //Act & Assert
    mockMvc.perform(get("/crewmembers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, GET_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Han Solo"))
        .andExpect(jsonPath("$.role").value("PILOT_OFFICER"))
        .andExpect(jsonPath("$.status").value("ACTIVE"))
        .andExpect(jsonPath("$.spaceShipId").value(1234567L))
        .andExpect(jsonPath("$.salary").value(3000L));

  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking GET /crewmembers/{id} with none-existent identifier, then reply 404 response")
  void getSpaceCrewMemberNotFound() {
    //Arrange
    when(spaceCrewMemberService.findBydId(1L))
        .thenThrow(
            new EntityNotFoundException(
                String.format(ENTITY_NOT_FOUND_MSG, SPACE_CREW_MEMBER, 1L)));
    //Arrange
    mockMvc.perform(get("/crewmembers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, GET_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$.reason").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
        .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(
            jsonPath("$.message")
                .value(String.format(ENTITY_NOT_FOUND_MSG, SPACE_CREW_MEMBER, 1L)));

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

    when(modelMapper.map(request, SpaceCrewMember.class)).thenReturn(spaceCrewMember);
    when(spaceCrewMemberService.save(spaceCrewMember)).thenReturn(persistedSpaceCrewMember);
    //Arrange
    mockMvc.perform(post("/crewmembers")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestContent))
        .andExpect(status().isCreated())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andExpect(header().string(LOCATION, "http://localhost/crewmembers/1"));
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
        .content(requestContent))
        .andExpect(status().isBadRequest())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, CREATE_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$.reason").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.message").value(containsString("role must not be null")));
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

    when(spaceCrewMemberService.findBydId(1L)).thenReturn(spaceCrewMember);
    when(spaceCrewMemberService.update(patchedSpaceCrewMember)).thenReturn(patchedSpaceCrewMember);
    when(modelMapper.map(patchedSpaceCrewMember, PatchSpaceCrewMemberResponse.class))
        .thenReturn(response);
    //Act & Assert
    mockMvc.perform(patch("/crewmembers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(APPLICATION_JSON_PATCH)
        .content(requestContent))
        .andExpect(status().isOk())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, PATCH_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andDo(print())
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Han Solo"))
        .andExpect(jsonPath("$.role").value("COMMANDER_OFFICER"))
        .andExpect(jsonPath("$.status").value("ACTIVE"))
        .andExpect(jsonPath("$.spaceShipId").value(1234567L))
        .andExpect(jsonPath("$.salary").value(5000L));
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

    when(modelMapper.map(request, SpaceCrewMember.class)).thenReturn(spaceCrewMember);
    when(spaceCrewMemberService.update(spaceCrewMember)).thenReturn(spaceCrewMember);
    when(modelMapper.map(spaceCrewMember, PutSpaceCrewMemberResponse.class)).thenReturn(response);
    //Act & Assert
    mockMvc.perform(put("/crewmembers/3")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestContent))
        .andExpect(status().isOk())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.name").value("Finn"))
        .andExpect(jsonPath("$.role").value("ENGINEER_OFFICER"))
        .andExpect(jsonPath("$.status").value("ACTIVE"))
        .andExpect(jsonPath("$.spaceShipId").value(1234567L))
        .andExpect(jsonPath("$.salary").value(1850L));

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
        .content(requestContent))
        .andExpect(status().isBadRequest())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, PUT_SPACE_CREW_MEMBER_SERVICE_OPERATION))
        .andExpect(header().string(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
        .andExpect(jsonPath("$.reason").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
        .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
        .andExpect(jsonPath("$.message").value(containsString("name must not be empty")));
  }

  @Test
  @SneakyThrows
  @DisplayName("Given a consumer client, when invoking DELETE /crewmembers/{id}, then reply 204 response")
  void deleteSpaceCrewMemberNoContent() {
    //No Arrange required
    //Act & Assert
    mockMvc.perform(delete("/crewmembers/1"))
        .andExpect(status().isNoContent())
        .andExpect(header().exists(TRACE_ID_HEADER))
        .andExpect(
            header().string(SERVICE_OPERATION_HEADER, DELETE_SPACE_CREW_MEMBER_SERVICE_OPERATION));
  }
}