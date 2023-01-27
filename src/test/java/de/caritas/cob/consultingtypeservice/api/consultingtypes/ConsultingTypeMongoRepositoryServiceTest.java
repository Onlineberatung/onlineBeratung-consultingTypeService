package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultingTypeMongoRepositoryServiceTest {

  @Mock private ConsultingTypeRepository consultingTypeRepository;

  @Mock private ConsultingTypeConverter consultingTypeConverter;

  @InjectMocks ConsultingTypeMongoRepositoryService consultingTypeMongoRepositoryService;

  @AfterEach
  public void tearDown() {
    setField(consultingTypeMongoRepositoryService, "multitenancyWithSingleDomainEnabled", false);
  }

  @Test
  void addConsultingType_Should_notAdd_When_givenSlugExists() {
    // given
    ConsultingTypeEntity consultingType =
        (ConsultingTypeEntity)
            new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");

    when(consultingTypeRepository.findBySlug("beratung"))
        .thenReturn(Lists.newArrayList(consultingType));
    // when
    consultingTypeMongoRepositoryService.addConsultingType(
        new ConsultingTypeEntity().withId(2).withTenantId(2).withSlug("beratung"));
    // then
    verify(consultingTypeRepository, Mockito.never()).save(consultingType);
  }

  @Test
  void addConsultingType_Should_Add_When_GivenSlugAndIdNotExist() {
    // given
    var consultingType =
        (ConsultingTypeEntity)
            new ConsultingTypeEntity().withId(1).withTenantId(2).withSlug("beratung");
    when(consultingTypeRepository.findBySlug("beratung1")).thenReturn(Lists.newArrayList());
    when(consultingTypeRepository.save(any())).thenReturn(consultingType);
    // when
    ConsultingTypeEntity newConsultingType =
        (ConsultingTypeEntity)
            new ConsultingTypeEntity().withId(2).withTenantId(3).withSlug("beratung1");
    consultingTypeMongoRepositoryService.addConsultingType(newConsultingType);
    // then
    verify(consultingTypeRepository).save(newConsultingType);
  }

  @Test
  void addConsultingType_Should_Add_When_GivenIdExists() {
    // given
    ConsultingTypeEntity consultingType = new ConsultingTypeEntity();
    consultingType.setId(1);
    consultingType.setSlug("beratung");
    when(consultingTypeRepository.findBySlug("beratung"))
        .thenReturn(Lists.newArrayList(consultingType));
    // when
    consultingTypeMongoRepositoryService.addConsultingType(consultingType);
    // then
    verify(consultingTypeRepository, Mockito.never()).save(consultingType);
  }

  @Test
  void
      addConsultingType_Should_Add_When_GivenIdNotExistsAndSlugExistsAndSingleDomainMultitenancy() {
    // given
    ConsultingTypeEntity consultingType = new ConsultingTypeEntity();
    consultingType.setId(2);
    consultingType.setSlug("beratung");
    setField(consultingTypeMongoRepositoryService, "multitenancyWithSingleDomainEnabled", true);
    when(consultingTypeRepository.save(any())).thenReturn(consultingType);

    // when
    consultingTypeMongoRepositoryService.addConsultingType(consultingType);
    // then
    verify(consultingTypeRepository).save(consultingType);
  }

  @Test
  void
      addConsultingType_Should_NotAdd_When_GivenIdNotExistsAndSlugExistsAndNotSingleDomainMultitenancy() {
    // given
    ConsultingTypeEntity consultingType = new ConsultingTypeEntity();
    consultingType.setId(2);
    consultingType.setSlug("beratung");
    setField(consultingTypeMongoRepositoryService, "multitenancyWithSingleDomainEnabled", false);

    when(consultingTypeRepository.findBySlug("beratung"))
        .thenReturn(Lists.newArrayList(consultingType));
    // when
    consultingTypeMongoRepositoryService.addConsultingType(consultingType);
    // then
    verify(consultingTypeRepository, never()).save(consultingType);
  }
}
