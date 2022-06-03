package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceFacade {

  private final @NonNull TopicService topicService;
  private final @NonNull TopicConverter topicConverter;

  public List<TopicDTO> getAllTopics() {
    var topicEntities = topicService.getAllTopics();
    return topicEntities.stream().map(topicConverter::toDTO).collect(
        Collectors.toList());
  }

  public TopicDTO createTopic(TopicDTO topicDTO) {
    var topicEntity = topicConverter.toEntity(topicDTO);
    var savedTopic = topicService.createTopic(topicEntity);
    return topicConverter.toDTO(savedTopic);
  }
}
