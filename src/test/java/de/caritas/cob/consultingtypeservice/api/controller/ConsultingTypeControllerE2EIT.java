package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.ROOT_PATH;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeConverter;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.FullConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTO;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Arrays;
import javax.servlet.http.Cookie;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("testing")
@AutoConfigureTestDatabase
public class ConsultingTypeControllerE2EIT {

  private static final EasyRandom easyRandom = new EasyRandom();
  private static final String CSRF_HEADER = "csrfHeader";
  private static final String CSRF_VALUE = "test";
  private static final Cookie CSRF_COOKIE = new Cookie("csrfCookie", CSRF_VALUE);

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ConsultingTypeConverter consultingTypeConverter;


  @Test
  public void createConsultingType_Should_returnOk_When_requiredConsultingTypeDTOIsGiven()
      throws Exception {
    // given
    ConsultingTypeDTO consultingTypeDTO =
        easyRandom.nextObject(ConsultingTypeDTO.class)
            .voluntaryComponents(null);
    consultingTypeDTO.getRoles().getConsultant().addRoleNames("test", Arrays.asList("test"));

    FullConsultingTypeResponseDTO expectedResponseDTO = createFrom(consultingTypeDTO);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // when
    this.mvc
        .perform(
            post(ROOT_PATH)
                .cookie(CSRF_COOKIE)
                .header(CSRF_HEADER, CSRF_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultingTypeDTO)))
        // then
        .andExpect(status().isOk())
        .andExpect(json().isEqualTo(objectMapper.writeValueAsString(expectedResponseDTO)));
  }

  private FullConsultingTypeResponseDTO createFrom(ConsultingTypeDTO consultingTypeDTO) {
    final ConsultingType consultingType = consultingTypeConverter.convert(consultingTypeDTO);
    return ConsultingTypeMapper.mapConsultingType(consultingType,
        FullConsultingTypeMapper::mapConsultingType);
  }
}
