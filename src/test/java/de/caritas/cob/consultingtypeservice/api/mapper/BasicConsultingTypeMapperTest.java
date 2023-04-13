package de.caritas.cob.consultingtypeservice.api.mapper;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.testHelper.HelperMethods;
import java.io.IOException;
import org.junit.Test;

public class BasicConsultingTypeMapperTest {

  @Test
  public void mapConsultingType_Should_MapAllBasicProperties() throws IOException {

    BasicConsultingTypeResponseDTO result =
        BasicConsultingTypeMapper.mapConsultingType(HelperMethods.getConsultingType());
    String resultJson = new ObjectMapper().writeValueAsString(result);

    assertThat(result, notNullValue());
    assertThat(
        resultJson,
        jsonEquals(HelperMethods.getConsultingTypeSettingsAsJsonString())
            .whenIgnoringPaths(
                "lockedAgencies",
                "consultantBoundedToConsultingType",
                "slug",
                "excludeNonMainConsultantsFromTeamSessions",
                "welcomeMessage",
                "sendFurtherStepsMessage",
                "sendSaveSessionDataMessage",
                "sessionDataInitializing",
                "whiteSpot",
                "initializeFeedbackChat",
                "isPeerChat",
                "roles",
                "notifications",
                "registration",
                "requiredComponents",
                "groups",
                "welcomeScreen",
                "tenantId"));
  }
}
