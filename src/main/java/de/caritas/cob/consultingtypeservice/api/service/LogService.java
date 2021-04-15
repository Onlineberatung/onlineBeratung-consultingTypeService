package de.caritas.cob.consultingtypeservice.api.service;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

import javax.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Service for logging.
 */
public class LogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

  private LogService() {
  }

  /**
   * Logs a database error.
   *
   * @param exception the exception to be logged
   */
  public static void logDatabaseError(Exception exception) {
    LOGGER.error("Database error: {}", getStackTrace(exception));
  }

  /**
   * javax Bad Request Exception.
   *
   * @param exception the exception to be logged
   */
  public static void logBadRequestException(BadRequestException exception) {
    LOGGER.warn("Bad Request: {}", getStackTrace(exception));
  }

  /**
   * Logs a {@link NumberFormatException}.
   *
   * @param exception the exception to be logged
   */
  public static void logNumberFormatException(Exception exception) {
    LOGGER.error("Error while formatting number: {}", getStackTrace(exception));
  }

  /**
   * Logs a info message.
   *
   * @param message the message to be logged
   */
  public static void logInfo(String message) {
    LOGGER.info(message);
  }

  /**
   * Logs a warning.
   *
   * @param exception the exception to be logged
   */
  public static void logWarning(Exception exception) {
    LOGGER.warn("ConsultingTypeService API: {}", getStackTrace(exception));
  }

  /**
   * Logs a warning.
   *
   * @param httpStatus status to be logged
   * @param exception  the exception to be logged
   */
  public static void logWarning(HttpStatus httpStatus, Exception exception) {
    LOGGER.warn("ConsultingTypeService API: {}: {}", httpStatus.getReasonPhrase(),
        getStackTrace(exception));
  }

  /**
   * Logs a internal server error.
   *
   * @param exception the exception to be logged
   */
  public static void logInternalServerError(Exception exception) {
    LOGGER.error("ConsultingTypeService API: 500 Internal Server Error: {}", getStackTrace(exception));
  }

  /**
   * Logs a error.
   *
   * @param exception  the exception to be logged
   */
  public static void logError(Exception exception) {
    LOGGER.error("ConsultingTypeService API: {}", getStackTrace(exception));
  }
}
