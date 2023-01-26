package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultingTypeMongoTenantAwareRepositoryServiceTest {

  private static final long TECHNICAL_TENANT_CONTEXT = 0L;
  private static final long NON_TECHNICAL_TENANT_CONTEXT = 1L;

  @Mock private ConsultingTypeTenantAwareRepository consultingTypeMongoTenantAwareRepository;

  @Mock private ConsultingTypeConverter consultingTypeConverter;

  @InjectMocks
  ConsultingTypeMongoTenantAwareRepositoryService consultingTypeMongoTenantAwareRepositoryService;

  @AfterEach
  public void cleanUp() {
    TenantContext.clear();
  }

  @Test
  void getListOfConsultingTypes_Should_returnNotFilteredConsultingTypesForTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(TECHNICAL_TENANT_CONTEXT);
    consultingTypeMongoTenantAwareRepositoryService.getListOfConsultingTypes();
    // then
    verify(consultingTypeMongoTenantAwareRepository).findAll();
    verify(consultingTypeConverter).convertList(Mockito.anyList());
  }

  @Test
  void getListOfConsultingTypes_Should_returnFilteredConsultingTypesForNonTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(NON_TECHNICAL_TENANT_CONTEXT);
    consultingTypeMongoTenantAwareRepositoryService.getListOfConsultingTypes();
    // then
    verify(consultingTypeMongoTenantAwareRepository)
        .findAllHavingTenantId(NON_TECHNICAL_TENANT_CONTEXT);
    verify(consultingTypeConverter).convertList(Mockito.anyList());
  }

  @Test
  void
      getConsultingTypeById_Should_getConsultingTypeByIdAndNotFilterByTenantIdForTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(TECHNICAL_TENANT_CONTEXT);
    Mockito.when(consultingTypeMongoTenantAwareRepository.findByConsultingTypeId(1))
        .thenReturn(new ConsultingTypeEntity());
    consultingTypeMongoTenantAwareRepositoryService.getConsultingTypeById(1);
    // then
    verify(consultingTypeMongoTenantAwareRepository).findByConsultingTypeId(1);
  }

  @Test
  void
      getConsultingTypeById_Should_getConsultingTypeByIdAndFilterByTenantIdForNonTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(NON_TECHNICAL_TENANT_CONTEXT);
    Mockito.when(
            consultingTypeMongoTenantAwareRepository.findConsultingTypeByIdAndTenantId(
                1, NON_TECHNICAL_TENANT_CONTEXT))
        .thenReturn(new ConsultingTypeEntity());

    // when
    consultingTypeMongoTenantAwareRepositoryService.getConsultingTypeById(1);

    // then
    verify(consultingTypeMongoTenantAwareRepository)
        .findConsultingTypeByIdAndTenantId(1, NON_TECHNICAL_TENANT_CONTEXT);
  }

  @Test
  void
      getConsultingTypeById_Should_throwExceptionIfConsultingTypeNotFoundForTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(TECHNICAL_TENANT_CONTEXT);

    // when, then
    assertThrows(
        NotFoundException.class,
        () -> this.consultingTypeMongoTenantAwareRepositoryService.getConsultingTypeById(1));
  }

  @Test
  void
      getConsultingTypeById_Should_throwExceptionIfConsultingTypeNotFoundForNonTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(NON_TECHNICAL_TENANT_CONTEXT);
    // when, then
    assertThrows(
        NotFoundException.class,
        () -> this.consultingTypeMongoTenantAwareRepositoryService.getConsultingTypeById(1));
  }

  @Test
  void
      isConsultingTypeWithGivenSlugPresent_Should_CallFindSlugByTenantIdFromProvidedConsultingType() {
    // given
    TenantContext.setCurrentTenant(NON_TECHNICAL_TENANT_CONTEXT);
    // when
    consultingTypeMongoTenantAwareRepositoryService.isConsultingTypeWithGivenSlugPresent(
        new ConsultingType().withSlug("slug").withTenantId(1));
    // then
    verify(consultingTypeMongoTenantAwareRepository).findBySlugAndTenantId("slug", 1L);
  }
}
