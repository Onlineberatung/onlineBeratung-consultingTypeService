package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationSettingsService {

  private @NonNull ApplicationSettingsRepository applicationSettingsRepository;

  public ApplicationSettingsEntity getApplicationSettings() {
    var settings = applicationSettingsRepository.findAll();
    assertExactlyOneEntryFound(settings);
    return settings.get(0);
  }

  private void assertExactlyOneEntryFound(List<ApplicationSettingsEntity> settings) {
    assertConditionIsFulfilled(settings.isEmpty(), "Application settings not found in Mongo");
    assertConditionIsFulfilled(settings.size() > 1,
        "Non unique application settings found in Mongo");
  }

  private void assertConditionIsFulfilled(boolean condition, String exceptionMessage) {
    if (condition) {
      throw new IllegalStateException(exceptionMessage);
    }
  }
}
