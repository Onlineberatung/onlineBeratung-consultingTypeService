package de.caritas.cob.consultingtypeservice.api.converter;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopicConverterTest {

  TopicConverter topicConverter = new TopicConverter();

  @Test
  void toEntity_should_convertToEntityAndBackToDTO() {
    // given
    var topicDTO = new TopicDTO().id(1L).status(TopicStatus.ACTIVE.toString())
        .internalIdentifier("identifier").name("name").description("desc");

    // when
    var entity = topicConverter.toEntity(topicDTO);

    // then
    assertThat(topicConverter.toDTO(entity)).isEqualToIgnoringGivenFields(topicDTO, "id",
        "createDate", "updateDate");
  }

  @Test
  void toDTOList_Should_convertCollectionOfTopicEntitiesToListOfTopicDTOs() {
    // given
    var topicEntity1 = TopicEntity.builder().id(1L).status(TopicStatus.ACTIVE)
        .internalIdentifier("identifier")
        .name("name").description("description").build();
    var topicEntity2 = TopicEntity.builder().id(2L).status(TopicStatus.ACTIVE)
        .internalIdentifier("identifier 2")
        .name("name 2").description("description 2").build();

    var topicDTO1 = new TopicDTO().id(1L).status(TopicStatus.ACTIVE.toString())
        .internalIdentifier("identifier").name("name").description("description");
    var topicDTO2 = new TopicDTO().id(2L).status(TopicStatus.ACTIVE.toString())
        .internalIdentifier("identifier 2").name("name 2").description("description 2");

    // when
    var entities = topicConverter.toDTOList(List.of(topicEntity1, topicEntity2));

    // then
    assertThat(entities).containsExactly(topicDTO1, topicDTO2);
  }
}