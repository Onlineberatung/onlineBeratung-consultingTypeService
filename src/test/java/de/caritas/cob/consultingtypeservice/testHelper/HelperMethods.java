package de.caritas.cob.consultingtypeservice.testHelper;

import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.FILE_CONSULTING_TYPE_GROUP_RESPONSE_JSON;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.FILE_VALID_COMPLETE_CONSULTING_TYPE;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.FILE_VALID_COMPLETE_CONSULTING_TYPE_2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeGroupResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class HelperMethods {

  public static ConsultingType getConsultingType() throws IOException {
    return loadConsultingType(FILE_VALID_COMPLETE_CONSULTING_TYPE);
  }

  public static List<ConsultingType> getConsultingTypeList() throws IOException {
    return Arrays.asList(
        loadConsultingType(FILE_VALID_COMPLETE_CONSULTING_TYPE),
        loadConsultingType(FILE_VALID_COMPLETE_CONSULTING_TYPE_2));
  }

  public static String getConsultingTypeSettingsAsJsonString() throws IOException {
    return new String(Files.readAllBytes(Paths.get(FILE_VALID_COMPLETE_CONSULTING_TYPE)));
  }

  private static ConsultingType loadConsultingType(String filename) throws IOException {
    URL url = new URL("file:" + filename);
    return new ObjectMapper().readValue(url, ConsultingType.class);
  }

  public static List<ConsultingTypeGroupResponseDTO> loadConsultingTypeGroupResponse()
      throws IOException {
    URL url = new URL("file:" + FILE_CONSULTING_TYPE_GROUP_RESPONSE_JSON);
    return new ObjectMapper().readValue(url, new TypeReference<>() {});
  }

  public static String loadConsultingTypeGroupResponseAsJsonString() throws IOException {
    return new String(Files.readAllBytes(Paths.get(FILE_CONSULTING_TYPE_GROUP_RESPONSE_JSON)));
  }
}
