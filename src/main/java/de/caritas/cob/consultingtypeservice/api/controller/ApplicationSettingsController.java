package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.service.ApplicationSettingsServiceFacade;
import de.caritas.cob.consultingtypeservice.generated.api.controller.SettingsApi;
import io.swagger.annotations.Api;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for consulting type API requests.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "applicationsettings-controller")
@Slf4j
public class ApplicationSettingsController implements SettingsApi {

  private final @NonNull ApplicationSettingsServiceFacade applicationSettingsServiceFacade;

  /**
   * Returns application settings
   *
   * @return {@link ResponseEntity} containing application settings
   */
  @Override
  public ResponseEntity<ApplicationSettingsDTO> getApplicationSettings() {
    var settings = applicationSettingsServiceFacade.getApplicationSettings();
    return new ResponseEntity<>(settings, HttpStatus.OK);
  }
}
