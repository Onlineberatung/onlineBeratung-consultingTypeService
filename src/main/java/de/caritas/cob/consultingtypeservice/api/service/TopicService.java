package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.repository.TopicRepository;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TopicService {

  private @NonNull TopicRepository topicRepository;

  @Value("${multitenancy.enabled}")
  private boolean multitenancy;

  public Collection<TopicEntity> getAllTopics() {
    if (multitenancy) {
      return topicRepository.findAllForTenant(TenantContext.getCurrentTenant());
    } else {
      return topicRepository.findAll();
    }
  }

  public Collection<TopicEntity> getAllActiveTopics() {
    if (multitenancy) {
      return topicRepository.findAllActiveForTenant(TenantContext.getCurrentTenant());
    } else {
      return topicRepository.findAllActive();
    }
  }

  public Optional<TopicEntity> findTopicById(Long id) {
    if (multitenancy) {
      return topicRepository.findByIdForTenant(id, TenantContext.getCurrentTenant());
    } else {
      return topicRepository.findById(id);
    }
  }

  public TopicEntity createTopic(TopicEntity topicEntity) {
    topicEntity.setCreateDate(LocalDateTime.now(ZoneOffset.UTC));
    return topicRepository.save(topicEntity);
  }

  public TopicEntity updateTopic(TopicEntity topicEntity) {
    topicEntity.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
    return topicRepository.save(topicEntity);
  }
}
