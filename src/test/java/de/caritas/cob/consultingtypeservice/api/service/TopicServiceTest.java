package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

  @InjectMocks TopicService topicService;

  @Mock TopicRepository topicRepository;

  @AfterEach
  void tearDown() {
    TenantContext.clear();
  }

  @Test
  void getAllTopics_Should_CallRepository() {
    // given, when
    TenantContext.clear();
    topicService.getAllTopics();
    // then
    verify(topicRepository).findAll();
  }

  @Test
  void getAllActiveTopics_Should_callRepository() {
    // given, when
    TenantContext.clear();
    topicService.getAllActiveTopics();
    // Then
    verify(topicRepository).findAllActive();
  }

  @Test
  void createTopics_Should_TakeTopicStatusFromRequest() {
    // given, when
    TopicEntity topicEntity = new TopicEntity();
    topicEntity.setStatus(TopicStatus.INACTIVE);
    topicService.createTopic(topicEntity);
    // then
    verify(topicRepository).save(topicEntity);
    assertThat(topicEntity.getCreateDate()).isNotNull();
    assertThat(topicEntity.getStatus()).isEqualTo(TopicStatus.INACTIVE);
  }

  @Test
  void updateTopics_Should_UpdateUpdateDateAndCallRepository() {
    // given, when
    TopicEntity topicEntity = new TopicEntity();
    topicService.updateTopic(topicEntity);
    // then
    verify(topicRepository).save(topicEntity);
    assertThat(topicEntity.getUpdateDate()).isNotNull();
  }
}
