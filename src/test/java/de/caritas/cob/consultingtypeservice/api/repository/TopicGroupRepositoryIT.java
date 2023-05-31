package de.caritas.cob.consultingtypeservice.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  void setup() {
    topicGroupRepository.deleteAll();
    topicRepository.deleteAll();
  }

  @Test
  void topicGroupRepositoryReturnsEmptyListForNoTopicGroups() {
    // when
    val topicGroups = topicGroupRepository.findAll();
    // then
    assertThat(topicGroups).isEmpty();
  }

  @Test
  void topicGroupRepositoryReturnsSomeMeaningfulData() {
    val now = LocalDateTime.now();
    val te1 = TopicEntity.builder().name("te1").createDate(now).updateDate(now).build();
    val te2 = TopicEntity.builder().name("te2").createDate(now).updateDate(now).build();

    topicRepository.saveAllAndFlush(List.of(te1, te2));

    var tg1 =
        TopicGroupEntity.builder()
            .name("tg1")
            .createDate(now)
            .updateDate(now)
            .topicEntities(Set.of(te1, te2))
            .build();

    topicGroupRepository.saveAllAndFlush(List.of(tg1));

    /* when */
    val topicGroups = topicGroupRepository.findAll();

    /* then */

    tg1 = getTopicGroupEntityByName(topicGroups, "tg1");

    assertThat(tg1.getId()).isNotNull();
    assertThat(tg1.getName()).isEqualTo("tg1");
    assertThat(tg1.getCreateDate()).isEqualTo(now);
    assertThat(tg1.getUpdateDate()).isEqualTo(now);
    assertThat(tg1.getTopicEntities()).hasSize(2).contains(te1, te2);
  }

  @Test
  void deletingTopicGroupKeepsTopicsUntouched() {
    /* given */
    val now = LocalDateTime.now();
    val te1 = TopicEntity.builder().name("te1").createDate(now).updateDate(now).build();
    val te2 = TopicEntity.builder().name("te2").createDate(now).updateDate(now).build();
    val te3 = TopicEntity.builder().name("te3").createDate(now).updateDate(now).build();
    val te4 = TopicEntity.builder().name("te4").createDate(now).updateDate(now).build();

    topicRepository.saveAllAndFlush(List.of(te1, te2, te3, te4));

    val tg1 =
        TopicGroupEntity.builder()
            .name("tg1")
            .createDate(now)
            .updateDate(now)
            .topicEntities(Set.of(te1, te2))
            .build();
    val tg2 =
        TopicGroupEntity.builder()
            .name("tg2")
            .createDate(now)
            .updateDate(now)
            .topicEntities(Set.of(te3, te4))
            .build();

    topicGroupRepository.saveAllAndFlush(List.of(tg1, tg2));

    val allTopics = topicRepository.findAll();
    val allTopicGroups = topicGroupRepository.findAll();

    assertThat(allTopics).hasSize(4);
    assertThat(allTopicGroups).hasSize(2);

    topicGroupShouldContainTopics(allTopicGroups, "tg1", te1, te2);
    topicGroupShouldContainTopics(allTopicGroups, "tg2", te3, te4);

    /* when */
    topicGroupRepository.delete(tg1);

    /* then */
    assertThat(topicGroupRepository.findAll()).containsOnly(tg2);
    assertThat(topicRepository.findAll()).containsOnly(te1, te2, te3, te4);

    topicGroupShouldContainTopics(topicGroupRepository.findAll(), "tg2", te3, te4);
  }

  @Test
  void deletingTopicGetsItRemovedFromGroup() {
    /* given */
    val now = LocalDateTime.now();
    val te1 = TopicEntity.builder().name("te1").createDate(now).updateDate(now).build();
    val te2 = TopicEntity.builder().name("te2").createDate(now).updateDate(now).build();

    topicRepository.saveAllAndFlush(List.of(te1, te2));

    val tg1 =
        TopicGroupEntity.builder()
            .name("tg1")
            .createDate(now)
            .updateDate(now)
            .topicEntities(Set.of(te1, te2))
            .build();

    topicGroupRepository.saveAndFlush(tg1);

    val allTopics = topicRepository.findAll();
    val allTopicGroups = topicGroupRepository.findAll();

    assertThat(allTopics).hasSize(2);
    assertThat(allTopicGroups).hasSize(1);

    topicGroupShouldContainTopics(allTopicGroups, "tg1", te1, te2);

    /* when */
    topicRepository.delete(te1);

    /* then */
    assertThat(topicGroupRepository.findAll()).containsOnly(tg1);
    assertThat(topicRepository.findAll()).containsOnly(te2);
  }

  private void topicGroupShouldContainTopics(
      List<TopicGroupEntity> topicGroups, String topicGroupName, TopicEntity... topics) {
    val topicGroup = getTopicGroupEntityByName(topicGroups, topicGroupName);
    assertThat(topicGroup.getTopicEntities()).hasSize(topics.length).contains(topics);
  }

  private static TopicGroupEntity getTopicGroupEntityByName(
      List<TopicGroupEntity> topicGroups, String topicGroupName) {
    return topicGroups.stream()
        .filter(topicGroupEntity -> topicGroupEntity.getName().equals(topicGroupName))
        .findFirst()
        .orElseThrow();
  }
}
