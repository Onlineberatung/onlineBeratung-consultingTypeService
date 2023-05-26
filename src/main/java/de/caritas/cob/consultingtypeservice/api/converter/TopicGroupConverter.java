package de.caritas.cob.consultingtypeservice.api.converter;

import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTOData;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTODataItemsInner;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class TopicGroupConverter {

  public TopicGroupsDTO toTopicGroupsDTO(Collection<TopicGroupEntity> topicGroups) {
    val tg = new TopicGroupsDTO();
    val items =
        topicGroups.stream()
            .map(
                topicGroup ->
                    new TopicGroupsDTODataItemsInner()
                        .topicIds(
                            topicGroup.getTopicEntities().stream()
                                .map(topicEntity -> topicEntity.getId().intValue())
                                .collect(Collectors.toList()))
                        .id(topicGroup.getId().intValue())
                        .name(topicGroup.getName()))
            .collect(Collectors.toList());
    return tg.data(new TopicGroupsDTOData().items(items));
  }
}
