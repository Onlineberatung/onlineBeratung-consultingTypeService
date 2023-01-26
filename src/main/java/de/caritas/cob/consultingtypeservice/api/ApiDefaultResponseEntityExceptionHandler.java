package de.caritas.cob.consultingtypeservice.api;

import de.caritas.cob.consultingtypeservice.api.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiDefaultResponseEntityExceptionHandler {

  private static final MultiValueMap<String, String> EMPTY_HEADERS = new LinkedMultiValueMap<>();

  /**
   * "Catch all" respectively fallback for all controller error messages that are not specifically
   * retained by {@link ApiResponseEntityExceptionHandler}. For the caller side does not need to
   * know the exact error stack trace, this method catches the trace and logs it.
   *
   * @param ex the thrown {@link RuntimeException}
   * @param request the {@link WebRequest}
   * @return a {@link ResponseEntity}
   */
  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleInternal(
      final RuntimeException ex, final WebRequest request) {
    LogService.logInternalServerError(ex);
    return new ResponseEntity<>(EMPTY_HEADERS, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<Object> handleInternal(
      final AccessDeniedException ex, final WebRequest request) {
    LogService.logWarning(ex);
    return new ResponseEntity<>(EMPTY_HEADERS, HttpStatus.FORBIDDEN);
  }
}
