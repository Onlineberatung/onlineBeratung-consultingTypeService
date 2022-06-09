package de.caritas.cob.consultingtypeservice.api.converter;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class TopicConverter {

  public TopicDTO toDTO(TopicEntity topic) {
    var topicDTO = new TopicDTO()
        .id(topic.getId())
        .name(topic.getName())
        .description(topic.getDescription())
        .status(topic.getStatus().name())
        .internalIdentifier(topic.getInternalIdentifier());
    if (topic.getCreateDate() != null) {
      topicDTO.setCreateDate(topic.getCreateDate().toString());
    }
    if (topic.getUpdateDate() != null) {
      topicDTO.setUpdateDate(topic.getUpdateDate().toString());
    }
    return topicDTO;
  }

  public TopicEntity toEntity(TopicDTO topicDTO) {
    TopicEntity topicEntity = new TopicEntity();
    topicEntity.setName(topicDTO.getName());
    topicEntity.setStatus(TopicStatus.valueOf(topicDTO.getStatus()));
    topicEntity.setDescription(topicDTO.getDescription());
    topicEntity.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
    topicEntity.setInternalIdentifier(topicDTO.getInternalIdentifier());
    return topicEntity;
  }

  public TopicEntity toEntity(TopicEntity targetEntity, TopicDTO topicDTO) {
    targetEntity.setName(topicDTO.getName());
    targetEntity.setStatus(TopicStatus.valueOf(topicDTO.getStatus()));
    targetEntity.setDescription(topicDTO.getDescription());
    targetEntity.setInternalIdentifier(topicDTO.getInternalIdentifier());
    targetEntity.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
    return targetEntity;
  }
}
