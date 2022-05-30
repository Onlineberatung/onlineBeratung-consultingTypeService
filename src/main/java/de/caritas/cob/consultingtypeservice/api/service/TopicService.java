package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

  public TopicEntity saveTopic(TopicDTO topicDTO) {
    TopicEntity topicEntity = new TopicEntity();
    topicEntity.setName(topicDTO.getName());
    topicEntity.setStatus(TopicStatus.ACTIVE);
    topicEntity.setDescription(topicDTO.getDescription());
    topicEntity.setCreateDate(LocalDateTime.now(ZoneOffset.UTC));
    topicEntity.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
    return topicRepository.save(topicEntity);
  }
}
