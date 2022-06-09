package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.validation.TopicInputSanitizer;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceFacadeTest {

  private static final long TOPIC_ID = 1L;

  @Mock
  TopicService topicService;

  @Mock
  TopicConverter topicConverter;

  @Mock
  TopicInputSanitizer topicInputSanitizer;

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
    TopicDTO sanitizedDTO = new TopicDTO();
    when(topicInputSanitizer.sanitize(topicDTO)).thenReturn(sanitizedDTO);
    when(topicConverter.toEntity(topicDTO)).thenReturn(topicEntity);
    var createdTopicEntity = new TopicEntity();

    when(topicService.createTopic(topicEntity)).thenReturn(createdTopicEntity);

    // when
    topicServiceFacade.createTopic(topicDTO);

    // then
    verify(topicInputSanitizer).sanitize(topicDTO);
    verify(topicConverter).toEntity(topicDTO);
    verify(topicService).createTopic(topicEntity);
    verify(topicConverter).toDTO(createdTopicEntity);
  }

  @Test
  void updateTopic_Should_CallServiceAndPerformConversions() {
    // given
    var topicDTO = new TopicDTO();
    var topicEntity = new TopicEntity();
    TopicDTO sanitizedDTO = new TopicDTO().id(TOPIC_ID);
    when(topicService.findTopicById(TOPIC_ID)).thenReturn(Optional.of(topicEntity));
    when(topicInputSanitizer.sanitize(topicDTO)).thenReturn(sanitizedDTO);
    when(topicConverter.toEntity(topicEntity, sanitizedDTO)).thenReturn(topicEntity);
    var updatedTopicEntity = new TopicEntity();
    when(topicService.updateTopic(topicEntity)).thenReturn(updatedTopicEntity);

    // when
    topicServiceFacade.updateTopic(TOPIC_ID, topicDTO);

    // then
    verify(topicInputSanitizer).sanitize(topicDTO);
    verify(topicService).findTopicById(TOPIC_ID);
    verify(topicConverter).toEntity(topicEntity, sanitizedDTO);
    verify(topicService).updateTopic(topicEntity);
    verify(topicConverter).toDTO(updatedTopicEntity);
  }
}