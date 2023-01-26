package de.caritas.cob.consultingtypeservice.api.exception;

/** Exception, when unexpected error occurs. */
public class UnexpectedErrorException extends RuntimeException {

  private static final long serialVersionUID = -5083156826149548581L;

  /** Unexpected error exception. */
  public UnexpectedErrorException() {
    super("Service exited with errors");
  }

  /**
   * Unexpected error exception.
   *
   * @param message the error message
   */
  public UnexpectedErrorException(String message) {
    super(message);
  }
}
