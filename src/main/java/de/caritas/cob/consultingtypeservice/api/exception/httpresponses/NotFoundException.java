package de.caritas.cob.consultingtypeservice.api.exception.httpresponses;

import de.caritas.cob.consultingtypeservice.api.service.LogService;

/** 404 - Not found http exception. */
public class NotFoundException extends CustomHttpStatusException {

  /**
   * Not found exception.
   *
   * @param message the message
   */
  public NotFoundException(String message) {
    super(message, LogService::logWarning);
  }
}
