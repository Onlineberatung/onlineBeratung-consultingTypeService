package de.caritas.cob.consultingtypeservice.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "feature topic disabled")
public class TopicAccessDeniedException extends AccessDeniedException {

  public TopicAccessDeniedException(String msg) {
    super(msg);
  }
}
