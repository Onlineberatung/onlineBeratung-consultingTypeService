package de.caritas.cob.consultingtypeservice.api.exception.httpresponses;

import de.caritas.cob.consultingtypeservice.api.service.LogService;
import java.util.function.Consumer;

/**
 * 404 - Not found http exception
 */
public class NotFoundException extends CustomHttpStatusException {

  /**
   * Not found exception.
   *
   * @param message the message
   */
  public NotFoundException(String message) {
    super(message, LogService::logWarning);
  }

  /**
   * Not found exception.
   *
   * @param message an additional message
   * @param loggingMethod the method being used to log this exception
   */
  public NotFoundException(String message, Consumer<Exception> loggingMethod) {
    super(message, loggingMethod);
  }

}
