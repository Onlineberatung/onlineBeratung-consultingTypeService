package de.caritas.cob.consultingtypeservice.testHelper;

import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.FILE_VALID_COMPLETE_CONSULTING_TYPE;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelperMethods {


  public static ConsultingType getConsultingType() throws IOException {
    URL url = new URL("file:" + FILE_VALID_COMPLETE_CONSULTING_TYPE);
    return new ObjectMapper().readValue(url, ConsultingType.class);
  }

  public static String getConsultingTypeSettingsAsJsonString() throws IOException {
    return new String(
        Files.readAllBytes(Paths.get(FILE_VALID_COMPLETE_CONSULTING_TYPE)));
  }

}
