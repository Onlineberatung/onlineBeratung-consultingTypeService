package de.caritas.cob.consultingtypeservice.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TopicValidationException extends RuntimeException {

  public TopicValidationException(final String message) {
    super(message);
  }

  public TopicValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
