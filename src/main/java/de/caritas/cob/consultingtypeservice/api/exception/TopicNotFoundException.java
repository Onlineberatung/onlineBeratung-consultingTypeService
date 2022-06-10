package de.caritas.cob.consultingtypeservice.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such topic")
public class TopicNotFoundException extends RuntimeException {

  public TopicNotFoundException(String exception) {
    super(exception);
  }
}
