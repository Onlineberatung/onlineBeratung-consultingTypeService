package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.converter.TopicConverter;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicServiceFacade {

  private final @NonNull TopicService topicService;
  private final @NonNull TopicConverter topicConverter;

  public Collection<TopicDTO> getAllTopics() {
    var topicEntities = topicService.getAllTopics();
    return topicEntities.stream().map(topicConverter::toDTO).collect(
        Collectors.toList());
  }

  public TopicDTO saveTopic(TopicDTO topicDTO) {
    TopicEntity savedTopic = topicService.saveTopic(topicDTO);
    return topicConverter.toDTO(savedTopic);
  }
}
