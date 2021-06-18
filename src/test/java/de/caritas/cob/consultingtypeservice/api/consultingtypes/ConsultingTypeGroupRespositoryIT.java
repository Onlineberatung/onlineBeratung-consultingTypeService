package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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
public class ConsultingTypeGroupRespositoryIT {

  @Autowired
  private ConsultingTypeLoader consultingTypeLoader;
  @Autowired
  private ConsultingTypeGroupRepository consultingTypeGroupRepository;

  @Test
  public void getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups() {

    Map<String, List<ConsultingType>> result = consultingTypeGroupRepository
        .getConsultingTypesGroupMap();

    assertThat(result, notNullValue());
    final String IGNORE_PLACEHOLDER_GROUP = "${json-unit.ignore-element}";
    final int CONSULTING_TYPE_ID_0 = 0;
    assertThat(result.get(IGNORE_PLACEHOLDER_GROUP), hasSize(1));
    assertThat(result.get(IGNORE_PLACEHOLDER_GROUP).get(0).getId(), is(CONSULTING_TYPE_ID_0));
    assertThat(result.get(IGNORE_PLACEHOLDER_GROUP).get(0).getGroup(),
        is(IGNORE_PLACEHOLDER_GROUP));
    final String GROUP = "group";
    final int CONSULTING_TYPE_ID_1 = 1;
    final int CONSULTING_TYPE_ID_2 = 2;
    assertThat(result.get(GROUP), hasSize(2));
    assertThat(result, IsMapContaining.hasKey(GROUP));
    assertThat(result, IsMapContaining.hasKey(IGNORE_PLACEHOLDER_GROUP));
    assertThat(result.get(IGNORE_PLACEHOLDER_GROUP).get(0).getId(), is(CONSULTING_TYPE_ID_0));
    assertThat(result.get(GROUP), contains(
        hasProperty("id", is(CONSULTING_TYPE_ID_1)),
        hasProperty("id", is(CONSULTING_TYPE_ID_2))));
  }
}
