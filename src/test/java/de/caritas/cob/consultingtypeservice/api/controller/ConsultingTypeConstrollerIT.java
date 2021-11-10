package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_BASIC_CONSULTING_TYPE_BY_ID;
import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_BASIC_CONSULTING_TYPE_LIST;
import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_EXTENDED_CONSULTING_TYPE_BY_ID;
import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_FULL_CONSULTING_TYPE_BY_ID;
import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_FULL_CONSULTING_TYPE_BY_SLUG;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.mapper.BasicConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ExtendedConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.FullConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.service.ConsultingTypeService;
import de.caritas.cob.consultingtypeservice.testHelper.HelperMethods;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.client.LinkDiscoverers;
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
  @MockBean
  private LinkDiscoverers linkDiscoverers;

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

    BasicConsultingTypeResponseDTO basicConsultingTypeResponseDTO = BasicConsultingTypeMapper
        .mapConsultingType(HelperMethods.getConsultingType());
    BasicConsultingTypeResponseDTO[] basicConsultingTypeResponseDTOArray = { basicConsultingTypeResponseDTO, basicConsultingTypeResponseDTO };
    String basicConsultingTypeResponseDTOJson = new ObjectMapper().writeValueAsString(basicConsultingTypeResponseDTOArray);

    when(consultingTypeService.fetchBasicConsultingTypesList())
        .thenReturn(Arrays.asList(basicConsultingTypeResponseDTO,
            basicConsultingTypeResponseDTO));

    mvc.perform(
        get(PATH_GET_BASIC_CONSULTING_TYPE_LIST)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(json().isEqualTo(basicConsultingTypeResponseDTOJson));
  }

  @Test
  public void getFullConsultingTypeById_Should_ReturnFullConsultingTypeDTO() throws Exception {

    Integer consultingTypeId = 1;
    when(consultingTypeService.fetchFullConsultingTypeSettingsById(consultingTypeId))
        .thenReturn(FullConsultingTypeMapper
            .mapConsultingType(HelperMethods.getConsultingType()));

    mvc.perform(
        get(String.format(PATH_GET_FULL_CONSULTING_TYPE_BY_ID, consultingTypeId))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(json().isEqualTo(HelperMethods.getConsultingTypeSettingsAsJsonString()));
  }

  @Test
  public void getFullConsultingTypeById_Should_ReturnNotFound_WhenConsultingTypeIsMissing() throws Exception {

    Integer consultingTypeId = 1;
    when(consultingTypeService.fetchFullConsultingTypeSettingsById(consultingTypeId))
        .thenThrow(new NotFoundException("Not found"));

    mvc.perform(
        get(String.format(PATH_GET_FULL_CONSULTING_TYPE_BY_ID, consultingTypeId))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(json().isStringEqualTo(StringUtils.EMPTY));
  }

  @Test
  public void getFullConsultingTypeBySlug_Should_ReturnFullConsultingTypeDTO() throws Exception {

    String consultingTypeSlug = "consultingtype0";
    when(consultingTypeService.fetchFullConsultingTypeSettingsBySlug(consultingTypeSlug))
        .thenReturn(FullConsultingTypeMapper
            .mapConsultingType(HelperMethods.getConsultingType()));

    mvc.perform(
        get(String.format(PATH_GET_FULL_CONSULTING_TYPE_BY_SLUG, consultingTypeSlug))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(json().isEqualTo(HelperMethods.getConsultingTypeSettingsAsJsonString()));
  }

  @Test
  public void getFullConsultingTypeBySlug_Should_ReturnNotFound_WhenConsultingTypeIsMissing() throws Exception {

    String consultingTypeSlug = "consultingtype0";
    when(consultingTypeService.fetchFullConsultingTypeSettingsBySlug(consultingTypeSlug))
        .thenThrow(new NotFoundException("Not found"));

    mvc.perform(
        get(String.format(PATH_GET_FULL_CONSULTING_TYPE_BY_SLUG, consultingTypeSlug))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(json().isStringEqualTo(StringUtils.EMPTY));
  }

  @Test
  public void getExtendedConsultingTypeById_Should_ReturnFullConsultingTypeDTO() throws Exception {

    Integer consultingTypeId = 1;
    ExtendedConsultingTypeResponseDTO extendedConsultingTypeResponseDTO = ExtendedConsultingTypeMapper
        .mapConsultingType(HelperMethods.getConsultingType());
    when(consultingTypeService.fetchExtendedConsultingTypeSettingsById(consultingTypeId))
        .thenReturn(extendedConsultingTypeResponseDTO);

    mvc.perform(
        get(String.format(PATH_GET_EXTENDED_CONSULTING_TYPE_BY_ID, consultingTypeId))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(json()
            .isEqualTo(new ObjectMapper().writeValueAsString(extendedConsultingTypeResponseDTO)));
  }

  @Test
  public void getExtendedConsultingTypeId_Should_ReturnNotFound_WhenConsultingTypeIsMissing()
      throws Exception {

    Integer consultingTypeId = 1;
    when(consultingTypeService.fetchExtendedConsultingTypeSettingsById(consultingTypeId))
        .thenThrow(new NotFoundException("Not found"));

    mvc.perform(
        get(String.format(PATH_GET_EXTENDED_CONSULTING_TYPE_BY_ID, consultingTypeId))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(json().isStringEqualTo(StringUtils.EMPTY));

  }

  @Test
  public void getBasicConsultingTypeById_Should_ReturnBasicConsultingTypeDTO() throws Exception {

    Integer consultingTypeId = 1;
    BasicConsultingTypeResponseDTO basicConsultingTypeResponseDTO = BasicConsultingTypeMapper
        .mapConsultingType(HelperMethods.getConsultingType());
    when(consultingTypeService.fetchBasicConsultingTypeSettingsById(consultingTypeId))
        .thenReturn(basicConsultingTypeResponseDTO);

    mvc.perform(
        get(String.format(PATH_GET_BASIC_CONSULTING_TYPE_BY_ID, consultingTypeId))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(json()
            .isEqualTo(new ObjectMapper().writeValueAsString(basicConsultingTypeResponseDTO)));
  }

  @Test
  public void getBasicConsultingTypeById_Should_ReturnNotFound_WhenConsultingTypeIsMissing()
      throws Exception {

    Integer consultingTypeId = 1;
    when(consultingTypeService.fetchBasicConsultingTypeSettingsById(consultingTypeId))
        .thenThrow(new NotFoundException("Not found"));

    mvc.perform(
        get(String.format(PATH_GET_BASIC_CONSULTING_TYPE_BY_ID, consultingTypeId))
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(json().isStringEqualTo(StringUtils.EMPTY));

  }

}
