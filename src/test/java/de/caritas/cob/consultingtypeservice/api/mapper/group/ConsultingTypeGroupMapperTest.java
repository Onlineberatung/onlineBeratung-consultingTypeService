package de.caritas.cob.consultingtypeservice.api.mapper.group;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeCoreDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import de.caritas.cob.consultingtypeservice.testHelper.HelperMethods;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class ConsultingTypeGroupMapperTest {

  @Test
  public void determineGroupTitle_Should_determineGroupTitle() throws IOException {

    ConsultingType consultingType = HelperMethods.getConsultingType();
    List<ConsultingType> consultingTypeList = Collections
        .singletonList(consultingType);
    String result = ConsultingTypeGroupMapper.determineGroupTitle(consultingTypeList);
    assertThat(result, is(consultingType.getGroup()));
  }

  @Test(expected = UnexpectedErrorException.class)
  public void determineGroupTitle_Should_throwUnexpectedErrorException_WhenConsultingTypeListIsEmpty() {

    ConsultingTypeGroupMapper.determineGroupTitle(new ArrayList<>());

  }

  @Test
  public void mapConsultingTypeCoreDtoList_Should_MapIdAndDefaultTitle() throws IOException {

    ConsultingType consultingType = HelperMethods.getConsultingType();
    List<ConsultingType> consultingTypeList = Collections
        .singletonList(consultingType);

    List<ConsultingTypeCoreDTO> result = ConsultingTypeGroupMapper.mapConsultingTypeCoreDtoList(consultingTypeList);

    assertThat(result.size(), is(1));
    assertThat(result.get(0).getId(), is(consultingType.getId()));
    assertThat(result.get(0).getTitles(), notNullValue());
    assertThat(result.get(0).getTitles().getDefault(), is(consultingType.getTitles().getDefault()));

  }

}
