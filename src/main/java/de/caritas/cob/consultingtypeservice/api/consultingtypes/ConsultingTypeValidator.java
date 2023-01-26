package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Validator for consulting type settings files against the json schema for consulting type
 * settings.
 */
@Service
public class ConsultingTypeValidator {

  @Value("${consulting.types.json.schema.file}")
  private String consultingTypeJsonSchemaFile;

  /**
   * Validate a consulting type settings file.
   *
   * @param consultingTypeJsonFile the {@link File} to be validated
   */
  public void validateConsultingTypeConfigurationJsonFile(File consultingTypeJsonFile) {

    try {
      var consultingTypeJsonObject =
          new JSONObject(new JSONTokener(new FileInputStream(consultingTypeJsonFile)));
      var schema = buildSchema();
      schema.validate(consultingTypeJsonObject);
    } catch (JSONException | FileNotFoundException exception) {
      LogService.logError(exception);
      throw new UnexpectedErrorException();
    } catch (ValidationException validationException) {
      LogService.logJsonSchemaValidationException(
          "Found errors during validation of consulting type. Please fix and restart.",
          consultingTypeJsonFile.getAbsolutePath(),
          validationException);
      throw new UnexpectedErrorException();
    }
  }

  private Schema buildSchema() {
    var consultingTypeJsonSchema =
        new JSONObject(
            new JSONTokener(
                Objects.requireNonNull(
                    ConsultingTypeValidator.class.getResourceAsStream(
                        consultingTypeJsonSchemaFile))));
    return SchemaLoader.builder()
        .useDefaults(true)
        .schemaJson(consultingTypeJsonSchema)
        .draftV7Support()
        .build()
        .load()
        .build();
  }
}
