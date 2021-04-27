package de.caritas.cob.consultingtypeservice.api.exception;

public class UnexpectedErrorException extends RuntimeException {

  private static final long serialVersionUID = -5083156826149548581L;

  /**
   * Unexpected error exception
   */
  public UnexpectedErrorException() {
    super("Service exited with errors");
  }

}
