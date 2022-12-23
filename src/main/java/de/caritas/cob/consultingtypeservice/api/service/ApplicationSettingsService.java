package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationSettingsService {

  private @NonNull ApplicationSettingsRepository applicationSettingsRepository;

  public Optional<ApplicationSettingsEntity> getApplicationSettings() {
    var settings = applicationSettingsRepository.findAll();
    assertExactlyOneEntryFound(settings);
    return settings.isEmpty() ? Optional.empty() : Optional.of(settings.get(0));
  }

  public void saveApplicationSettings(ApplicationSettingsEntity entity) {
    applicationSettingsRepository.deleteAll();
    applicationSettingsRepository.save(entity);
  }

  private void assertExactlyOneEntryFound(List<ApplicationSettingsEntity> settings) {
    if (settings.size() > 1) {
      throw new IllegalStateException("Non unique application settings found in Mongo");
    }
  }
}
