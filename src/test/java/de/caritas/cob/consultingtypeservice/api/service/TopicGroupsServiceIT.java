package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import de.caritas.cob.consultingtypeservice.api.repository.TopicGroupRepository;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
class TopicGroupsServiceIT {

  @Autowired TopicGroupService topicGroupService;

  @Autowired TopicGroupRepository topicGroupRepository;

  @Autowired TopicRepository topicRepository;

  @Test
  @Transactional
  void getAllTopicGroups_Should_returnAllTopicGroups() {
    // given, when
    val now = LocalDateTime.now();
    val te1 = TopicEntity.builder().name("te1").createDate(now).updateDate(now).build();
    val te2 = TopicEntity.builder().name("te2").createDate(now).updateDate(now).build();

    topicRepository.saveAllAndFlush(List.of(te1, te2));

    val topicGroupEntity =
        new TopicGroupEntity(
            1L, "topicGroup1", LocalDateTime.now(), LocalDateTime.now(), Set.of(te1, te2));
    topicGroupRepository.saveAndFlush(topicGroupEntity);

    val allTopicGroups = topicGroupService.getAllTopicGroups();
    // then
    assertThat(allTopicGroups).hasSize(1);
    val firstTopicGroup = allTopicGroups.iterator().next();
    assertThat(firstTopicGroup.getName()).isEqualTo("topicGroup1");
    val firstTopicGroupTopics = firstTopicGroup.getTopicEntities();
    assertThat(firstTopicGroupTopics).hasSize(2);
    assertThat(firstTopicGroupTopics).extracting("name").containsExactlyInAnyOrder("te1", "te2");
  }
}
