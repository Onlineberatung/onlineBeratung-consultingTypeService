package de.caritas.cob.consultingtypeservice.api.exception.customheader;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.HttpStatusExceptionReason;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;

/*
* Custom http header with X-Reason header.
 */
@RequiredArgsConstructor
public class CustomHttpHeader {

  private final @NonNull HttpStatusExceptionReason httpStatusExceptionReason;

  /**
   * Build the header object.
   *
   * @return an instance of {@Link HttpHeaders}
   */
  public HttpHeaders buildHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Reason", httpStatusExceptionReason.name());
    return headers;
  }
}
