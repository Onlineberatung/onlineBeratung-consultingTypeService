package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import java.util.Collection;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TopicService {

  private @NonNull TopicRepository topicRepository;

  public Collection<TopicEntity> getAllTopics() {
    return topicRepository.findAll();
  }
}
