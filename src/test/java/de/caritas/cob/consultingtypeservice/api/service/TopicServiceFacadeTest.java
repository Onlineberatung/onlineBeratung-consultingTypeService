package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceFacadeTest {

  @Mock
  TopicService topicService;

  @Mock
  TopicConverter topicConverter;

  @InjectMocks
  TopicServiceFacade topicServiceFacade;


  @Test
  void getAllTopics_Should_CallServiceAndConvertResults() {
    // given
    var topicEntity1 = TopicEntity.builder().name("a name").build();
    var topicEntity2 = new TopicEntity();

    when(topicService.getAllTopics()).thenReturn(newArrayList(topicEntity1, topicEntity2));
    // when
    topicServiceFacade.getAllTopics();

    // then
    verify(topicService).getAllTopics();
    verify(topicConverter).toDTO(topicEntity1);
    verify(topicConverter).toDTO(topicEntity2);
  }

  @Test
  void createTopic_Should_CallServiceAndPerformConversions() {
    // given
    var topicDTO = new TopicDTO();
    var topicEntity = new TopicEntity();
    when(topicConverter.toEntity(topicDTO)).thenReturn(topicEntity);
    var createdTopicEntity = new TopicEntity();
    when(topicService.createTopic(topicEntity)).thenReturn(createdTopicEntity);

    // when
    topicServiceFacade.createTopic(topicDTO);

    // then
    verify(topicConverter).toEntity(topicDTO);
    verify(topicService).createTopic(topicEntity);
    verify(topicConverter).toDTO(createdTopicEntity);
  }
}