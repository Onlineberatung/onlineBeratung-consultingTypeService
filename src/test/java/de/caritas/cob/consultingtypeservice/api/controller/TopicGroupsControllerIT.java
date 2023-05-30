package de.caritas.cob.consultingtypeservice.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.auth.UserRole;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import de.caritas.cob.consultingtypeservice.api.service.TopicGroupService;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class TopicGroupsControllerIT {

  private MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @MockBean TopicGroupService topicGroupService;

  @BeforeEach
  public void setup() {
    TenantContext.clear();
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  void getAllTopicGroups_Should_ReturnTopicGroupsDTO_When_UserIsAuthenticated() throws Exception {
    /* arrange */
    var tge1 = new TopicGroupEntity();
    tge1.setId(1L);
    tge1.setName("1");
    var te1 = new TopicEntity();
    te1.setId(1L);
    var te2 = new TopicEntity();
    te2.setId(2L);
    tge1.setTopicEntities(Set.of(te1, te2));

    var tge2 = new TopicGroupEntity();
    tge2.setId(2L);
    tge2.setName("2");
    var te3 = new TopicEntity();
    te3.setId(3L);
    var te4 = new TopicEntity();
    te4.setId(4L);
    tge2.setTopicEntities(Set.of(te3, te4));
    when(topicGroupService.getAllTopicGroups()).thenReturn(List.of(tge1, tge2));

    final AuthenticationMockBuilder builder = new AuthenticationMockBuilder();
    /* act */
    mockMvc
        .perform(
            get("/topic-groups")
                .with(authentication(builder.withUserRole(UserRole.TOPIC_ADMIN.getValue()).build()))
                .accept(MediaType.APPLICATION_JSON))
        /* assert */
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{\"data\":{\"items\":[{\"id\":1,\"name\":\"1\",\"topicIds\":[2,1]},{\"id\":2,\"name\":\"2\",\"topicIds\":[4,3]}]}}"));
  }

  @Test
  void getTopicList_Should_ReturnForbidden_When_UserIsNotAuthenticated() throws Exception {
    /* arrange, act */
    mockMvc
        .perform(get("/topic-groups").accept(MediaType.APPLICATION_JSON))
        /* assert */
        .andExpect(status().isForbidden());
  }
}
