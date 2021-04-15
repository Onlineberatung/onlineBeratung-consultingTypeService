package de.caritas.cob.consultingtypeservice.api.manager.consultingtype;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.exception.MissingConsultingTypeException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsultingTypeManager {

  @Value("${consulting.types.settings.json.path}")
  private String consultingTypesSettingsJsonPath;

  private Map<Integer, ConsultingTypeSettings> consultingTypeSettingsMap;

  @PostConstruct
  private void init() throws IOException {

    LogService.logInfo("Start initializing consulting type settings...");

    ObjectMapper mapper = new ObjectMapper();
    TypeReference<ConsultingTypeSettings> typeReference =
        new TypeReference<ConsultingTypeSettings>() {};

    consultingTypeSettingsMap = new HashMap<>();

    for (ConsultingType consultingType : ConsultingType.values()) {
      InputStream inputStream =
          TypeReference.class.getResourceAsStream(getJsonFileNameWithPath(consultingType));
      ConsultingTypeSettings consultingTypeSettings = mapper.readValue(inputStream, typeReference);
      consultingTypeSettings.setConsultingType(consultingType);
      consultingTypeSettingsMap.put(consultingType.getValue(), consultingTypeSettings);
    }

    LogService.logInfo("Finished initializing consulting type settings...");

  }

  public ConsultingTypeSettings getConsultantTypeSettings(ConsultingType consultingType)
      throws MissingConsultingTypeException {

    if (consultingTypeSettingsMap.containsKey(consultingType.getValue())
        && consultingTypeSettingsMap.get(consultingType.getValue()) != null) {
      return consultingTypeSettingsMap.get(consultingType.getValue());
    } else {
      throw new MissingConsultingTypeException(
          String.format("No settings for consulting type %s found.", consultingType.name()));
    }

  }

  private String getJsonFileNameWithPath(ConsultingType consultingType) {
    return consultingTypesSettingsJsonPath + "/" + consultingType.name().toLowerCase() + ".json";
  }

}
