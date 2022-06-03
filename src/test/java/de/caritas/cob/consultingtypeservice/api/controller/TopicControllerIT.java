package de.caritas.cob.consultingtypeservice.api.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.authorisation.UserRole;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.util.JsonConverter;
import de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@AutoConfigureMockMvc(addFilters = false)
class TopicControllerIT {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @Test
  void getTopicList_Should_ReturnTopicsList_When_UserIsAuthenticated()
      throws Exception {
    AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc.perform(
            get(TopicPathConstants.PATH_GET_TOPIC_LIST)
                .with(authentication(builder.withAuthority(UserRole.TOPIC_ADMIN.getValue()).build()))
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
  void createTopic_Should_returnStatusOk_When_calledWithValidCreateParamsAndValidAuthority()
      throws Exception {
    EasyRandom easyRandom = new EasyRandom();
    String payload = JsonConverter.convert(easyRandom.nextObject(TopicDTO.class));
    AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc.perform(post(TopicPathConstants.ROOT_PATH)
            .with(authentication(builder.withAuthority(UserRole.TOPIC_ADMIN.getValue()).build()))
            .contentType(APPLICATION_JSON)
            .content(payload)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.description").exists())
        .andExpect(jsonPath("$.status").exists())
        .andExpect(jsonPath("$.createDate").exists());
  }

  @Test
  void createTopic_Should_returnBadRequest_When_calledWithValidCreateParamsAndValidAuthorityButContentNotValid()
      throws Exception {
    EasyRandom easyRandom = new EasyRandom();
    TopicDTO topicDTO = easyRandom.nextObject(TopicDTO.class);
    topicDTO.setStatus("a very very long status");
    String payload = JsonConverter.convert(topicDTO);
    AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc.perform(post(TopicPathConstants.ROOT_PATH)
            .with(authentication(builder.withAuthority(UserRole.TOPIC_ADMIN.getValue()).build()))
            .contentType(APPLICATION_JSON)
            .content(payload)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createTopic_Should_returnForbidden_When_calledWithValidCreateParamsButAsUnauthenticatedUser()
      throws Exception {
    EasyRandom easyRandom = new EasyRandom();
    TopicDTO topicDTO = easyRandom.nextObject(TopicDTO.class);
    String payload = JsonConverter.convert(topicDTO);
    mockMvc.perform(post(TopicPathConstants.ROOT_PATH)
            .content(payload)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void createTopic_Should_returnForbidden_When_calledWithValidCreateParamsButWithoutTopicAdminRole()
      throws Exception {
    EasyRandom easyRandom = new EasyRandom();
    TopicDTO topicDTO = easyRandom.nextObject(TopicDTO.class);
    String payload = JsonConverter.convert(topicDTO);
    AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    mockMvc.perform(post(TopicPathConstants.ROOT_PATH)
            .with(authentication(builder.withAuthority("another-authority").build()))
            .content(payload)
            .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void getTopicList_Should_ReturnForbidden_When_UserIsNotAuthenticated()
      throws Exception {
    mockMvc.perform(
            get(TopicPathConstants.PATH_GET_TOPIC_LIST)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void save_Should_ReturnForbidden_IfUserIsAuthenticatedButDoesNotHavePermission()
      throws Exception {
    mockMvc.perform(
            post(TopicPathConstants.PATH_GET_TOPIC_LIST)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }
}
