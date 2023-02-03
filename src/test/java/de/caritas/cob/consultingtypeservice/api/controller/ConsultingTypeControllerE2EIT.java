package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.api.auth.UserRole.TENANT_ADMIN;
import static de.caritas.cob.consultingtypeservice.api.auth.UserRole.TOPIC_ADMIN;
import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.PATH_GET_FULL_CONSULTING_TYPE_BY_TENANT;
import static de.caritas.cob.consultingtypeservice.testHelper.PathConstants.ROOT_PATH;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.auth.AuthenticatedUser;
import de.caritas.cob.consultingtypeservice.api.auth.Authority.AuthorityValue;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeConverter;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepository;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.FullConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOWelcomeMessage;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypePatchDTO;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.Arrays;
import java.util.HashSet;
import javax.servlet.http.Cookie;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("testing")
@AutoConfigureTestDatabase
class ConsultingTypeControllerE2EIT {

  private static final EasyRandom easyRandom = new EasyRandom();
  private static final String CSRF_HEADER = "csrfHeader";
  private static final String CSRF_VALUE = "test";
  private static final Cookie CSRF_COOKIE = new Cookie("csrfCookie", CSRF_VALUE);
  private static final Integer CREATE_ID = 1001;

  private static final Integer EXISTING_ID = 1;

  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ConsultingTypeConverter consultingTypeConverter;

  @Autowired private ConsultingTypeRepository consultingTypeRepository;

  @MockBean private Keycloak keycloak;

  @MockBean AuthenticatedUser authenticatedUser;

