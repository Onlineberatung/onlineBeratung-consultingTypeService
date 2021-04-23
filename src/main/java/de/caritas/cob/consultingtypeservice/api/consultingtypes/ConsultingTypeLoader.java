package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
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
 * Loader for the consulting types from the file system
 */
@Service
@RequiredArgsConstructor
public class ConsultingTypeLoader {

  @Value("${consulting.types.json.path}")
  private String consultingTypesFilePath;

  private final @NonNull ConsultingTypeRepository consultingTypeRepository;
  private final @NonNull ConsultingTypeValidator consultingTypeValidator;
  private static final File[] NO_FILES = {};

  @PostConstruct
  private void init() {
    Stream.of(ArrayUtils.nullToEmpty(determineConsultingTypeConfigurationFile()))
        .forEach(f -> obtainInitializedConsultingType((File) f));
  }

  private void obtainInitializedConsultingType(File file) {
    consultingTypeValidator.validateConsultingTypeConfigurationJsonFile(file);
    try {
      consultingTypeRepository.addConsultingType(new ObjectMapper().readValue(file, ConsultingType.class));
    } catch (IOException ioException) {
      LogService.logError(ioException);
      ConsultingTypeServiceApplication.exitService();
    }
  }

  private File[] determineConsultingTypeConfigurationFile() {
    try {
      URL dirUrl = Paths.get(consultingTypesFilePath).toUri().toURL();
      return new File(dirUrl.toURI())
          .listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
    } catch (URISyntaxException | MalformedURLException exception) {
      LogService.logError(exception);
      ConsultingTypeServiceApplication.exitService();
    }
    return NO_FILES;
  }

}
