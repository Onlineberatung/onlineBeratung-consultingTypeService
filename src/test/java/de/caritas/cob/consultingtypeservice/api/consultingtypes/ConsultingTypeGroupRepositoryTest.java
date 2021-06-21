package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;

public class ConsultingTypeGroupRepositoryTest {

  private ConsultingType consultingType1;
  private ConsultingType consultingType2;
  private ConsultingType consultingType3;
  private final String GROUP_1 = "group1";
  private final String GROUP_2 = "group2";
  private final String GROUP_3 = "group3";

  @Before
  public void setup() {
    EasyRandom easyRandom = new EasyRandom();
    consultingType1 = easyRandom.nextObject(ConsultingType.class);
    consultingType2 = easyRandom.nextObject(ConsultingType.class);
    consultingType3 = easyRandom.nextObject(ConsultingType.class);
    consultingType1.setGroups(Arrays.asList(GROUP_1, GROUP_3));
    consultingType2.setGroups(Collections.singletonList(GROUP_1));
    consultingType2.setId(consultingType1.getId() + 1);
    consultingType3.setGroups(Collections.singletonList(GROUP_2));
    consultingType3.setId(consultingType2.getId() + 1);
  }

  @Test
  public void getConsultingTypesGroupMap_Should_ReturnMapWithConsultingTypeGroups() {

    ConsultingTypeGroupRepository consultingTypeGroupRepository = new ConsultingTypeGroupRepository();
    consultingTypeGroupRepository.addConsultingType(consultingType1);
    consultingTypeGroupRepository.addConsultingType(consultingType2);
    consultingTypeGroupRepository.addConsultingType(consultingType3);

    Map<String, List<ConsultingType>> result = consultingTypeGroupRepository.getConsultingTypesGroupMap();

    assertThat(result, notNullValue());
    assertThat(result.get(GROUP_1), hasSize(2));
    assertThat(result.get(GROUP_1).contains(consultingType1), is(true));
    assertThat(result.get(GROUP_1).contains(consultingType2), is(true));
    assertThat(result.get(GROUP_2), hasSize(1));
    assertThat(result.get(GROUP_2).contains(consultingType3), is(true));
    assertThat(result.get(GROUP_3), hasSize(1));
    assertThat(result.get(GROUP_3).contains(consultingType1), is(true));

  }

}
