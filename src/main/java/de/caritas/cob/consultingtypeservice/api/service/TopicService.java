package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import java.util.Collection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {

  private @NonNull TopicRepository topicRepository;

  public Collection<TopicEntity> getAllTopics() {
    return topicRepository.findAll();
  }
}
