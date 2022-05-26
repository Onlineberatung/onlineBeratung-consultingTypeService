package de.caritas.cob.consultingtypeservice.api.converter;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import org.springframework.stereotype.Component;

@Component
public class TopicConverter {

  public TopicDTO toDTO(TopicEntity topic) {
    var topicDTO = new TopicDTO()
        .id(topic.getId())
        .name(topic.getName())
        .description(topic.getDescription())
        .status(topic.getStatus());
    if (topic.getCreateDate() != null) {
      topicDTO.setCreateDate(topic.getCreateDate().toString());
    }
    if (topic.getUpdateDate() != null) {
      topicDTO.setUpdateDate(topic.getUpdateDate().toString());
    }
    return topicDTO;
  }
}
