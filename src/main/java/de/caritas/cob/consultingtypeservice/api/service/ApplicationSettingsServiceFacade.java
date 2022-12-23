package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import java.util.Optional;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsPatchDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.LegalContentChangesBySingleTenantAdminsAllowed;
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

  public void patchApplicationSettings(ApplicationSettingsPatchDTO settingsPatchDTO) {
    var applicationSettings = applicationSettingsService.getApplicationSettings();
    if (applicationSettings.isPresent()) {
      ApplicationSettingsEntity entity = applicationSettings.get();
      convertPatchedValues(settingsPatchDTO, entity);
      applicationSettingsService.saveApplicationSettings(entity);
    }
  }

  private void convertPatchedValues(ApplicationSettingsPatchDTO settingsPatchDTO, ApplicationSettingsEntity entity) {
    entity.getLegalContentChangesBySingleTenantAdminsAllowed().setValue(settingsPatchDTO.getLegalContentChangesBySingleTenantAdminsAllowed().getValue());
  }
}
