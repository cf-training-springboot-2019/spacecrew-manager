package com.springboot.training.spaceover.spacecrew.manager.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.springboot.training.spaceover.spacecrew.manager.domain.response.inbound.GetSpaceShipResponse;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class WebClientSpaceCrewMemberClientTest {

  @InjectMocks
  private WebClientSpaceShipClient webClientSpaceShipClient;

  @Mock
  private WebClient webClient;


  @Test
  void findBydId() {
    //Arrange
    GetSpaceShipResponse spaceShipResponse = GetSpaceShipResponse.builder()
        .id(1L)
        .name("Millennium Falcon")
        .status("ACTIVE")
        .type("STAR_CRUISER")
        .maxOccupancy(BigInteger.TEN)
        .build();

    WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
    WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

    when(webClient.get()).thenReturn(uriSpec);
    when(uriSpec.uri("spaceships/1")).thenReturn(uriSpec);
    when(uriSpec.retrieve()).thenReturn(responseSpec);
    when(responseSpec.bodyToMono(GetSpaceShipResponse.class))
        .thenReturn(Mono.just(spaceShipResponse));

    //Act
    spaceShipResponse = webClientSpaceShipClient.findBydId(1L);
    //Assert
    assertNotNull(spaceShipResponse);
    assertEquals(1L, spaceShipResponse.getId());
    assertEquals("Millennium Falcon", spaceShipResponse.getName());
    assertEquals("ACTIVE", spaceShipResponse.getStatus());
    assertEquals("STAR_CRUISER", spaceShipResponse.getType());
    assertEquals(BigInteger.TEN, spaceShipResponse.getMaxOccupancy());
  }
}