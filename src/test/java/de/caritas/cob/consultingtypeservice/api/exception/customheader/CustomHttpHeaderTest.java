package de.caritas.cob.consultingtypeservice.api.exception.customheader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.HttpStatusExceptionReason;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

public class CustomHttpHeaderTest {

  @Test
  public void buildHeader_Should_ReturnHttpHeadersWithXReason() {

    CustomHttpHeader customHttpHeader = new CustomHttpHeader(HttpStatusExceptionReason.LOCKED_CONSULTING_TYPE);
    HttpHeaders result = customHttpHeader.buildHeader();
    assertTrue(result.containsKey("X-Reason"));

  }

  @Test
  public void buildHeader_Should_ReturnCorrectHttpStatusExceptionReason() {

    CustomHttpHeader customHttpHeader = new CustomHttpHeader(HttpStatusExceptionReason.LOCKED_CONSULTING_TYPE);
    HttpHeaders result = customHttpHeader.buildHeader();
    assertEquals(HttpStatusExceptionReason.LOCKED_CONSULTING_TYPE.toString(), result.get("X-Reason").get(0));
  }

}
