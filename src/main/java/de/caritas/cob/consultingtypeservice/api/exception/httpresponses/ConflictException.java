package de.caritas.cob.consultingtypeservice.api.exception.httpresponses;

import de.caritas.cob.consultingtypeservice.api.service.LogService;

/** Representation of a 409 - CONFLICT. */
public class ConflictException extends CustomHttpStatusException {

  public ConflictException(String message) {
    super(message, LogService::logWarning);
  }
}
