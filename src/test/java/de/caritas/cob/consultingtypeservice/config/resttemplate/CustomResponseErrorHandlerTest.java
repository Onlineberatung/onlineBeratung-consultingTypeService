package de.caritas.cob.consultingtypeservice.config.resttemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.server.ResponseStatusException;

public class CustomResponseErrorHandlerTest {

  CustomResponseErrorHandler customResponseErrorHandler = new CustomResponseErrorHandler();

  @Test
  public void handleError_Should_throwExpectedResponseStatusException() throws IOException {
    URI uri = URI.create("/access/endpoint");
    HttpMethod httpMethod = HttpMethod.GET;
    ClientHttpResponse clientHttpResponse = mock(ClientHttpResponse.class);

    when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.I_AM_A_TEAPOT);

    try {
      this.customResponseErrorHandler.handleError(uri, httpMethod, clientHttpResponse);
      fail("Exception was not thrown");
    } catch (ResponseStatusException e) {
      assertThat(e.getStatus(), is(HttpStatus.I_AM_A_TEAPOT));
      assertThat(e.getMessage(), is("418 I_AM_A_TEAPOT \"GET /access/endpoint\""));
    }
  }
}
