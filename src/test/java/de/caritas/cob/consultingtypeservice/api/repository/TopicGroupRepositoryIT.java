package de.caritas.cob.consultingtypeservice.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.val;
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
class TopicGroupRepositoryIT {

  @Autowired private TopicRepository topicRepository;
  @Autowired private TopicGroupRepository topicGroupRepository;

  @Test
  void topicGroupRepositoryReturnsEmptyListForNoTopicGroups() {
    val topicGroups = topicGroupRepository.findAll();
    // then
    assertThat(topicGroups).isEmpty();
  }

  @Test
  void topicGroupRepositoryReturnsSomeMeaningfulData() {
    val topics = topicRepository.findAll();
    val now = LocalDateTime.now();
    val tg1 =
        topicGroupRepository.saveAndFlush(
            TopicGroupEntity.builder()
                .name("tg1")
                .topicEntities(Set.of(topics.get(0), topics.get(1)))
                .createDate(now)
                .updateDate(now)
                .build());
    val tg2 =
        topicGroupRepository.saveAndFlush(
            TopicGroupEntity.builder()
                .name("tg2")
                .topicEntities(Set.of(topics.get(2)))
                .createDate(now)
                .updateDate(now)
                .build());
    assertThat(tg1)
        .satisfies(
            tge -> {
              assertThat(tge.getId()).isNotNull();
              assertThat(tge.getName()).isEqualTo("tg1");
              assertThat(tge.getCreateDate()).isEqualTo(now);
              assertThat(tge.getUpdateDate()).isEqualTo(now);
              assertThat(tge.getTopicEntities()).hasSize(2).contains(topics.get(0), topics.get(1));
            });
    assertThat(tg2)
        .satisfies(
            tge -> {
              assertThat(tge.getId()).isNotNull();
              assertThat(tge.getName()).isEqualTo("tg2");
              assertThat(tge.getCreateDate()).isEqualTo(now);
              assertThat(tge.getUpdateDate()).isEqualTo(now);
              assertThat(tge.getTopicEntities()).hasSize(1).contains(topics.get(2));
            });
  }
}
