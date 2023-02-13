package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.api.auth.UserRole.TOPIC_ADMIN;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.auth.UserRole;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.service.TenantService;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.api.util.JsonConverter;
import de.caritas.cob.consultingtypeservice.api.util.MultilingualTopicTestDataBuilder;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.Settings;
import de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants;
import java.util.Map;
import org.assertj.core.util.Maps;
import org.assertj.core.util.Sets;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "feature.multitenancy.with.single.domain.enabled=true")
class TopicAdminControllerIT {

  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @MockBean TenantService tenantService;

  @BeforeEach
  public void setup() {
    TenantContext.clear();
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    givenTopicFeatureEnabled(true);
  }

  private void givenTopicFeatureEnabled(boolean topicFeatureEnabled) {
    Mockito.when(tenantService.getRestrictedTenantDataBySubdomainNoCache("app"))
        .thenReturn(
            new RestrictedTenantDTO()
                .settings(new Settings().featureTopicsEnabled(topicFeatureEnabled)));
  }

  @Test
  void
      createTopic_Should_returnStatusBadRequest_When_calledWithInvalidCreateParamsButValidAuthority()
          throws Exception {
    final EasyRandom easyRandom = new EasyRandom();
    final TopicMultilingualDTO topicDTO = easyRandom.nextObject(TopicMultilingualDTO.class);
    topicDTO.setStatus("invalid status");
    final String payload = JsonConverter.convertToJson(topicDTO);
    AuthenticationMockBuilder builder = new AuthenticationMockBuilder();

    mockMvc
        .perform(
            post(TopicPathConstants.ADMIN_ROOT_PATH)
                .with(authentication(builder.withUserRole(TOPIC_ADMIN.getValue()).build()))
                .contentType(APPLICATION_JSON)
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTopic_Should_returnStatusOk_When_calledWithValidCreateParamsAndValidAuthority()
      throws Exception {
    final String payload =
        new MultilingualTopicTestDataBuilder()
            .topicDTO()
            .withName("new name")
            .withDescription("new desc")
            .withInternalIdentifier("new ident")
            .withStatus(TopicStatus.INACTIVE.toString())
            .jsonify();

    final Authentication authentication = givenMockAuthentication(UserRole.TOPIC_ADMIN);
    mockMvc
        .perform(
            put(String.format(TopicPathConstants.PATH_PUT_TOPIC_BY_ID, "1"))
                .with(authentication(authentication))
                .contentType(APPLICATION_JSON)
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name['de']").value("new name"))
        .andExpect(jsonPath("$.description['de']").value("new desc"))
        .andExpect(jsonPath("$.internalIdentifier").value("new ident"))
        .andExpect(jsonPath("$.status").value(TopicStatus.INACTIVE.toString()))
        .andExpect(jsonPath("$.updateDate").exists())
        .andExpect(jsonPath("$.createDate").exists());
  }

  @Test
  void updateTopic_Should_returnStatusOk_When_featureToggleIsDisabled() throws Exception {
    givenTopicFeatureEnabled(false);
    final String payload =
        new MultilingualTopicTestDataBuilder()
            .topicDTO()
            .withName("new name")
            .withDescription("new desc")
            .withInternalIdentifier("new ident")
            .withStatus(TopicStatus.INACTIVE.toString())
            .jsonify();

    final Authentication authentication = givenMockAuthentication(UserRole.TOPIC_ADMIN);
    mockMvc
        .perform(
            put(String.format(TopicPathConstants.PATH_PUT_TOPIC_BY_ID, "1"))
                .with(authentication(authentication))
                .contentType(APPLICATION_JSON)
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void
      createTopic_Should_returnBadRequest_When_calledWithValidCreateParamsAndValidAuthorityButContentNotValid()
          throws Exception {
    final EasyRandom easyRandom = new EasyRandom();
    final TopicMultilingualDTO topicDTO = easyRandom.nextObject(TopicMultilingualDTO.class);
    topicDTO.setStatus("a very very long status");
    final String payload = JsonConverter.convertToJson(topicDTO);
    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc
        .perform(
            post(TopicPathConstants.ADMIN_ROOT_PATH)
                .with(authentication(builder.withUserRole(TOPIC_ADMIN.getValue()).build()))
                .contentType(APPLICATION_JSON)
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTopic_Should_returnForbidden_When_calledWithValidCreateParamsButAsUnauthenticatedUser()
      throws Exception {
    final EasyRandom easyRandom = new EasyRandom();
    final TopicMultilingualDTO topicDTO = easyRandom.nextObject(TopicMultilingualDTO.class);
    final String payload = JsonConverter.convertToJson(topicDTO);
    mockMvc
        .perform(
            post(TopicPathConstants.ADMIN_ROOT_PATH).content(payload).contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void createTopic_Should_returnForbidden_When_calledWithValidCreateParamsButWithoutTopicAdminRole()
      throws Exception {
    final EasyRandom easyRandom = new EasyRandom();
    final TopicMultilingualDTO topicDTO = easyRandom.nextObject(TopicMultilingualDTO.class);
    topicDTO.setStatus(TopicStatus.ACTIVE.toString());
    final String payload = JsonConverter.convertToJson(topicDTO);
    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc
        .perform(
            post(TopicPathConstants.ADMIN_ROOT_PATH)
                .with(authentication(builder.withUserRole("another-authority").build()))
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void createTopic_Should_returnStatusOk_When_calledWithValidCreateParamsAndValidAuthority()
      throws Exception {
    final EasyRandom easyRandom = new EasyRandom();
    final TopicMultilingualDTO topicDTO = easyRandom.nextObject(TopicMultilingualDTO.class);
    topicDTO.setStatus(TopicStatus.INACTIVE.toString());
    final String payload = JsonConverter.convertToJson(topicDTO);
    final Authentication authentication = givenMockAuthentication(UserRole.TOPIC_ADMIN);
    mockMvc
        .perform(
            post(TopicPathConstants.ADMIN_ROOT_PATH)
                .with(authentication(authentication))
                .contentType(APPLICATION_JSON)
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.description").exists())
        .andExpect(jsonPath("$.status").value("INACTIVE"))
        .andExpect(jsonPath("$.internalIdentifier").exists())
        .andExpect(jsonPath("$.createDate").exists());
  }

  @Test
  void createTopic_Should_returnStatusForbidden_When_topicToggleIsDisabled() throws Exception {
    givenTopicFeatureEnabled(false);
    final EasyRandom easyRandom = new EasyRandom();
    final TopicMultilingualDTO topicDTO = easyRandom.nextObject(TopicMultilingualDTO.class);
    topicDTO.setStatus(TopicStatus.INACTIVE.toString());
    final String payload = JsonConverter.convertToJson(topicDTO);
    final Authentication authentication = givenMockAuthentication(UserRole.TOPIC_ADMIN);
    mockMvc
        .perform(
            post(TopicPathConstants.ADMIN_ROOT_PATH)
                .with(authentication(authentication))
                .contentType(APPLICATION_JSON)
                .content(payload)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void save_Should_ReturnForbidden_IfUserIsAuthenticatedButDoesNotHavePermission()
      throws Exception {
    mockMvc
        .perform(post(TopicPathConstants.ADMIN_ROOT_PATH).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void getAllTopics_Should_ReturnTopicsList_When_UserIsAuthenticated() throws Exception {
    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc
        .perform(
            get(TopicPathConstants.ADMIN_PATH_GET_TOPIC_LIST)
                .with(authentication(builder.withUserRole(TOPIC_ADMIN.getValue()).build()))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(greaterThan(1))))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].description").exists())
        .andExpect(jsonPath("$[0].status").exists())
        .andExpect(jsonPath("$[0].createDate").exists());
  }

  @Test
  void getAllTopics_Should_ReturnForbiddenIfTopicFeatureIsDisabled() throws Exception {
    givenTopicFeatureEnabled(false);
    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc
        .perform(
            get(TopicPathConstants.ADMIN_PATH_GET_TOPIC_LIST)
                .with(authentication(builder.withUserRole(TOPIC_ADMIN.getValue()).build()))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void getTopicWithTranslationById_Should_ReturnTopic_When_UserIsAuthenticated() throws Exception {
    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc
        .perform(
            get(String.format(TopicPathConstants.ADMIN_PATH_GET_TOPIC_BY_ID, 1))
                .with(authentication(builder.withUserRole(TOPIC_ADMIN.getValue()).build()))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.description").exists())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.createDate").exists());
  }

  @Test
  void getTopicList_Should_ReturnForbidden_When_UserIsNotAuthenticated() throws Exception {
    mockMvc
        .perform(
            get(TopicPathConstants.ADMIN_PATH_GET_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  private Authentication givenMockAuthentication(final UserRole userRole) {
    final var securityContext = mock(RefreshableKeycloakSecurityContext.class);
    when(securityContext.getTokenString()).thenReturn("tokenString");
    final var token = mock(AccessToken.class, Mockito.RETURNS_DEEP_STUBS);
    when(securityContext.getToken()).thenReturn(token);
    givenOtherClaimsAreDefinedForToken(token);
    final KeycloakAccount mockAccount =
        new SimpleKeycloakAccount(() -> "user", Sets.newHashSet(), securityContext);

    Authentication authentication =
        new AuthenticationMockBuilder().withUserRole(userRole.getValue()).build();
    return new KeycloakAuthenticationToken(mockAccount, true, authentication.getAuthorities());
  }

  private void givenOtherClaimsAreDefinedForToken(final AccessToken token) {
    final Map<String, Object> claimMap = Maps.newHashMap("username", "test");
    claimMap.put("userId", "some userid");
    when(token.getOtherClaims()).thenReturn(claimMap);
  }
}
