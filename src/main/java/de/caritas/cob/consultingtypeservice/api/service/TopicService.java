package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicService {

  private @NonNull TopicRepository topicRepository;

  public Collection<TopicEntity> getAllTopics() {

    if (TenantContext.getCurrentTenant() == null) {
      return topicRepository.findAll();
    } else {
      return topicRepository.findAllForTenant(TenantContext.getCurrentTenant());
    }
  }

  public TopicEntity createTopic(TopicEntity topicEntity) {
    topicEntity.setStatus(TopicStatus.ACTIVE);
    topicEntity.setCreateDate(LocalDateTime.now(ZoneOffset.UTC));
    return topicRepository.save(topicEntity);
  }
}
