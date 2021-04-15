package de.caritas.cob.consultingtypeservice.api.manager.consultingtype;

import static de.caritas.cob.consultingtypeservice.testHelper.FieldConstants.FIELD_NAME_CONSULTING_TYPES_SETTINGS_JSON_PATH_VALUE;
import static de.caritas.cob.consultingtypeservice.testHelper.FieldConstants.FIELD_NAME_CONSULTING_TYPE_SETTINGS_MAP;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_MAP;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_MAP_WITH_MISSING_CONSULTING_TYPE_SETTINGS_FOR_U25;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_SUCHT;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.CONSULTING_TYPE_SETTINGS_U25;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.CONSULTING_TYPE_SUCHT;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.CONSULTING_TYPE_U25;
import static de.caritas.cob.consultingtypeservice.testHelper.TestConstants.INIT_GROUP_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.exception.MissingConsultingTypeException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import javax.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeManagerTest {

  @InjectMocks
  ConsultingTypeManager consultingTypeManager;

  @Test
  public void test_Should_Fail_WhenMethodInitDoesNotHavePostConstructAnnotation()
      throws NoSuchMethodException, SecurityException {

    Class<? extends ConsultingTypeManager> classToTest = consultingTypeManager.getClass();

    Method methodToTest = classToTest.getDeclaredMethod(INIT_GROUP_NAME);
    methodToTest.setAccessible(true);
    PostConstruct annotation = methodToTest.getAnnotation(PostConstruct.class);

    assertNotNull(annotation);
  }

  @Test
  public void getConsultantTypeSettings_Should_ReturnConsultantTypeSettingsForConsultingType()
      throws NoSuchFieldException, SecurityException, MissingConsultingTypeException {

    FieldSetter.setField(consultingTypeManager,
        consultingTypeManager.getClass().getDeclaredField(FIELD_NAME_CONSULTING_TYPE_SETTINGS_MAP),
        CONSULTING_TYPE_SETTINGS_MAP);

    ConsultingTypeSettings result =
        consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_SUCHT);
    assertEquals(CONSULTING_TYPE_SETTINGS_SUCHT, result);

    result = consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_U25);
    assertEquals(CONSULTING_TYPE_SETTINGS_U25, result);
  }

  @Test
  public void getConsultantTypeSettings_Should_ThrowMissingConsultingTypeException_WhenSettingsForGivenConsultingTypeAreMissing()
      throws NoSuchFieldException, SecurityException {

    FieldSetter.setField(consultingTypeManager,
        consultingTypeManager.getClass().getDeclaredField(FIELD_NAME_CONSULTING_TYPE_SETTINGS_MAP),
        CONSULTING_TYPE_SETTINGS_MAP_WITH_MISSING_CONSULTING_TYPE_SETTINGS_FOR_U25);

    try {
      consultingTypeManager.getConsultantTypeSettings(CONSULTING_TYPE_U25);
      fail("Expected exception: MissingConsultingTypeException");
    } catch (MissingConsultingTypeException missingConsultingTypeException) {
      assertTrue("Excepted MissingConsultingTypeException thrown", true);
    }

  }

  protected static ConsultingTypeSettings loadConsultingTypeSettings(ConsultingType consultingType)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    TypeReference<ConsultingTypeSettings> typeReference =
        new TypeReference<ConsultingTypeSettings>() {};
    InputStream inputStream = null;
    inputStream =
        TypeReference.class.getResourceAsStream(FIELD_NAME_CONSULTING_TYPES_SETTINGS_JSON_PATH_VALUE
            + "/" + consultingType.name().toLowerCase() + ".json");
    return mapper.readValue(inputStream, typeReference);
  }

}
