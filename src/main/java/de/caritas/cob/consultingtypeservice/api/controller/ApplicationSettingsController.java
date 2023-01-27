package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsPatchDTO;
import de.caritas.cob.consultingtypeservice.api.service.ApplicationSettingsServiceFacade;
import de.caritas.cob.consultingtypeservice.generated.api.controller.SettingsApi;
import de.caritas.cob.consultingtypeservice.generated.api.controller.SettingsadminApi;
import io.swagger.annotations.Api;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

/** Controller for consulting type API requests. */
@RestController
@RequiredArgsConstructor
@Api(tags = "applicationsettings-controller")
@Slf4j
public class ApplicationSettingsController implements SettingsApi, SettingsadminApi {

  private final @NonNull ApplicationSettingsServiceFacade applicationSettingsServiceFacade;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return SettingsApi.super.getRequest();
  }

  /**
   * Returns application settings
   *
   * @return {@link ResponseEntity} containing application settings
   */
  @Override
  public ResponseEntity<ApplicationSettingsDTO> getApplicationSettings() {
    var settings = applicationSettingsServiceFacade.getApplicationSettings();
    return settings.isPresent()
        ? new ResponseEntity<>(settings.get(), HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  @PreAuthorize("hasAuthority('AUTHORIZATION_PATCH_APPLICATION_SETTINGS')")
  public ResponseEntity<ApplicationSettingsDTO> patchApplicationSettings(
      ApplicationSettingsPatchDTO settingsPatchDTO) {
    applicationSettingsServiceFacade.patchApplicationSettings(settingsPatchDTO);
    var settings = applicationSettingsServiceFacade.getApplicationSettings();
    return settings.isPresent()
        ? new ResponseEntity<>(settings.get(), HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
