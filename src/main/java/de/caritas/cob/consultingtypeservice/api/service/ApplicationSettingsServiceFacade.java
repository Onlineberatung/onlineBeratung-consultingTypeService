package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsPatchDTO;
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

  public void patchApplicationSettings(ApplicationSettingsPatchDTO settingsPatchDTO) {
    var applicationSettings = applicationSettingsService.getApplicationSettings();
    if (applicationSettings.isPresent()) {
      ApplicationSettingsEntity entity = applicationSettings.get();
      convertPatchedValues(settingsPatchDTO, entity);
      applicationSettingsService.saveApplicationSettings(entity);
    }
  }

  private void convertPatchedValues(
      ApplicationSettingsPatchDTO settingsPatchDTO, ApplicationSettingsEntity entity) {
    if (settingsPatchDTO.getLegalContentChangesBySingleTenantAdminsAllowed() != null) {
      entity
          .getLegalContentChangesBySingleTenantAdminsAllowed()
          .setValue(settingsPatchDTO.getLegalContentChangesBySingleTenantAdminsAllowed());
    }
    if (settingsPatchDTO.getMainTenantSubdomainForSingleDomainMultitenancy() != null) {
      entity
          .getMainTenantSubdomainForSingleDomainMultitenancy()
          .setValue(settingsPatchDTO.getMainTenantSubdomainForSingleDomainMultitenancy());
    }
  }
}
