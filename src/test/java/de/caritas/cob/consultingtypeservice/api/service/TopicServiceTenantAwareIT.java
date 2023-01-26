package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@TestPropertySource(properties = "multitenancy.enabled=true")
@TestPropertySource(
    properties =
        "consulting.types.json.path=src/test/resources/consulting-type-settings-tenant-specific")
class TopicServiceTenantAwareIT {

  @Autowired TopicService topicService;

  @BeforeEach
  void setup() {
    TenantContext.setCurrentTenant(1L);
  }

  @AfterEach
  void cleanUp() {
    TenantContext.clear();
  }

  @Test
  void getAllTopics_Should_returnAllTopicsForTheCurrentTenant() {
    // given, when
    var topicEntities = topicService.getAllTopics();

    // then
    assertThat(topicEntities).hasSize(2).extracting(TopicEntity::getId).contains(1L, 2L);
  }

  @Test
  void getAllActiveTopics_Should_returnAllActiveTopicsForTheCurrentTenant() {
    // given, when
    var allActiveTopicEntities = topicService.getAllActiveTopics();

    // then
    assertThat(allActiveTopicEntities).hasSize(1).extracting(TopicEntity::getId).contains(1L);
  }

  @Test
  void findTopicById_Should_returnRequestedTopicForTheCurrentTenant() {
    // given, when
    var topicById = topicService.findTopicById(1L);

    // then
    assertThat(topicById).isPresent().get().extracting(TopicEntity::getId).isEqualTo(1L);
  }

  @Test
  void findTopicById_Should_notBePresent_When_theTopicIsNotOwnedByTheCurrentTenant() {
    // given, when
    var topicById = topicService.findTopicById(3L);

    // then
    assertThat(topicById).isNotPresent();
  }

  @Test
  void findTopicById_Should_notBePresent_When_theTopicNotExists() {
    // given, when
    var topicById = topicService.findTopicById(4L);

    // then
    assertThat(topicById).isNotPresent();
  }
}
