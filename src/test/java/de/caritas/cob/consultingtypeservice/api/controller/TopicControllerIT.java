package de.caritas.cob.consultingtypeservice.api.controller;

import static de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants.PATH_GET_PUBLIC_TOPIC_LIST;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.auth.UserRole;
import de.caritas.cob.consultingtypeservice.api.service.TenantService;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.Settings;
import de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants;
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
  void getTopicList_Should_ReturnForbidden_When_UserIsNotAuthenticated() throws Exception {
    mockMvc
        .perform(get(TopicPathConstants.PATH_GET_TOPIC_LIST).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }
}
