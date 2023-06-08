package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import de.caritas.cob.consultingtypeservice.api.repository.TopicGroupRepository;
import java.util.Collection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicGroupService {

  private @NonNull TopicGroupRepository topicGroupRepository;

  public Collection<TopicGroupEntity> getAllTopicGroups() {
    return topicGroupRepository.findAll();
  }
}
