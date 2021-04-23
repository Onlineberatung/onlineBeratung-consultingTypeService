package de.caritas.cob.consultingtypeservice.api.exception;

public class UnexpectedErrorException extends RuntimeException {

  private static final long serialVersionUID = -5083156826149548581L;

  /**
   * Unexpected error exception
   *
   * @param message the error message
   */
  public UnexpectedErrorException(String message) {
    super(message);
  }

}
