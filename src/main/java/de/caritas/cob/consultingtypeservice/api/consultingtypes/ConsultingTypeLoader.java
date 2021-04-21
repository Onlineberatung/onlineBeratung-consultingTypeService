package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingTypeSettings;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Loader for the consulting type settings from the file system
 */
@Service
@RequiredArgsConstructor
public class ConsultingTypeLoader {

  @Value("${consulting.types.settings.json.path}")
  private String consultingTypeSettingsFilePath;

  private final @NonNull ConsultingTypeSettingsRepository consultingTypeSettingsRepository;
  private final @NonNull ConsultingTypeSettingsValidator consultingTypeSettingsValidator;

  @PostConstruct
  private void init() {
    Stream.of(ArrayUtils.nullToEmpty(determineConsultingTypeSettingConfigurationFiles()))
        .forEach(f -> obtainInitializedConsultingTypeSetting((File) f));
  }

  private void obtainInitializedConsultingTypeSetting(File file) {
    consultingTypeSettingsValidator.validateConsultingTypeSettingsConfigurationJsonFile(file);
    try {
      consultingTypeSettingsRepository.addConsultingTypeSetting(new ObjectMapper().readValue(file, ConsultingTypeSettings.class));
    } catch (IOException ioException) {
      LogService.logError(ioException);
      ConsultingTypeServiceApplication.exitServiceWithErrorStatus();
    }
  }

  private File[] determineConsultingTypeSettingConfigurationFiles() {
    try {
      URL dirUrl = Paths.get(consultingTypeSettingsFilePath).toUri().toURL();
      return new File(dirUrl.toURI())
          .listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
    } catch (URISyntaxException | MalformedURLException exception) {
      LogService.logError(exception);
      ConsultingTypeServiceApplication.exitServiceWithErrorStatus();
    }
    return null;
  }

}
