package de.caritas.cob.consultingtypeservice.config.resttemplate;

import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

/** Custom rest template error handler to deal with unexpected client errors. */
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler {

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse response)
      throws IOException {
    throw new ResponseStatusException(
        response.getStatusCode(), method.name() + " " + url.toString());
  }
}
