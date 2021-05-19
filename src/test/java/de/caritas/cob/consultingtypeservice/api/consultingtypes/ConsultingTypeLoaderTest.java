package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.BROKEN_FILE_PATH;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.SRC_TEST_RESOURCES_BROKEN_CONSULTING_TYPE_SETTINGS;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.SRC_TEST_RESOURCES_CONSULTING_TYPE_SETTINGS;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.util.ReflectionUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeLoaderTest {

  private final static String INIT_METHOD_NAME = "init";
  private final static String CONSULTING_TYPES_FILE_PATH_NAME = "consultingTypesFilePath";

  @InjectMocks
  ConsultingTypeLoader consultingTypeLoader;
  @Mock
  ConsultingTypeRepository consultingTypeRepository;
  @Mock
  ConsultingTypeValidator consultingTypeValidator;
  @Mock
  private Logger logger;

  @Before
  public void setup() {
    setInternalState(LogService.class, "LOGGER", logger);
  }

  @Test
  public void test_Should_Fail_WhenMethodInitDoesNotHavePostConstructAnnotation()
      throws NoSuchMethodException, SecurityException {

    ConsultingTypeLoader consultingTypeLoader = new ConsultingTypeLoader(consultingTypeRepository,
        consultingTypeValidator);

    PostConstruct annotation = getInitMethodFromConsultingTypeLoader()
        .getAnnotation(PostConstruct.class);

    assertNotNull(annotation);
  }

  @Test
  public void init_Should_ThrowUnexpectedErrorException_WhenInvalidConsultingTypeSettingsPath()
      throws NoSuchMethodException {

    setConsultingTypesFilePath(BROKEN_FILE_PATH);
    Method initMethod = getInitMethodFromConsultingTypeLoader();
    assertThrows(UnexpectedErrorException.class, () ->
        ReflectionUtils.invokeMethod(initMethod, consultingTypeLoader)
    );

    verify(logger, times(1))
        .error(Mockito.anyString(), Mockito.anyString(), Mockito.any());
  }

  @Test
  public void init_Should_ThrowUnexpectedErrorException_WhenBrokenConsultingTypeSettings()
      throws NoSuchMethodException {

    setConsultingTypesFilePath(SRC_TEST_RESOURCES_BROKEN_CONSULTING_TYPE_SETTINGS);
    Method initMethod = getInitMethodFromConsultingTypeLoader();
    assertThrows(UnexpectedErrorException.class, () ->
        ReflectionUtils.invokeMethod(initMethod, consultingTypeLoader)
    );

    verify(logger, times(1))
        .error(Mockito.anyString(), Mockito.anyString(), Mockito.any());
  }

  @Test
  public void init_Should_AddConsultingTypesToRepository() throws NoSuchMethodException {

    setConsultingTypesFilePath(SRC_TEST_RESOURCES_CONSULTING_TYPE_SETTINGS);
    ReflectionUtils.invokeMethod(getInitMethodFromConsultingTypeLoader(), consultingTypeLoader);
    verify(consultingTypeRepository, times(2)).addConsultingType(Mockito.any(ConsultingType.class));
  }

  @Test
  public void init_Should_ValidateConsultingTypeSettings() throws NoSuchMethodException {

    setConsultingTypesFilePath(SRC_TEST_RESOURCES_CONSULTING_TYPE_SETTINGS);
    ReflectionUtils.invokeMethod(getInitMethodFromConsultingTypeLoader(), consultingTypeLoader);
    verify(consultingTypeValidator, times(2))
        .validateConsultingTypeConfigurationJsonFile(Mockito.any(
            File.class));
  }

  private Method getInitMethodFromConsultingTypeLoader() throws NoSuchMethodException {
    Class<? extends ConsultingTypeLoader> classToTest = consultingTypeLoader.getClass();
    Method methodToTest = classToTest.getDeclaredMethod(INIT_METHOD_NAME);
    methodToTest.setAccessible(true);
    return methodToTest;
  }

  private void setConsultingTypesFilePath(String consultingTypesFilePath) {
    Field fieldConsultingTypesFilePath = ReflectionUtils
        .findField(ConsultingTypeLoader.class, CONSULTING_TYPES_FILE_PATH_NAME, String.class);
    assert fieldConsultingTypesFilePath != null;
    fieldConsultingTypesFilePath.setAccessible(true);
    ReflectionUtils.setField(fieldConsultingTypesFilePath, consultingTypeLoader,
        consultingTypesFilePath);
  }

}
