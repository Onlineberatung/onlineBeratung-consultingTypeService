package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import java.io.File;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeValidatorTest {

  private static final String FIELD_NAME_CONSULTING_TYPE_JSON_SCHEMA_FILE = "consultingTypeJsonSchemaFile";
  private static final String FILE_CONSULTING_TYPE_JSON_SCHEMA = "/schemas/consulting-type.json";
  private static final String FILE_INVALID_CONSULTING_TYPE = "/invalid-consulting-type-settings/invalid-consulting-type.json";

  private ConsultingTypeValidator consultingTypeValidator;

  @Mock
  private Logger logger;

  @Before
  public void setup() {
    setInternalState(LogService.class, "LOGGER", logger);
    consultingTypeValidator = new ConsultingTypeValidator();
    setField(consultingTypeValidator, FIELD_NAME_CONSULTING_TYPE_JSON_SCHEMA_FILE,
        FILE_CONSULTING_TYPE_JSON_SCHEMA);
  }

  @Test
  public void validateConsultingTypeConfigurationJsonFile_Should_ThrowValidationException_WhenJsonViolatesSchema() {

    File invalidConsultingSettingsFile = new File(
        Objects.requireNonNull(
            ConsultingTypeValidatorTest.class.getResource(FILE_INVALID_CONSULTING_TYPE)).getFile());

    assertThrows(UnexpectedErrorException.class, () ->
        consultingTypeValidator
            .validateConsultingTypeConfigurationJsonFile(invalidConsultingSettingsFile)
    );
    verify(logger, times(1))
        .error(Mockito.anyString(), Mockito.anyString(), Mockito.any());

  }


}
