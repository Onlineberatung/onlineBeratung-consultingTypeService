package de.caritas.cob.consultingtypeservice.api.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
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
@Disabled
class TopicRepositoryTest {
  private static final long EXISTING_ID = 1L;

  @Autowired
  private TopicRepository topicRepository;

  @Test
  void findById_Should_findTopicById() {
    // given, when
    Optional<TopicEntity> topicEntity = topicRepository.findById(1L);
    // then
    assertThat(topicEntity).isPresent();
  }
}