package de.caritas.cob.consultingtypeservice.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import java.time.LocalDateTime;
import java.util.List;
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
    // when
    val topicGroups = topicGroupRepository.findAll();
    // then
    assertThat(topicGroups).isEmpty();
  }

  @Test
  void topicGroupRepositoryReturnsSomeMeaningfulData() {
    // given
    val topics = topicRepository.findAll();
    val now = LocalDateTime.now();
    topicGroupRepository.saveAllAndFlush(
        List.of(
            TopicGroupEntity.builder()
                .name("tg1")
                .topicEntities(Set.of(topics.get(0), topics.get(1)))
                .createDate(now)
                .updateDate(now)
                .build(),
            TopicGroupEntity.builder()
                .name("tg2")
                .topicEntities(Set.of(topics.get(2)))
                .createDate(now)
                .updateDate(now)
                .build()));

    /* when */
    val topicGroups = topicGroupRepository.findAll();

    /* then */

    val tg1 = getTopicGroupEntityByName(topicGroups, "tg1");

    assertThat(tg1.getId()).isNotNull();
    assertThat(tg1.getName()).isEqualTo("tg1");
    assertThat(tg1.getCreateDate()).isEqualTo(now);
    assertThat(tg1.getUpdateDate()).isEqualTo(now);
    assertThat(tg1.getTopicEntities()).hasSize(2).contains(topics.get(0), topics.get(1));

    val tg2 = getTopicGroupEntityByName(topicGroups, "tg2");

    assertThat(tg2.getId()).isNotNull();
    assertThat(tg2.getName()).isEqualTo("tg2");
    assertThat(tg2.getCreateDate()).isEqualTo(now);
    assertThat(tg2.getUpdateDate()).isEqualTo(now);
    assertThat(tg2.getTopicEntities()).hasSize(1).contains(topics.get(2));
  }

  private static TopicGroupEntity getTopicGroupEntityByName(
      List<TopicGroupEntity> topicGroups, String topicGroupName) {
    return topicGroups.stream()
        .filter(topicGroupEntity -> topicGroupEntity.getName().equals(topicGroupName))
        .findFirst()
        .orElseThrow();
  }
}
