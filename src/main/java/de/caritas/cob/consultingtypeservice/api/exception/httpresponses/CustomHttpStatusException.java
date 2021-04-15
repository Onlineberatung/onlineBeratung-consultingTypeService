package de.caritas.cob.consultingtypeservice.api.exception.httpresponses;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

/**
 * Custom HTTP status exception.
 */
public abstract class CustomHttpStatusException extends RuntimeException {

  private static final long serialVersionUID = -3545035432045919306L;
  private final Consumer<Exception> loggingMethod;

  CustomHttpStatusException(String message, Consumer<Exception> loggingMethod) {
    super(message);
    this.loggingMethod = loggingMethod;
  }

  CustomHttpStatusException(String message, Exception ex, Consumer<Exception> loggingMethod) {
    super(message, ex);
    this.loggingMethod = loggingMethod;
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
