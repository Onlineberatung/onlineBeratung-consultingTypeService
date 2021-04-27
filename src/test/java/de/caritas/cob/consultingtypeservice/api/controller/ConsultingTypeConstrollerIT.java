package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_BASIC_CONSULTING_TYPE_LIST;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.service.ConsultingTypeService;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsultingTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConsultingTypeConstrollerIT {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private ConsultingTypeService consultingTypeService;

  @Test
  public void getBasicConsultingTypeList_Should_ReturnNoContent_When_ServiceReturnsEmptyList()
      throws Exception {

    when(consultingTypeService.fetchBasicConsultingTypesList())
        .thenReturn(null);

    mvc.perform(
        get(PATH_GET_BASIC_CONSULTING_TYPE_LIST)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  public void getBasicConsultingTypeList_Should_ReturnConsultingTypeBasicList()
      throws Exception {

    when(consultingTypeService.fetchBasicConsultingTypesList())
        .thenReturn(Arrays.asList(new BasicConsultingTypeResponseDTO().id(0),
            new BasicConsultingTypeResponseDTO().id(1),
            new BasicConsultingTypeResponseDTO().id(3)));

    mvc.perform(
        get(PATH_GET_BASIC_CONSULTING_TYPE_LIST)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
