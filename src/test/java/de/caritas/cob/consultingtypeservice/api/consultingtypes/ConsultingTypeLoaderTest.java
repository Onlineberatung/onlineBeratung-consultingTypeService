package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import javax.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeLoaderTest {

  private final static String INIT_METHOD_NAME = "init";

  @Mock
  ConsultingTypeRepository consultingTypeRepository;
  @Mock
  ConsultingTypeValidator consultingTypeValidator;

  @Test
  public void test_Should_Fail_WhenMethodInitDoesNotHavePostConstructAnnotation()
      throws NoSuchMethodException, SecurityException {

    ConsultingTypeLoader consultingTypeLoader = new ConsultingTypeLoader(consultingTypeRepository, consultingTypeValidator);
    Class<? extends ConsultingTypeLoader> classToTest = consultingTypeLoader.getClass();

    Method methodToTest = classToTest.getDeclaredMethod(INIT_METHOD_NAME);
    methodToTest.setAccessible(true);
    PostConstruct annotation = methodToTest.getAnnotation(PostConstruct.class);

    assertNotNull(annotation);
  }

}
