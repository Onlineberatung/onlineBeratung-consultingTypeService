package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationSettingsServiceFacade {

  private final @NonNull ApplicationSettingsService applicationSettingsService;
  private final @NonNull ApplicationSettingsConverter applicationSettingsConverter;

  public Optional<ApplicationSettingsDTO> getApplicationSettings() {
    var applicationSettings = applicationSettingsService.getApplicationSettings();
    return applicationSettings.isPresent()
        ? Optional.of(applicationSettingsConverter.toDTO(applicationSettings.get()))
        : Optional.empty();
  }
}
