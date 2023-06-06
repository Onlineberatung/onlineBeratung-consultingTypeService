package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import de.caritas.cob.consultingtypeservice.api.repository.TopicGroupRepository;
import java.util.Collection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicGroupService {

  private @NonNull TopicGroupRepository topicGroupRepository;

  @Transactional(readOnly = true)
  public Collection<TopicGroupEntity> getAllTopicGroups() {
    return topicGroupRepository.findAll();
  }
}
