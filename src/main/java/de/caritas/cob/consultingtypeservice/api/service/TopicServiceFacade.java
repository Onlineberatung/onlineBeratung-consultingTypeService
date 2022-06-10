package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.exception.TopicNotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.validation.TopicInputSanitizer;
import de.caritas.cob.consultingtypeservice.api.validation.TopicValidationService;
import java.util.List;
import java.util.stream.Collectors;
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
    var topicEntities = topicService.getAllTopics();
    return topicEntities.stream().map(topicConverter::toDTO).collect(
        Collectors.toList());
  }

  public TopicDTO createTopic(TopicDTO topicDTO) {
    topicValidationService.validate(topicDTO);
    TopicDTO sanitizedTopicDTO = topicInputSanitizer.sanitize(topicDTO);
    var topicEntity = topicConverter.toEntity(sanitizedTopicDTO);
    var savedTopic = topicService.createTopic(topicEntity);
    return topicConverter.toDTO(savedTopic);
  }

  public TopicDTO updateTopic(Long id, TopicDTO topicDTO) {
    topicValidationService.validate(topicDTO);
    var topicById = topicService.findTopicById(id);
    if (topicById.isPresent()) {
      log.info("Found topic with id {}", topicById);
      var sanitizedTopicDTO = topicInputSanitizer.sanitize(topicDTO);
      var topicEntity = topicConverter.toEntity(topicById.get(), sanitizedTopicDTO);
      var updatedEntity = topicService.updateTopic(topicEntity);
      return topicConverter.toDTO(updatedEntity);
    } else {
      throw new TopicNotFoundException("Topic with given id could not be found : " + id);
    }
  }
}
