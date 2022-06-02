package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import java.lang.reflect.Field;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.ReflectionUtils;

@ExtendWith(MockitoExtension.class)
class ConsultingTypeMongoRepositoryServiceTest {

  @Mock
  private ConsultingTypeRepository consultingTypeRepository;

  @Mock
  private ConsultingTypeConverter consultingTypeConverter;

  @InjectMocks
  ConsultingTypeMongoRepositoryService consultingTypeMongoRepositoryService;

  @AfterEach
  public void cleanUp() {
    setMultitenancyEnabled(false);
  }

  @Test
  void addConsultingType_Should_Not_AddIfGivenSlugExistsAndMultitenancyDisabled() {
    // given
    ConsultingTypeEntity consultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");

    when(consultingTypeRepository.findBySlug("beratung")).thenReturn(Lists.newArrayList(consultingType));
    // when
    consultingTypeMongoRepositoryService.addConsultingType(new ConsultingTypeEntity().withId(2).withTenantId(2).withSlug("beratung"));
    // then
    Mockito.verify(consultingTypeRepository, Mockito.never()).save(consultingType);
  }

  @Test
  void addConsultingType_Should_Not_AddIfGivenSlugExistsAndMultitenancyEnabledAndTenantIdMatches() {
    // given
    setMultitenancyEnabled(true);
    ConsultingTypeEntity consultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");
    when(consultingTypeRepository.findBySlug("beratung")).thenReturn(Lists.newArrayList(consultingType));

    // when
    consultingTypeMongoRepositoryService.addConsultingType(new ConsultingTypeEntity().withId(2).withTenantId(2).withSlug("beratung"));
    // then
    Mockito.verify(consultingTypeRepository, Mockito.never()).save(consultingType);
  }

  @Test
  void addConsultingType_Should_AddIfGivenSlugExistsAndMultitenancyEnabledAndTenantIdDoesNotMatch() {
    // given
    setMultitenancyEnabled(true);
    ConsultingTypeEntity consultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");
    when(consultingTypeRepository.findBySlug("beratung")).thenReturn(Lists.newArrayList(consultingType));

    // when
    ConsultingTypeEntity newConsultingType = (ConsultingTypeEntity) new ConsultingTypeEntity().withId(2).withTenantId(3)
        .withSlug("beratung");
    consultingTypeMongoRepositoryService.addConsultingType(newConsultingType);
    // then
    Mockito.verify(consultingTypeRepository).save(newConsultingType);
  }

  private void setMultitenancyEnabled(Boolean value) {
    Field multitenancyField = ReflectionUtils
        .findField(ConsultingTypeMongoRepositoryService.class, "multitenancy", boolean.class);
    assert multitenancyField != null;
    multitenancyField.setAccessible(true);
    ReflectionUtils.setField(multitenancyField, consultingTypeMongoRepositoryService,
        value);
  }

  @Test
  void addConsultingType_Should_AddIfGivenSlugExistsAndMultitenancyIsEnabled() {
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