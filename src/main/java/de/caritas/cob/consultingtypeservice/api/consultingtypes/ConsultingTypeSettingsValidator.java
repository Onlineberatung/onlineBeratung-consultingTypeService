package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Validator for consulting type settings  files against the json schema for consulting type settings.
 */
@Service
public class ConsultingTypeSettingsValidator {

  @Value("${consulting.types.settings.json.schema.file}")
  private String consultingTypeSettingsJsonSchemaFile;

  /**
   * Validate a consulting type settings file.
   * @param consultingTypeSettingsJsonFile the {@link File} to be validated
   */
  public void validateConsultingTypeSettingsConfigurationJsonFile(
      File consultingTypeSettingsJsonFile) {

    try {
      JSONObject consultingTypeSettingsjsonObject = new JSONObject(
          new JSONTokener(new FileInputStream(consultingTypeSettingsJsonFile)));
      Schema schema = buildSchema();
      schema.validate(consultingTypeSettingsjsonObject);
    } catch (JSONException | FileNotFoundException exception) {
      LogService.logError(exception);
      ConsultingTypeServiceApplication.exitServiceWithErrorStatus();
    } catch (ValidationException validationException) {
      LogService.logJsonSchemaValidationException(
          "Found errors during validation of consulting type settings. Please fix and restart.",
          consultingTypeSettingsJsonFile.getAbsolutePath(),
          validationException);
      ConsultingTypeServiceApplication.exitServiceWithErrorStatus();
    }
  }

  private Schema buildSchema() {
    JSONObject consultingTypeSettingsJsonSchema = new JSONObject(new JSONTokener(
        Objects.requireNonNull(ConsultingTypeSettingsValidator.class
            .getResourceAsStream(consultingTypeSettingsJsonSchemaFile))));
    return SchemaLoader.builder()
        .useDefaults(true)
        .schemaJson(consultingTypeSettingsJsonSchema)
        .draftV7Support()
        .build()
        .load()
        .build();
  }

}
