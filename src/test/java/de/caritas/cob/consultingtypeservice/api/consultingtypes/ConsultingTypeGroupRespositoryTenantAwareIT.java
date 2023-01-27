package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "spring.profiles.active=testing")
@TestPropertySource(properties = "multitenancy.enabled=true")
@TestPropertySource(
    properties =
        "consulting.types.json.path=src/test/resources/consulting-type-settings-tenant-specific")
public class ConsultingTypeGroupRespositoryTenantAwareIT {

  @Autowired private ConsultingTypeGroupRepository consultingTypeGroupRepository;

  @AfterEach
  public void tearDown() {
    TenantContext.clear();
  }

  @Test
  public void getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups() {
    // given
    TenantContext.setCurrentTenant(2L);

    // when
    var result = consultingTypeGroupRepository.getConsultingTypesGroupMap();

    // then
    assertGroupsCorrectlyRetrievedAndFilteredForTenant(result);
  }

  private void assertGroupsCorrectlyRetrievedAndFilteredForTenant(
      Map<String, List<ConsultingType>> result) {
    assertThat(result).isNotNull();
    final String GROUP_1 = "group1";
    assertThat(result.get(GROUP_1)).isNull();
    final String GROUP_2 = "group2";
    final String GROUP_3 = "group3";
    final int CONSULTING_TYPE_ID_1 = 11;
    final int CONSULTING_TYPE_ID_2 = 12;
    assertThat(result.get(GROUP_2)).hasSize(1);
    assertThat(result.get(GROUP_3)).hasSize(2);
    assertThat(result).containsKey(GROUP_2);
    assertThat(result).containsKey(GROUP_3);
    assertThat(result.get(GROUP_2).get(0).getId()).isEqualTo(CONSULTING_TYPE_ID_1);
    assertThat(result.get(GROUP_3))
        .extracting(group -> group.getId())
        .contains(CONSULTING_TYPE_ID_1, CONSULTING_TYPE_ID_2);
  }
}
