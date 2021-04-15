package de.caritas.cob.consultingtypeservice.api.exception;

public class MissingConsultingTypeException extends Exception {

  private static final long serialVersionUID = -6127271234647444277L;

  /**
   * Exception, when settings for an requested consulting type are missing
   *
   * @param message
   */
  public MissingConsultingTypeException(String message) {
    super(message);
  }

}
