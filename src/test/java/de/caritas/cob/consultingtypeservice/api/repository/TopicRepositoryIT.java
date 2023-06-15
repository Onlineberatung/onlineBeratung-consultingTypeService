package de.caritas.cob.consultingtypeservice.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(properties = "spring.profiles.active=testing")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TopicRepositoryIT {

  public static final String NEW_TOPIC_NAME = "a new topic";

  @Autowired private TopicRepository topicRepository;

  @Test
  void findById_Should_findTopicById() {
    // given, when
    Optional<TopicEntity> topicEntity = topicRepository.findById(1L);
    // then
    assertThat(topicEntity).isPresent();
  }

  @Test
  void findAll_Should_findAllTopics() {
    // given, when
    var all = topicRepository.findAll();
    // then
    assertThat(all).hasSize(3);
  }

  @Test
  void findAllActive_Should_findAllActiveTopics() {
    // given, when
    var all = topicRepository.findAllActive();
    // then
    assertThat(all).hasSize(2);
  }

  @Test
  void save_Should_saveNewTopic() {
    // given
    TopicEntity topicEntity =
        TopicEntity.builder()
            .name(NEW_TOPIC_NAME)
            .description("desc")
            .createDate(LocalDateTime.now())
            .build();
    var savedTopicEntity = topicRepository.save(topicEntity);
    // then
    assertThat(savedTopicEntity).isNotNull();
    assertThat(topicRepository.findByName(NEW_TOPIC_NAME)).isPresent();
    assertThat(topicRepository.findAll()).hasSize(4);
  }

  @Nested
  class FallbackAgencyIdTests {
    @Test
    void save_Should_saveNewTopic_withFallbackAgencyId() {
      // given
      TopicEntity topicEntity =
          TopicEntity.builder()
              .name(NEW_TOPIC_NAME)
              .description("desc")
              .createDate(LocalDateTime.now())
              .fallbackAgencyId(123)
              .build();
      var savedTopicEntity = topicRepository.save(topicEntity);
      // then
      assertThat(savedTopicEntity).isNotNull();
      val newTopic = topicRepository.findByName(NEW_TOPIC_NAME).orElseThrow();
      assertThat(newTopic.getFallbackAgencyId()).isEqualTo(123);
      assertThat(topicRepository.findAll()).hasSize(4);
    }

    @Test
    void save_Should_saveNewTopic_withoutFallbackAgencyId() {
      // given
      TopicEntity topicEntity =
          TopicEntity.builder()
              .name(NEW_TOPIC_NAME)
              .description("desc")
              .createDate(LocalDateTime.now())
              .build();
      var savedTopicEntity = topicRepository.save(topicEntity);
      // then
      assertThat(savedTopicEntity).isNotNull();
      val newTopic = topicRepository.findByName(NEW_TOPIC_NAME).orElseThrow();
      assertThat(newTopic.getFallbackAgencyId()).isNull();
      assertThat(topicRepository.findAll()).hasSize(4);
    }
  }

  @Nested
  class FallbackUrlTests {
    @Test
    void save_Should_saveNewTopic_withFallbackUrl() {
      // given
      val fallbackUrl = "https://www.caritas.de";
      val topicEntity =
          TopicEntity.builder()
              .name(NEW_TOPIC_NAME)
              .description("desc")
              .createDate(LocalDateTime.now())
              .fallbackUrl(fallbackUrl)
              .build();
      var savedTopicEntity = topicRepository.save(topicEntity);
      // then
      assertThat(savedTopicEntity).isNotNull();
      val newTopic = topicRepository.findByName(NEW_TOPIC_NAME).orElseThrow();
      assertThat(newTopic.getFallbackUrl()).isEqualTo(fallbackUrl);
      assertThat(topicRepository.findAll()).hasSize(4);
    }

    @Test
    void save_Should_saveNewTopic_withoutFallbackUrl() {
      // given
      TopicEntity topicEntity =
          TopicEntity.builder()
              .name(NEW_TOPIC_NAME)
              .description("desc")
              .createDate(LocalDateTime.now())
              .build();
      var savedTopicEntity = topicRepository.save(topicEntity);
      // then
      assertThat(savedTopicEntity).isNotNull();
      val newTopic = topicRepository.findByName(NEW_TOPIC_NAME).orElseThrow();
      assertThat(newTopic.getFallbackUrl()).isNull();
      assertThat(topicRepository.findAll()).hasSize(4);
    }
  }

  @Nested
  class WelcomeMessageTests {
    @Test
    void save_Should_saveNewTopic_withWelcomeMessage() {
      // given
      val welcomeMessage = "welcome";
      val topicEntity =
          TopicEntity.builder()
              .name(NEW_TOPIC_NAME)
              .description("desc")
              .createDate(LocalDateTime.now())
              .welcomeMessage(welcomeMessage)
              .build();
      var savedTopicEntity = topicRepository.save(topicEntity);
      // then
      assertThat(savedTopicEntity).isNotNull();
      val newTopic = topicRepository.findByName(NEW_TOPIC_NAME).orElseThrow();
      assertThat(newTopic.getWelcomeMessage()).isEqualTo(welcomeMessage);
      assertThat(topicRepository.findAll()).hasSize(4);
    }

    @Test
    void save_Should_saveNewTopic_withoutWelcomeMessage() {
      // given
      val topicEntity =
          TopicEntity.builder()
              .name(NEW_TOPIC_NAME)
              .description("desc")
              .createDate(LocalDateTime.now())
              .build();
      var savedTopicEntity = topicRepository.save(topicEntity);
      // then
      assertThat(savedTopicEntity).isNotNull();
      val newTopic = topicRepository.findByName(NEW_TOPIC_NAME).orElseThrow();
      assertThat(newTopic.getWelcomeMessage()).isNull();
      assertThat(topicRepository.findAll()).hasSize(4);
    }
  }
}
