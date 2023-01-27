package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Lists;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConsultingTypeGroupTenantAwareRepositoryImplTest {

  private ConsultingTypeEntity consultingType1;
  private ConsultingTypeEntity consultingType2;
  private ConsultingTypeEntity consultingType3;
  private final String GROUP_1 = "group1";
  private final String GROUP_2 = "group2";
  private final String GROUP_3 = "group3";

  @InjectMocks
  ConsultingTypeGroupTenantAwareRepositoryImpl consultingTypeGroupTenantAwareRepository;

  @Mock ConsultingTypeTenantAwareRepository consultingTypeMongoTenantAwareRepository;

  @BeforeEach
  void setup() {
    EasyRandom easyRandom = new EasyRandom();
    consultingType1 = easyRandom.nextObject(ConsultingTypeEntity.class);
    consultingType2 = easyRandom.nextObject(ConsultingTypeEntity.class);
    consultingType3 = easyRandom.nextObject(ConsultingTypeEntity.class);
    consultingType1.setGroups(Arrays.asList(GROUP_1, GROUP_3));
    consultingType2.setGroups(Collections.singletonList(GROUP_1));
    consultingType2.setId(consultingType1.getId() + 1);
    consultingType3.setGroups(Collections.singletonList(GROUP_2));
    consultingType3.setId(consultingType2.getId() + 1);
  }

  @AfterEach
  void tearDown() {
    TenantContext.clear();
  }

  @Test
  void
      getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups_ForTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(0L);
    when(consultingTypeMongoTenantAwareRepository.findAll())
        .thenReturn(Lists.newArrayList(consultingType1, consultingType2, consultingType3));

    // when
    Map<String, List<ConsultingType>> result =
        consultingTypeGroupTenantAwareRepository.getConsultingTypesGroupMap();

    // then
    assertThat(result).isNotNull();
    assertThat(result.get(GROUP_1)).hasSize(2);
    assertThat(result.get(GROUP_1)).contains(consultingType1, consultingType2);
    assertThat(result.get(GROUP_2)).hasSize(1);
    assertThat(result.get(GROUP_2)).contains(consultingType3);
    assertThat(result.get(GROUP_3)).hasSize(1);
    assertThat(result.get(GROUP_3)).contains(consultingType1);
  }

  @Test
  void
      getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups_ForNonTechnicalTenantContext() {
    // given
    TenantContext.setCurrentTenant(1L);
    when(consultingTypeMongoTenantAwareRepository.findAllHavingTenantId(1L))
        .thenReturn(Lists.newArrayList(consultingType1, consultingType2));

    // when
    Map<String, List<ConsultingType>> result =
        consultingTypeGroupTenantAwareRepository.getConsultingTypesGroupMap();

    // then
    assertThat(result).isNotNull();
    assertThat(result.get(GROUP_1)).hasSize(2);
    assertThat(result.get(GROUP_1)).contains(consultingType1, consultingType2);
    assertThat(result.get(GROUP_2)).isNull();
    assertThat(result.get(GROUP_3)).hasSize(1);
    assertThat(result.get(GROUP_3)).contains(consultingType1);
  }
}
