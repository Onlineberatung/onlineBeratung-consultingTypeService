package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.validation.TopicInputSanitizer;
import de.caritas.cob.consultingtypeservice.api.validation.TopicValidationService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceFacadeTest {

  private static final long TOPIC_ID = 1L;

  @Mock TopicService topicService;

  @Mock TopicConverter topicConverter;

  @Mock TopicInputSanitizer topicInputSanitizer;

  @Mock TopicValidationService topicValidationService;

  @InjectMocks TopicServiceFacade topicServiceFacade;

  @Test
  void getAllTopics_Should_CallServiceAndConvertResults() {
    // given
    final var topicEntity1 = TopicEntity.builder().id(1L).name("a name").build();
    final var topicEntity2 = TopicEntity.builder().id(2L).name("a name 2").build();

    when(topicService.getAllTopics()).thenReturn(newArrayList(topicEntity1, topicEntity2));
    // when
    topicServiceFacade.getAllTopics();

    // then
    verify(topicService).getAllTopics();
    verify(topicConverter).toDTOList(List.of(topicEntity1, topicEntity2));
    verifyNoMoreInteractions(topicConverter);
  }

  @Test
  void getAllActiveTopics_Should_CallServiceAndConvertResults() {
    // given
    final var topicEntity1 = TopicEntity.builder().id(1L).name("a name").build();
    final var topicEntity2 = TopicEntity.builder().id(2L).name("a name 2").build();

    when(topicService.getAllActiveTopics()).thenReturn(newArrayList(topicEntity1, topicEntity2));
    // when
    topicServiceFacade.getAllActiveTopics();

    // then
    verify(topicService).getAllActiveTopics();
    verify(topicConverter).toDTOList(List.of(topicEntity1, topicEntity2));
    verifyNoMoreInteractions(topicConverter);
  }

  @Test
  void createTopic_Should_CallServiceAndPerformConversions() {
    // given
    final var topicDTO = new TopicMultilingualDTO();
    final var topicEntity = new TopicEntity();
    final TopicMultilingualDTO sanitizedDTO = new TopicMultilingualDTO();
    when(topicInputSanitizer.sanitize(topicDTO)).thenReturn(sanitizedDTO);
    when(topicConverter.toEntity(topicDTO)).thenReturn(topicEntity);
    final var createdTopicEntity = new TopicEntity();

    when(topicService.createTopic(topicEntity)).thenReturn(createdTopicEntity);

    // when
    topicServiceFacade.createTopic(topicDTO);

    // then
    verify(topicValidationService).validate(topicDTO);
    verify(topicInputSanitizer).sanitize(topicDTO);
    verify(topicConverter).toEntity(topicDTO);
    verify(topicService).createTopic(topicEntity);
    verify(topicConverter).toMultilingualDTO(createdTopicEntity);
  }

  @Test
  void updateTopic_Should_CallServiceAndPerformConversions() {
    // given
    final var topicDTO = new TopicMultilingualDTO();
    final var topicEntity = new TopicEntity();
    final TopicMultilingualDTO sanitizedDTO = new TopicMultilingualDTO().id(TOPIC_ID);
    when(topicService.findTopicById(TOPIC_ID)).thenReturn(Optional.of(topicEntity));
    when(topicInputSanitizer.sanitize(topicDTO)).thenReturn(sanitizedDTO);
    when(topicConverter.toEntity(topicEntity, sanitizedDTO)).thenReturn(topicEntity);
    final var updatedTopicEntity = new TopicEntity();
    when(topicService.updateTopic(topicEntity)).thenReturn(updatedTopicEntity);

    // when
    topicServiceFacade.updateTopic(TOPIC_ID, topicDTO);

    // then
    verify(topicValidationService).validate(topicDTO);
    verify(topicInputSanitizer).sanitize(topicDTO);
    verify(topicService).findTopicById(TOPIC_ID);
    verify(topicConverter).toEntity(topicEntity, sanitizedDTO);
    verify(topicService).updateTopic(topicEntity);
    verify(topicConverter).toMultilingualDTO(updatedTopicEntity);
  }
}
