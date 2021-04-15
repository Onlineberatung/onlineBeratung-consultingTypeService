package de.caritas.cob.consultingtypeservice.api.exception.httpresponses;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import de.caritas.cob.consultingtypeservice.api.service.LogService;
import java.util.function.Consumer;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom validation exception for http status with reason.
 */
@Getter
public abstract class CustomValidationHttpStatusException extends RuntimeException {

  private final Consumer<Exception> loggingMethod;
  private final HttpStatusExceptionReason httpStatusExceptionReason;
  private final HttpStatus httpStatus;

  CustomValidationHttpStatusException(HttpStatusExceptionReason httpStatusExceptionReason) {
    super();
    this.loggingMethod = LogService::logWarning;
    this.httpStatusExceptionReason = httpStatusExceptionReason;
    this.httpStatus = BAD_REQUEST;
  }

  CustomValidationHttpStatusException(HttpStatusExceptionReason httpStatusExceptionReason,
      HttpStatus httpStatus) {
    super();
    this.loggingMethod = LogService::logWarning;
    this.httpStatusExceptionReason = httpStatusExceptionReason;
    this.httpStatus = httpStatus;
  }

  /**
   * Executes the non null logging method.
   */
  public void executeLogging() {
    if (nonNull(this.loggingMethod)) {
      this.loggingMethod.accept(this);
    }
  }
}
