package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants.PATH_GET_PUBLIC_TOPIC_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.auth.UserRole;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.service.TenantService;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.Settings;
import de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@TestPropertySource(properties = "multitenancy.enabled=false")
@TestPropertySource(properties = "feature.multitenancy.with.single.domain.enabled=true")
@AutoConfigureMockMvc(addFilters = false)
class TopicControllerIT {

  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @MockBean TenantService tenantService;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    TenantContext.clear();
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    givenTopicFeatureEnabled(false);
  }

  private void givenTopicFeatureEnabled(boolean topicFeatureEnabled) {
    Mockito.when(tenantService.getRestrictedTenantDataBySubdomain("app"))
        .thenReturn(
            new RestrictedTenantDTO()
                .settings(new Settings().featureTopicsEnabled(topicFeatureEnabled)));
  }

  @Test
  void getTopicList_Should_ReturnTopicsList_When_UserIsAuthenticated() throws Exception {
    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc
        .perform(
            get(TopicPathConstants.PATH_GET_TOPIC_LIST)
                .with(authentication(builder.withUserRole(UserRole.TOPIC_ADMIN.getValue()).build()))
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
  void getAllActiveTopics_Should_returnActiveTopicsList() throws Exception {
    TenantContext.clear();
    mockMvc
        .perform(get(PATH_GET_PUBLIC_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].description").exists())
        .andExpect(jsonPath("$[0].status").exists())
        .andExpect(jsonPath("$[0].createDate").exists());
  }

  @Test
  void getAllActiveTopics_Should_returnActiveTopicsListWithOrWithoutFallbackAgencyId()
      throws Exception {
    TenantContext.clear();
    val result =
        mockMvc
            .perform(get(PATH_GET_PUBLIC_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    val topics =
        objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<TopicDTO>>() {});
    assertThat(
            topics.stream()
                .filter(t -> t.getId() == 1)
                .findFirst()
                .orElseThrow()
                .getFallbackAgencyId())
        .isEqualTo(1);
    assertThat(
            topics.stream()
                .filter(t -> t.getId() == 3)
                .findFirst()
                .orElseThrow()
                .getFallbackAgencyId())
        .isNull();
  }

  @Test
  void getTopicList_Should_ReturnForbidden_When_UserIsNotAuthenticated() throws Exception {
    mockMvc
        .perform(get(TopicPathConstants.PATH_GET_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void getAllActiveTopics_Should_returnActiveTopicsListWithOrWithoutFallbackUrl() throws Exception {
    TenantContext.clear();
    val result =
        mockMvc
            .perform(get(PATH_GET_PUBLIC_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    val topics =
        objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<TopicDTO>>() {});
    val topicWithOptionalFieldsPresent =
        topics.stream().filter(t -> t.getId() == 1).findFirst().orElseThrow();
    assertThat(topicWithOptionalFieldsPresent.getFallbackAgencyId()).isEqualTo(1);
    assertThat(topicWithOptionalFieldsPresent.getFallbackUrl()).isEqualTo("https://www.google.com");
    val topicWithoutOptionalFieldsPresent =
        topics.stream().filter(t -> t.getId() == 3).findFirst().orElseThrow();
    assertThat(topicWithoutOptionalFieldsPresent.getFallbackAgencyId()).isNull();
    assertThat(topicWithoutOptionalFieldsPresent.getFallbackUrl()).isNull();
  }

  @Test
  void getAllActiveTopics_Should_returnActiveTopicsListWithOrWithoutWelcomeMessage()
      throws Exception {
    TenantContext.clear();
    val result =
        mockMvc
            .perform(get(PATH_GET_PUBLIC_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    val topics =
        objectMapper.readValue(
            result.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<TopicDTO>>() {});
    val topicWithWelcomeMessage =
        topics.stream().filter(t -> t.getId() == 1).findFirst().orElseThrow();
    assertThat(topicWithWelcomeMessage.getWelcomeMessage()).isEqualTo("Welcome");
    val topicWithoutWelcomeMessage =
        topics.stream().filter(t -> t.getId() == 3).findFirst().orElseThrow();
    assertThat(topicWithoutWelcomeMessage.getWelcomeMessage()).isNull();
  }
}
