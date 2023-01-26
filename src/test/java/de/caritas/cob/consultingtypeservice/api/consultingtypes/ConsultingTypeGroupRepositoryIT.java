package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Map;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;
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
public class ConsultingTypeGroupRepositoryIT {

  @Autowired private ConsultingTypeLoader consultingTypeLoader;

  @Autowired private ConsultingTypeGroupRepository consultingTypeGroupRepository;

  @Test
  public void getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups() {

    Map<String, List<ConsultingType>> result =
        consultingTypeGroupRepository.getConsultingTypesGroupMap();

    assertThat(result, notNullValue());
    final String GROUP_1 = "group1";
    final int CONSULTING_TYPE_ID_0 = 0;
    assertThat(result.get(GROUP_1), hasSize(1));
    assertThat(result.get(GROUP_1).get(0).getId(), is(CONSULTING_TYPE_ID_0));
    assertThat(result.get(GROUP_1).get(0).getGroups().contains(GROUP_1), is(true));
    final String GROUP_2 = "group2";
    final String GROUP_3 = "group3";
    final int CONSULTING_TYPE_ID_1 = 1;
    final int CONSULTING_TYPE_ID_2 = 2;
    assertThat(result.get(GROUP_2), hasSize(2));
    assertThat(result, IsMapContaining.hasKey(GROUP_2));
    assertThat(result, IsMapContaining.hasKey(GROUP_3));
    assertThat(result, IsMapContaining.hasKey(GROUP_1));
    assertThat(result.get(GROUP_1).get(0).getId(), is(CONSULTING_TYPE_ID_0));
    assertThat(
        result.get(GROUP_2),
        containsInAnyOrder(
            hasProperty("id", is(CONSULTING_TYPE_ID_1)),
            hasProperty("id", is(CONSULTING_TYPE_ID_2))));
    assertThat(result.get(GROUP_3).get(0).getId(), is(CONSULTING_TYPE_ID_1));
  }
}
