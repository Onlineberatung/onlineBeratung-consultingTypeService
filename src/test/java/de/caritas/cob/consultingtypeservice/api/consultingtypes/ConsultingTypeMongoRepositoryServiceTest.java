package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ConsultingTypeMongoRepositoryServiceTest {

  @Mock
  private ConsultingTypeRepository consultingTypeRepository;

  @Mock
  private ConsultingTypeConverter consultingTypeConverter;

  @InjectMocks
  ConsultingTypeMongoRepositoryService consultingTypeMongoRepositoryService;


  @Test
  void addConsultingType_Should_Not_AddIfGivenSlugExists() {
    // given
    ConsultingTypeEntity consultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");

    when(consultingTypeRepository.findBySlug("beratung")).thenReturn(Lists.newArrayList(consultingType));
    // when
    consultingTypeMongoRepositoryService.addConsultingType(new ConsultingTypeEntity().withId(2).withTenantId(2).withSlug("beratung"));
    // then
    Mockito.verify(consultingTypeRepository, Mockito.never()).save(consultingType);
  }

  @Test
  void addConsultingType_Should_AddIfGivenSlugAndIdNotExist() {
    // given
    ConsultingTypeEntity consultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");
    when(consultingTypeRepository.findBySlug("beratung1")).thenReturn(Lists.newArrayList());

    // when
    ConsultingTypeEntity newConsultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(2).withTenantId(3)
        .withSlug("beratung1");
    consultingTypeMongoRepositoryService.addConsultingType(newConsultingType);
    // then
    Mockito.verify(consultingTypeRepository).save(newConsultingType);
  }

  @Test
  void addConsultingType_Should_AddIfGivenIdExists() {
    // given
    ConsultingTypeEntity consultingType = new ConsultingTypeEntity();
    consultingType.setId(1);
    consultingType.setSlug("beratung");
    when(consultingTypeRepository.findBySlug("beratung")).thenReturn(Lists.newArrayList(consultingType));
    // when
    consultingTypeMongoRepositoryService.addConsultingType(consultingType);
    // then
    Mockito.verify(consultingTypeRepository, Mockito.never()).save(consultingType);
  }
}