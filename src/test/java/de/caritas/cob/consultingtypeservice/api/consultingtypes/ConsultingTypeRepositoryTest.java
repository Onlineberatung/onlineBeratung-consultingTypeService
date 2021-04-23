package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.reflect.Whitebox.setInternalState;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeRepositoryTest {

  ConsultingTypeRepository consultingTypeRepository;

  @Mock
  private Logger logger;

  @Before
  public void setup() {
    setInternalState(LogService.class, "LOGGER", logger);
  }

  @Before
  public void init() {
    consultingTypeRepository = new ConsultingTypeRepository();
  }

  @Test
  public void getListOfConsultingTypes_Should_Return_ListOfAddedConsultingTypes() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType1 = easyRandom.nextObject(ConsultingType.class);
    ConsultingType consultingType2 = easyRandom.nextObject(ConsultingType.class);
    ConsultingType consultingType3 = easyRandom.nextObject(ConsultingType.class);
    consultingType2.setId(consultingType1.getId() + 1);
    consultingType3.setId(consultingType2.getId() + 1);

    consultingTypeRepository.addConsultingType(consultingType1);
    consultingTypeRepository.addConsultingType(consultingType2);
    consultingTypeRepository.addConsultingType(consultingType3);

    List<ConsultingType> result = consultingTypeRepository.getListOfConsultingTypes();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertThat(result, containsInAnyOrder(consultingType1, consultingType2, consultingType3));

  }

  @Test
  public void getConsultingTypeById_Should_Return_CorrectConsultingTypeViaId() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType = easyRandom.nextObject(ConsultingType.class);

    consultingTypeRepository.addConsultingType(consultingType);

    assertEquals(consultingType,
        consultingTypeRepository.getConsultingTypeById(consultingType.getId()));
  }

  @Test
  public void getConsultingTypeById_Should_ThrowNotFoundException_WhenConsultingTypeWithGivenIdIsNotFound() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType = easyRandom.nextObject(ConsultingType.class);

    consultingTypeRepository.addConsultingType(consultingType);

    Integer id = consultingType.getId() + 1;
    assertThrows(NotFoundException.class, () ->
        consultingTypeRepository.getConsultingTypeById(id)
    );

  }

  @Test
  public void getConsultingTypeBySlug() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType = easyRandom.nextObject(ConsultingType.class);

    consultingTypeRepository.addConsultingType(consultingType);

    assertEquals(consultingType,
        consultingTypeRepository.getConsultingTypeBySlug(consultingType.getSlug()));
  }

  @Test
  public void getConsultingTypeBySlug_Should_ThrowNotFoundException_WhenConsultingTypeWithGivenIdIsNotFound() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType = easyRandom.nextObject(ConsultingType.class);

    consultingTypeRepository.addConsultingType(consultingType);

    String slug = consultingType.getSlug() + "x";
    assertThrows(NotFoundException.class, () ->
        consultingTypeRepository.getConsultingTypeBySlug(slug)
    );
  }

  @Test
  public void addConsultingType_Should_AddConsultingTypeToRepository() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType = easyRandom.nextObject(ConsultingType.class);

    consultingTypeRepository.addConsultingType(consultingType);

    List<ConsultingType> consultingTypeList = consultingTypeRepository.getListOfConsultingTypes();
    assertNotNull(consultingTypeList.get(0));
    assertEquals(consultingType, consultingTypeList.get(0));

  }

  @Test
  public void addConsultingType_Should_ThrowRuntimeException_WhenIdIsNotUnique() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType1 = easyRandom.nextObject(ConsultingType.class);
    ConsultingType consultingType2 = easyRandom.nextObject(ConsultingType.class);
    consultingType2.setId(consultingType1.getId());

    consultingTypeRepository.addConsultingType(consultingType1);

    assertThrows(RuntimeException.class, () ->
        consultingTypeRepository.addConsultingType(consultingType2)
    );
    verify(logger, times(1)).error(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
  }

  @Test
  public void addConsultingType_Should_ThrowRuntimeException_WhenSlugIsNotUnique() {

    EasyRandom easyRandom = new EasyRandom();
    ConsultingType consultingType1 = easyRandom.nextObject(ConsultingType.class);
    ConsultingType consultingType2 = easyRandom.nextObject(ConsultingType.class);
    consultingType2.setId(consultingType1.getId() + 1);
    consultingType2.setSlug(consultingType1.getSlug());

    consultingTypeRepository.addConsultingType(consultingType1);

    assertThrows(RuntimeException.class, () ->
        consultingTypeRepository.addConsultingType(consultingType2)
    );
    verify(logger, times(1)).error(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
  }
}
