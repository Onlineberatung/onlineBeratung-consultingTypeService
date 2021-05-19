package de.caritas.cob.consultingtypeservice.api.service.securityheader;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHeaderSupplier {

  @Value("${csrf.header.property}")
  private String csrfHeaderProperty;

  @Value("${csrf.cookie.property}")
  private String csrfCookieProperty;

  /**
   * Creates the headers containing keycloak token and csrf headers {@link HttpHeaders} object.
   *
   * @return the created {@link HttpHeaders}
   */
  public HttpHeaders getKeycloakAndCsrfHttpHeaders() {
    return getCsrfHttpHeaders();
  }

  private HttpHeaders getCsrfHttpHeaders() {
    var httpHeaders = new HttpHeaders();

    return this.addCsrfValues(httpHeaders);
  }

  private HttpHeaders addCsrfValues(HttpHeaders httpHeaders) {
    var csrfToken = UUID.randomUUID().toString();

    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpHeaders.add("Cookie", csrfCookieProperty + "=" + csrfToken);
    httpHeaders.add(csrfHeaderProperty, csrfToken);

    return httpHeaders;
  }
}
