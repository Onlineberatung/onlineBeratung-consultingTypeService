package de.caritas.cob.consultingtypeservice.api.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.auth.AuthenticatedUser;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeConverter;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepositoryService;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypePatchDTO;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Fail;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeServiceTest {

  @InjectMocks ConsultingTypeService consultingTypeService;
  @Mock ConsultingTypeRepositoryService consultingTypeRepository;

  @Mock ConsultingTypeConverter consultingTypeConverter;

  @Mock AuthenticatedUser authenticatedUser;

  private ConsultingType consultingType1;
  private ConsultingType consultingType2;
  private ConsultingType consultingType3;

  @Before
  public void setup() {
    EasyRandom easyRandom = new EasyRandom();
    consultingType1 = easyRandom.nextObject(ConsultingType.class);
    consultingType2 = easyRandom.nextObject(ConsultingType.class);
    consultingType3 = easyRandom.nextObject(ConsultingType.class);
    consultingType2.setId(consultingType1.getId() + 1);
    consultingType3.setId(consultingType2.getId() + 1);
    when(consultingTypeRepository.getListOfConsultingTypes())
        .thenReturn(Arrays.asList(consultingType1, consultingType2, consultingType3));
    when(consultingTypeRepository.getConsultingTypeById(consultingType2.getId()))
        .thenReturn(consultingType2);
    when(consultingTypeRepository.getConsultingTypeBySlug(consultingType3.getSlug()))
        .thenReturn(consultingType3);
    ReflectionTestUtils.setField(
        consultingTypeService, "multitenancyWithSingleDomainEnabled", false);
  }

  @Test
  public void fetchBasicConsultingTypesList_Should_ReturnListOfBasicConsultingTypeResponseDTO() {

    List<BasicConsultingTypeResponseDTO> result =
        consultingTypeService.fetchBasicConsultingTypesList();
    assertThat(result, notNullValue());
    assertThat(result, hasSize(3));
  }

  @Test
  public void
      fetchFullConsultingTypeSettingsById_Should_ReturnWantedFullConsultingTypeResponseDTO() {

    FullConsultingTypeResponseDTO result =
        consultingTypeService.fetchFullConsultingTypeSettingsById(consultingType2.getId());
    assertThat(result, notNullValue());
    assertThat(result.getId(), is(consultingType2.getId()));
  }

  @Test
  public void
      fetchFullConsultingTypeSettingsBySlug_Should_ReturnWantedFullConsultingTypeResponseDTO() {

    FullConsultingTypeResponseDTO result =
        consultingTypeService.fetchFullConsultingTypeSettingsBySlug(consultingType3.getSlug());
    assertThat(result, notNullValue());
    assertThat(result.getId(), is(consultingType3.getId()));
    assertThat(result.getSlug(), is(consultingType3.getSlug()));
  }

  @Test
  public void
      fetchExtendedConsultingTypeSettingsById_Should_ReturnWantedExtendedConsultingTypeResponseDTO() {

    ExtendedConsultingTypeResponseDTO result =
        consultingTypeService.fetchExtendedConsultingTypeSettingsById(consultingType2.getId());
    assertThat(result, notNullValue());
    assertThat(result.getId(), is(consultingType2.getId()));
  }

  @Test
  public void
      fetchBasicConsultingTypeSettingsById_Should_ReturnWantedBasicConsultingTypeResponseDTO() {

    BasicConsultingTypeResponseDTO result =
        consultingTypeService.fetchBasicConsultingTypeSettingsById(consultingType2.getId());
    assertThat(result, notNullValue());
    assertThat(result.getId(), is(consultingType2.getId()));
  }

  @Test
  public void
      assertChangesAreAllowedForUsersWithLimitedPatchPermission_Should_ThrowExceptionIfSingleDomainMultitenancyAndSingleTenantAdminTriesToChangeLanguage() {
    // given
    ReflectionTestUtils.setField(
        consultingTypeService, "multitenancyWithSingleDomainEnabled", true);
    ConsultingTypePatchDTO patchDTO = new ConsultingTypePatchDTO();
    patchDTO.setLanguageFormal(false);
    ConsultingType existing = new ConsultingType().withLanguageFormal(true);

    // when, then
    assertThrows(
        AccessDeniedException.class,
        () ->
            consultingTypeService.assertChangesAreAllowedForUsersWithLimitedPatchPermission(
                patchDTO, existing));
  }

  @Test
  public void
      assertChangesAreAllowedForUsersWithLimitedPatchPermission_Should_NotThrowExceptionIfMultidomainMultitenancyAndSingleTenantAdminTriesToChangeLanguage() {
    // given
    ReflectionTestUtils.setField(
        consultingTypeService, "multitenancyWithSingleDomainEnabled", false);
    ConsultingTypePatchDTO patchDTO = new ConsultingTypePatchDTO();
    patchDTO.setLanguageFormal(false);
    ConsultingType existing = new ConsultingType().withLanguageFormal(true);

    // when, then
    try {
      consultingTypeService.assertChangesAreAllowedForUsersWithLimitedPatchPermission(
          patchDTO, existing);
    } catch (Exception e) {
      Fail.fail("Expected no exception");
    }
  }
}
