package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultingTypeRepositoryServiceTest {

  @Mock private ConsultingTypeRepository consultingTypeRepository;

  @Mock private ConsultingTypeConverter consultingTypeConverter;

  @InjectMocks ConsultingTypeMongoRepositoryService consultingTypeMongoRepositoryService;

  @AfterEach
  public void cleanUp() {
    TenantContext.clear();
  }

  @Test
  void getListOfConsultingTypes_Should_returnNotFilteredConsultingTypes() {
    // given
    consultingTypeMongoRepositoryService.getListOfConsultingTypes();
    // then
    verify(consultingTypeRepository).findAll();
    verify(consultingTypeConverter).convertList(Mockito.anyList());
  }

  @Test
  void getConsultingTypeById_Should_getConsultingTypeById() {
    // given
    Mockito.when(consultingTypeRepository.findByConsultingTypeId(1))
        .thenReturn(new ConsultingTypeEntity());
    // when
    consultingTypeMongoRepositoryService.getConsultingTypeById(1);
    // then
    verify(consultingTypeRepository).findByConsultingTypeId(1);
  }

  @Test
  void getConsultingTypeById_Should_throwExceptionIfConsultingTypeNotFoundFor() {
    // when, then
    assertThrows(
        NotFoundException.class,
        () -> this.consultingTypeMongoRepositoryService.getConsultingTypeById(1));
  }

  @Test
  void getConsultingTypeBySlug_Should_CallFindBySlugInTheRepository() {
    // given
    Mockito.when(consultingTypeRepository.findBySlug("slug"))
        .thenReturn(Lists.newArrayList(new ConsultingTypeEntity()));
    consultingTypeMongoRepositoryService.getConsultingTypeBySlug("slug");
    // then
    verify(consultingTypeRepository).findBySlug("slug");
  }

  @Test
  void getConsultingTypeByTenant_Should_CallFindByTenantIdInTheRepository() {
    // given
    Mockito.when(consultingTypeRepository.findByTenantId(1)).thenReturn(new ConsultingTypeEntity());
    consultingTypeMongoRepositoryService.getConsultingTypeByTenantId(1);
    // then
    verify(consultingTypeRepository).findByTenantId(1);
  }
}
