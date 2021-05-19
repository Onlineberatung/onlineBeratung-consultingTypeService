package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.InvalidPathException;
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

  @PostConstruct
  private void init() {
    Stream.of(ArrayUtils.nullToEmpty(determineConsultingTypeConfigurationFiles()))
        .forEach(f -> obtainInitializedConsultingType((File) f));
  }

  private void obtainInitializedConsultingType(File file) {
    consultingTypeValidator.validateConsultingTypeConfigurationJsonFile(file);
    try {
      consultingTypeRepository.addConsultingType(new ObjectMapper().readValue(file, ConsultingType.class));
    } catch (IOException ioException) {
      LogService.logError(ioException);
      throw new UnexpectedErrorException();
    }
  }

  private File[] determineConsultingTypeConfigurationFiles() {
    try {
      var dirUrl = Paths.get(consultingTypesFilePath).toUri().toURL();
      return new File(dirUrl.toURI())
          .listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
    } catch (URISyntaxException | MalformedURLException | InvalidPathException exception) {
      LogService.logError(exception);
      throw new UnexpectedErrorException();
    }
  }
}
