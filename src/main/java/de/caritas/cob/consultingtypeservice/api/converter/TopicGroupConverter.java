package de.caritas.cob.consultingtypeservice.api.converter;

import de.caritas.cob.consultingtypeservice.api.model.TopicGroupEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTOData;
import de.caritas.cob.consultingtypeservice.api.model.TopicGroupsDTODataItemsInner;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TopicGroupConverter {

  public TopicGroupsDTO toTopicGroupsDTO(Collection<TopicGroupEntity> topicGroups) {
    return new TopicGroupsDTO().data(new TopicGroupsDTOData().items(itemsOf(topicGroups)));
  }

  private static List<TopicGroupsDTODataItemsInner> itemsOf(
      Collection<TopicGroupEntity> topicGroups) {
    return topicGroups.stream()
        .map(
            topicGroup ->
                new TopicGroupsDTODataItemsInner()
                    .topicIds(topicIdsOf(topicGroup))
                    .id(topicGroup.getId().intValue())
                    .name(topicGroup.getName()))
        .collect(Collectors.toList());
  }

  private static List<Integer> topicIdsOf(TopicGroupEntity topicGroup) {
    return topicGroup.getTopicEntities().stream()
        .map(topicEntity -> topicEntity.getId().intValue())
        .collect(Collectors.toList());
  }
}
