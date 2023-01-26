package de.caritas.cob.consultingtypeservice.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Operation not allowed for current user")
public class SettingsPermissionException extends RuntimeException {

  public SettingsPermissionException(String message) {
    super(message);
  }
}