  @Test
  void createConsultingType_Should_returnOk_When_requiredConsultingTypeDTOIsGiven()
      throws Exception {
    // given
    AuthenticationMockBuilder builder = new AuthenticationMockBuilder();

    ConsultingTypeDTO consultingTypeDTO =
        easyRandom
            .nextObject(ConsultingTypeDTO.class)
            .tenantId(4)
            .slug("test-slug")
            .voluntaryComponents(null);
    consultingTypeDTO.getRoles().getConsultant().addRoleNames("test", Arrays.asList("test"));

    FullConsultingTypeResponseDTO expectedResponseDTO = createFrom(consultingTypeDTO, CREATE_ID);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // when
    this.mvc
        .perform(
            post(ROOT_PATH)
                .with(authentication(builder.withUserRole(TENANT_ADMIN.getValue()).build()))
                .cookie(CSRF_COOKIE)
                .header(CSRF_HEADER, CSRF_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultingTypeDTO)))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(CREATE_ID))
        .andExpect(jsonPath("tenantId").value(4))
        .andExpect(jsonPath("slug").value("test-slug"))
        .andExpect(json().isEqualTo(objectMapper.writeValueAsString(expectedResponseDTO)));
  }

  @Test
  void patchConsultingType_Should_returnOk_When_requiredConsultingTypeDTOIsGiven()
      throws Exception {
    // given
    ConsultingTypePatchDTO consultingTypeDTO =
        easyRandom
            .nextObject(ConsultingTypePatchDTO.class)
            .isVideoCallAllowed(true)
            .languageFormal(true)
            .welcomeMessage(
                new ConsultingTypeDTOWelcomeMessage()
                    .sendWelcomeMessage(true)
                    .welcomeMessageText("welcome"));

    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    Authentication authentication =
        new AuthenticationMockBuilder().withUserRole(TENANT_ADMIN.getValue()).build();

    // when
    MvcResult mvcResult =
        this.mvc
            .perform(
                patch(ROOT_PATH + "/" + EXISTING_ID)
                    .with(authentication(authentication))
                    .cookie(CSRF_COOKIE)
                    .header(CSRF_HEADER, CSRF_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(consultingTypeDTO)))
            // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(EXISTING_ID))
            .andExpect(jsonPath("isVideoCallAllowed").value(true))
            .andExpect(jsonPath("languageFormal").value(true))
            .andExpect(jsonPath("welcomeMessage.sendWelcomeMessage").value(true))
            .andExpect(jsonPath("welcomeMessage.welcomeMessageText").value("welcome"))
            .andReturn();
  }

  @Test
  @WithMockUser(authorities = {AuthorityValue.LIMITED_PATCH_CONSULTING_TYPE})
  void patchConsultingType_Should_returnOk_When_singleTenantAdminTriesToPatchLimitedSettings()
      throws Exception {
    // given

    HashSet<String> permissions = new HashSet<>();
    permissions.add(AuthorityValue.LIMITED_PATCH_CONSULTING_TYPE);
    when(authenticatedUser.getGrantedAuthorities()).thenReturn(permissions);
    ConsultingTypeEntity byConsultingTypeId =
        consultingTypeRepository.findByConsultingTypeId(EXISTING_ID);
    boolean existingVideoCallSetting = byConsultingTypeId.getIsVideoCallAllowed();
    boolean existingLanguageFormal = byConsultingTypeId.getLanguageFormal();

    ConsultingTypePatchDTO consultingTypeDTO =
        easyRandom
            .nextObject(ConsultingTypePatchDTO.class)
            .isVideoCallAllowed(existingVideoCallSetting)
            .languageFormal(existingLanguageFormal)
            .welcomeMessage(
                new ConsultingTypeDTOWelcomeMessage()
                    .sendWelcomeMessage(true)
                    .welcomeMessageText("welcome"));

    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    // when
    this.mvc
        .perform(
            patch(ROOT_PATH + "/" + EXISTING_ID)
                .cookie(CSRF_COOKIE)
                .header(CSRF_HEADER, CSRF_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultingTypeDTO)))
        // then
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(EXISTING_ID))
        .andExpect(jsonPath("isVideoCallAllowed").value(existingVideoCallSetting))
        .andExpect(jsonPath("languageFormal").value(existingLanguageFormal))
        .andExpect(jsonPath("welcomeMessage.sendWelcomeMessage").value(true))
        .andExpect(jsonPath("welcomeMessage.welcomeMessageText").value("welcome"))
        .andReturn();
  }

  @Test
  @WithMockUser(authorities = {AuthorityValue.LIMITED_PATCH_CONSULTING_TYPE})
  void
      patchConsultingType_Should_returnForbidden_When_singleTenantAdminTriesToPatchSettingsThatHeIsNotAllowedTo()
          throws Exception {
    // given

    HashSet<String> permissions = new HashSet<>();
    permissions.add(AuthorityValue.LIMITED_PATCH_CONSULTING_TYPE);
    when(authenticatedUser.getGrantedAuthorities()).thenReturn(permissions);
    ConsultingTypeEntity byConsultingTypeId =
        consultingTypeRepository.findByConsultingTypeId(EXISTING_ID);
    boolean existingVideoCallSetting = byConsultingTypeId.getIsVideoCallAllowed();
    boolean existingLanguageFormal = byConsultingTypeId.getLanguageFormal();
    ConsultingTypePatchDTO consultingTypeDTO =
        easyRandom
            .nextObject(ConsultingTypePatchDTO.class)
            .isVideoCallAllowed(!existingVideoCallSetting)
            .languageFormal(!existingLanguageFormal)
            .welcomeMessage(
                new ConsultingTypeDTOWelcomeMessage()
                    .sendWelcomeMessage(true)
                    .welcomeMessageText("welcome"));

    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    // when
    MvcResult mvcResult =
        this.mvc
            .perform(
                patch(ROOT_PATH + "/" + EXISTING_ID)
                    .cookie(CSRF_COOKIE)
                    .header(CSRF_HEADER, CSRF_VALUE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(consultingTypeDTO)))
            // then
            .andExpect(status().isForbidden())
            .andReturn();
  }

  @Test
  void
      patchConsultingType_Should_returnForbidden_When_userInDifferentRoleThanTenantAdminOrTenantSuperadmin()
          throws Exception {
    // given
    ConsultingTypeDTO consultingTypeDTO =
        easyRandom
            .nextObject(ConsultingTypeDTO.class)
            .tenantId(4)
            .slug("test-slug")
            .voluntaryComponents(null);
    consultingTypeDTO.getRoles().getConsultant().addRoleNames("test", Arrays.asList("test"));

    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    Authentication authentication =
        new AuthenticationMockBuilder().withUserRole(TOPIC_ADMIN.getValue()).build();

    // when
    this.mvc
        .perform(
            patch(ROOT_PATH + "/" + EXISTING_ID)
                .with(authentication(authentication))
                .cookie(CSRF_COOKIE)
                .header(CSRF_HEADER, CSRF_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultingTypeDTO)))
        // then
        .andExpect(status().isForbidden());
  }

  @Test
  void
      getConsultingTypeByTenantId_Should_returnNoContent_When_CalledAsTenantAdminButNoConsultingTypeIfFound()
          throws Exception {
    // given
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    Authentication authentication =
        new AuthenticationMockBuilder().withUserRole(TENANT_ADMIN.getValue()).build();
    // when

    mvc.perform(
            MockMvcRequestBuilders.get(String.format(PATH_GET_FULL_CONSULTING_TYPE_BY_TENANT, 1))
                .with(authentication(authentication))
                .accept(MediaType.APPLICATION_JSON)
                .cookie(CSRF_COOKIE)
                .header(CSRF_HEADER, CSRF_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void createConsultingType_Should_return_forbidden_When_userNotHaveRequiredAuthority()
      throws Exception {
    // given
    ConsultingTypeDTO consultingTypeDTO = easyRandom.nextObject(ConsultingTypeDTO.class);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    Authentication authentication =
        new AuthenticationMockBuilder().withUserRole(TOPIC_ADMIN.getValue()).build();
    // when
    this.mvc
        .perform(
            post(ROOT_PATH)
                .with(authentication(authentication))
                .cookie(CSRF_COOKIE)
                .header(CSRF_HEADER, CSRF_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(consultingTypeDTO)))
        // then
        .andExpect(status().isForbidden());
  }

  private FullConsultingTypeResponseDTO createFrom(
      ConsultingTypeDTO consultingTypeDTO, Integer expectedId) {
    final ConsultingType consultingType = consultingTypeConverter.convert(consultingTypeDTO);
    consultingType.setId(expectedId);
    return ConsultingTypeMapper.mapConsultingType(
        consultingType, FullConsultingTypeMapper::mapConsultingType);
  }
}
