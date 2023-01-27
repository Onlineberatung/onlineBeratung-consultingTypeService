package de.caritas.cob.consultingtypeservice.api.service;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import com.github.jknack.handlebars.internal.text.TextStringBuilder;
import org.everit.json.schema.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/** Service for logging. */
public class LogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);
  private static final String MESSAGE_CONSULTING_TYPE_SERVICE_API = "ConsultingTypeService API: ";
  private static final String MESSAGE_SERVER_ERROR = "500 Internal Server Error: ";

  private LogService() {}

  /**
   * Logs a warning.
   *
   * @param exception the exception to be logged
   */
  public static void logWarning(Exception exception) {
    LOGGER.warn("{}{}", MESSAGE_CONSULTING_TYPE_SERVICE_API, getStackTrace(exception));
  }

  /**
   * Logs a warning.
   *
   * @param httpStatus status to be logged
   * @param exception the exception to be logged
   */
  public static void logWarning(HttpStatus httpStatus, Exception exception) {
    LOGGER.warn(
        "{}{}{}",
        MESSAGE_CONSULTING_TYPE_SERVICE_API,
        httpStatus.getReasonPhrase(),
        getStackTrace(exception));
  }

  /**
   * Logs an warning.
   *
   * @param message the exception to be logged to be logged
   */
  public static void logWarning(String message) {
    LOGGER.warn("{}{}", MESSAGE_CONSULTING_TYPE_SERVICE_API, message);
  }

  /**
   * Logs a internal server error.
   *
   * @param exception the exception to be logged
   */
  public static void logInternalServerError(Exception exception) {
    LOGGER.error(
        "{}{}{}",
        MESSAGE_CONSULTING_TYPE_SERVICE_API,
        MESSAGE_SERVER_ERROR,
        getStackTrace(exception));
  }

  /**
   * Logs an error.
   *
   * @param exception the exception to be logged
   */
  public static void logError(Exception exception) {
    LOGGER.error("{}{}", MESSAGE_CONSULTING_TYPE_SERVICE_API, getStackTrace(exception));
  }

  /**
   * Logs an error.
   *
   * @param message the exception to be logged to be logged
   */
  public static void logError(String message) {
    LOGGER.error("{}{}", MESSAGE_CONSULTING_TYPE_SERVICE_API, message);
  }

  /**
   * Logs an json schema {@link ValidationException} with all violations.
   *
   * @param message an error message
   * @param filename the name of the invalid file
   * @param validationException the {@link ValidationException}
   */
  public static void logJsonSchemaValidationException(
      String message, String filename, ValidationException validationException) {
    var textStringBuilder = new TextStringBuilder();
    textStringBuilder.appendNewLine();
    textStringBuilder.appendln(message);
    textStringBuilder.appendln(
        String.format("%s in file %s", validationException.getMessage(), filename));
    validationException.getCausingExceptions().stream()
        .map(ValidationException::getMessage)
        .forEach(textStringBuilder::appendln);
    LOGGER.error("{}{}", MESSAGE_CONSULTING_TYPE_SERVICE_API, textStringBuilder);
  }
}
