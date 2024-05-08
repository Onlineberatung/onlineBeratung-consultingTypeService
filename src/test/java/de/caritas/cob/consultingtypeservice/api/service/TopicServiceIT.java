package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@Testcontainers
@Sql(
    scripts = "classpath:database/TopicDatabase.sql",
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class TopicServiceIT {

  @Autowired TopicService topicService;

  @Container
  static MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:6.0"));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void getAllTopics_Should_returnAllTopics() {
    // given, when
    var topicEntities = topicService.getAllTopics();

    // then
    assertThat(topicEntities).hasSize(3).extracting(TopicEntity::getId).contains(1L, 2L, 3L);
  }

  @Test
  void getAllActiveTopics_Should_returnAllActiveTopics() {
    // given, when
    var allActiveTopicEntities = topicService.getAllActiveTopics();

    // then
    assertThat(allActiveTopicEntities).hasSize(2).extracting(TopicEntity::getId).contains(1L, 3L);
  }

  @Test
  void findTopicById_Should_returnRequestedTopic() {
    // given, when
    var topicById = topicService.findTopicById(1L);

    // then
    assertThat(topicById).isPresent().get().extracting(TopicEntity::getId).isEqualTo(1L);
  }

  @Test
  void findTopicById_Should_notBePresent_When_theTopicNotExists() {
    // given, when
    var topicById = topicService.findTopicById(4L);

    // then
    assertThat(topicById).isNotPresent();
  }
}
