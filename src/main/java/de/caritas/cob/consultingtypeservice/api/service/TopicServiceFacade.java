package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.exception.TopicNotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.validation.TopicInputSanitizer;
import de.caritas.cob.consultingtypeservice.api.validation.TopicValidationService;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceFacade {

  private final @NonNull TopicService topicService;
  private final @NonNull TopicConverter topicConverter;
  private final @NonNull TopicInputSanitizer topicInputSanitizer;
  private final @NonNull TopicValidationService topicValidationService;

  public List<TopicDTO> getAllTopics() {
    final var topicEntities = topicService.getAllTopics();
    return topicConverter.toDTOList(topicEntities);
  }

  public List<TopicDTO> getAllActiveTopics() {
    final var topicEntities = topicService.getAllActiveTopics();
    return topicConverter.toDTOList(topicEntities);
  }

  public TopicMultilingualDTO createTopic(final TopicMultilingualDTO topicDTO) {
    topicValidationService.validate(topicDTO);
    final TopicMultilingualDTO sanitizedTopicDTO = topicInputSanitizer.sanitize(topicDTO);
    final var topicEntity = topicConverter.toEntity(sanitizedTopicDTO);
    final var savedTopic = topicService.createTopic(topicEntity);
    return topicConverter.toMultilingualDTO(savedTopic);
  }

  public TopicMultilingualDTO updateTopic(final Long id, final TopicMultilingualDTO topicDTO) {
    topicValidationService.validate(topicDTO);
    final var topicById = topicService.findTopicById(id);
    if (topicById.isPresent()) {
      log.info("Found topic with id {}", topicById);
      final var sanitizedTopicDTO = topicInputSanitizer.sanitize(topicDTO);
      final var topicEntity = topicConverter.toEntity(topicById.get(), sanitizedTopicDTO);
      final var updatedEntity = topicService.updateTopic(topicEntity);
      return topicConverter.toMultilingualDTO(updatedEntity);
    } else {
      throw new TopicNotFoundException("Topic with given id could not be found : " + id);
    }
  }

  public List<TopicMultilingualDTO> getAllTopicsMultilingual() {
    final var topicEntities = topicService.getAllTopics();
    return topicConverter.toMultilingualDTOList(topicEntities);
  }

  public TopicMultilingualDTO getTopicMultilingualById(final Long id) {
    final var topicEntity = topicService.findTopicById(id);
    if (topicEntity.isPresent()) {
      return topicConverter.toMultilingualDTO(topicEntity.get());
    } else {
      throw new TopicNotFoundException("Topic with given id could not be found : " + id);
    }
  }
}
