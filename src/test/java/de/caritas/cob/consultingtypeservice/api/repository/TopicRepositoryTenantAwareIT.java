package de.caritas.cob.consultingtypeservice.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource(properties = "spring.profiles.active=testing")
@TestPropertySource(properties = "multitenancy.enabled=true")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class TopicRepositoryTenantAwareIT {

  @Autowired private TopicRepository topicRepository;

  @Test
  void findByIdForTenant_Should_findTopicByIdAndTenantId() {
    // given, when
    Optional<TopicEntity> topicEntity = topicRepository.findByIdForTenant(1L, 1L);
    // then
    assertThat(topicEntity).isPresent();
  }

  @Test
  void findByIdForTenant_Should_notFindTopicByIdWhenTenantDoesNotMatch() {
    // given, when
    Optional<TopicEntity> topicEntity = topicRepository.findByIdForTenant(1L, 2L);
    // then
    assertThat(topicEntity).isNotPresent();
  }

  @Test
  void findAllForTenant_Should_findAllTopicsForTenantId() {
    // given, when
    var all = topicRepository.findAllForTenant(1L);
    // then
    assertThat(all).hasSize(2);
  }

  @Test
  void findAllActiveForTenant_Should_findAllActiveTopicsForTenantId() {
    // given, when
    var all = topicRepository.findAllActiveForTenant(1L);
    // then
    assertThat(all).hasSize(1);
  }
}
