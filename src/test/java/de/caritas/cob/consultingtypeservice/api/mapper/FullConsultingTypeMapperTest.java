package de.caritas.cob.consultingtypeservice.api.mapper;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.testHelper.HelperMethods;
import java.io.IOException;
import org.junit.Test;

public class FullConsultingTypeMapperTest {

  @Test
  public void mapConsultingType_Should_MapAllProperties() throws IOException {

    FullConsultingTypeResponseDTO result =
        FullConsultingTypeMapper.mapConsultingType(HelperMethods.getConsultingType());
    String resultJson = new ObjectMapper().writeValueAsString(result);

    assertThat(result, notNullValue());
    assertThat(
        resultJson,
        jsonEquals(HelperMethods.getConsultingTypeSettingsAsJsonString())
            .whenIgnoringPaths("groups"));
  }
}
