package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import de.caritas.cob.consultingtypeservice.testHelper.MongoTestInitializer;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration(
    classes = ConsultingTypeServiceApplication.class,
    initializers = MongoTestInitializer.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@TestPropertySource(properties = "multitenancy.enabled=false")
class ConsultingTypeGroupRepositoryIT {

  @Autowired private ConsultingTypeLoader consultingTypeLoader;

  @Autowired private ConsultingTypeGroupRepository consultingTypeGroupRepository;

  @Test
  void getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups() {

    // when
    Map<String, List<ConsultingType>> result =
        consultingTypeGroupRepository.getConsultingTypesGroupMap();

    // then

    final String GROUP_1 = "group1";
    final String GROUP_2 = "group2";
    final String GROUP_3 = "group3";

    assertThat(result).isNotNull().containsKeys(GROUP_1, GROUP_2, GROUP_3);
    assertThat(result.get(GROUP_1).get(0).getGroups()).contains(GROUP_1);

  }
}
