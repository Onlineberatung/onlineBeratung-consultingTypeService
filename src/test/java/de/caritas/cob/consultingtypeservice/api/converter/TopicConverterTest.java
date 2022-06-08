package de.caritas.cob.consultingtypeservice.api.converter;

import static org.assertj.core.api.Assertions.assertThat;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
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
}