package de.caritas.cob.consultingtypeservice.api.exception;

public class NotUniqueException extends RuntimeException {

  private static final long serialVersionUID = -5083156826149548581L;

  /**
   * Exception, when property of consulting type is not unique
   *
   * @param message
   */
  public NotUniqueException(String message) {
    super(message);
  }

}
