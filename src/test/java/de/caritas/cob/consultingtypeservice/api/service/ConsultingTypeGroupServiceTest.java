package de.caritas.cob.consultingtypeservice.api.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeGroupRepositoryImpl;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeGroupResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsultingTypeGroupServiceTest {

  @InjectMocks ConsultingTypeGroupService consultingTypeGroupService;
  @Mock ConsultingTypeGroupRepositoryImpl consultingTypeGroupRepository;

  @Test
  public void fetchConsultingTypeGroupList_Should_ReturnListOfConsultingTypeGroupResponseDTO() {

    EasyRandom easyRandom = new EasyRandom();
    final String GROUP_1 = "group1";
    final String GROUP_2 = "group2";
    var consultingType1 = easyRandom.nextObject(ConsultingType.class);
    consultingType1.setGroups(Arrays.asList(GROUP_1, GROUP_2));
    var consultingType2 = easyRandom.nextObject(ConsultingType.class);
    consultingType2.setGroups(Collections.singletonList(GROUP_1));
    consultingType2.setId(consultingType1.getId() + 1);
    var consultingType3 = easyRandom.nextObject(ConsultingType.class);
    consultingType3.setGroups(Collections.singletonList(GROUP_2));
    consultingType3.setId(consultingType2.getId() + 1);
    Map<String, List<ConsultingType>> consultingTypeGroupMap = new LinkedHashMap<>();
    consultingTypeGroupMap.put(GROUP_1, Arrays.asList(consultingType1, consultingType2));
    consultingTypeGroupMap.put(GROUP_2, Arrays.asList(consultingType1, consultingType3));
    when(consultingTypeGroupRepository.getConsultingTypesGroupMap())
        .thenReturn(consultingTypeGroupMap);

    List<ConsultingTypeGroupResponseDTO> result =
        consultingTypeGroupService.fetchConsultingTypeGroupList();

    assertThat(result, notNullValue());
    assertThat(result, hasSize(2));
    assertThat(result.get(0).getTitle(), is(GROUP_1));
    assertThat(result.get(0).getConsultingTypes(), hasSize(2));
    assertThat(
        result.get(0).getConsultingTypes().get(0).getTitles().getDefault(),
        is(consultingType1.getTitles().getDefault()));
    assertThat(
        result.get(0).getConsultingTypes().get(0).getTitles().getLong(),
        is(consultingType1.getTitles().getLong()));
    assertThat(result.get(0).getConsultingTypes().get(0).getId(), is(consultingType1.getId()));
    assertThat(
        result.get(0).getConsultingTypes().get(1).getTitles().getDefault(),
        is(consultingType2.getTitles().getDefault()));
    assertThat(
        result.get(0).getConsultingTypes().get(1).getTitles().getLong(),
        is(consultingType2.getTitles().getLong()));
    assertThat(result.get(0).getConsultingTypes().get(1).getId(), is(consultingType2.getId()));
    assertThat(result.get(1).getTitle(), is(GROUP_2));
    assertThat(result.get(1).getConsultingTypes(), hasSize(2));
    assertThat(
        result.get(1).getConsultingTypes().get(0).getTitles().getDefault(),
        is(consultingType1.getTitles().getDefault()));
    assertThat(
        result.get(1).getConsultingTypes().get(0).getTitles().getLong(),
        is(consultingType1.getTitles().getLong()));
    assertThat(result.get(1).getConsultingTypes().get(0).getId(), is(consultingType1.getId()));
    assertThat(
        result.get(1).getConsultingTypes().get(1).getTitles().getDefault(),
        is(consultingType3.getTitles().getDefault()));
    assertThat(
        result.get(1).getConsultingTypes().get(1).getTitles().getLong(),
        is(consultingType3.getTitles().getLong()));
    assertThat(result.get(1).getConsultingTypes().get(1).getId(), is(consultingType3.getId()));
  }
}
