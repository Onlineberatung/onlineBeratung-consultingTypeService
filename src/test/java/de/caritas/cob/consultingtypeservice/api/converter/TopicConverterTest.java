package de.caritas.cob.consultingtypeservice.api.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.service.TranslationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicConverterTest {

  @Mock private TranslationService translationService;
  @InjectMocks TopicConverter topicConverter;

  @Test
  void toEntity_should_convertToEntityAndBackToMultilingualDTO() {
    // given
    final Map<String, String> name = new HashMap<>();
    name.put("de", "name");
    final Map<String, String> description = new HashMap<>();
    description.put("de", "desc");
    final var topicDTO =
        new TopicMultilingualDTO()
            .id(1L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier")
            .name(name)
            .description(description);

    // when
    final var entity = topicConverter.toEntity(topicDTO);

    // then
    final TopicDTO actual = topicConverter.toDTO(entity);
    assertThat(actual.getName()).isEqualTo(topicDTO.getName().get("de"));
    assertThat(actual.getDescription()).isEqualTo(topicDTO.getDescription().get("de"));
  }

  @Test
  void toDTOList_Should_convertCollectionOfTopicEntitiesToListOfTopicDTOsWithCorrectLang() {
    // given
    when(translationService.getCurrentLanguageContext()).thenReturn("en");
    final var topicEntity1 =
        TopicEntity.builder()
            .id(1L)
            .status(TopicStatus.ACTIVE)
            .internalIdentifier("identifier")
            .name("{\"de\":\"name de\", \"en\":\"name en\"}")
            .description("{\"de\":\"desc de\", \"en\":\"desc en\"}")
            .build();
    final var topicEntity2 =
        TopicEntity.builder()
            .id(2L)
            .status(TopicStatus.ACTIVE)
            .internalIdentifier("identifier 2")
            .name("{\"de\":\"name 2 de\", \"en\":\"name 2 en\"}")
            .description("{\"de\":\"desc 2 de\", \"en\":\"desc 2 en\"}")
            .build();

    final var topicDTO1 =
        new TopicDTO()
            .id(1L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier")
            .name("name en")
            .description("desc en");
    final var topicDTO2 =
        new TopicDTO()
            .id(2L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier 2")
            .name("name 2 en")
            .description("desc 2 en");

    // when
    final var entities = topicConverter.toDTOList(List.of(topicEntity1, topicEntity2));

    // then
    assertThat(entities).containsExactly(topicDTO1, topicDTO2);
  }

  @Test
  void
      toDTOList_Should_convertCollectionOfTopicEntitiesToListOfTopicDTOsWithDE_When_languageIsNotProvided() {
    // given
    when(translationService.getCurrentLanguageContext()).thenReturn(null);
    final var topicEntity1 =
        TopicEntity.builder()
            .id(1L)
            .status(TopicStatus.ACTIVE)
            .internalIdentifier("identifier")
            .name("{\"de\":\"name de\", \"en\":\"name en\"}")
            .description("{\"de\":\"desc de\", \"en\":\"desc en\"}")
            .build();
    final var topicEntity2 =
        TopicEntity.builder()
            .id(2L)
            .status(TopicStatus.ACTIVE)
            .internalIdentifier("identifier 2")
            .name("{\"de\":\"name 2 de\", \"en\":\"name 2 en\"}")
            .description("{\"de\":\"desc 2 de\", \"en\":\"desc 2 en\"}")
            .build();

    final var topicDTO1 =
        new TopicDTO()
            .id(1L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier")
            .name("name de")
            .description("desc de");
    final var topicDTO2 =
        new TopicDTO()
            .id(2L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier 2")
            .name("name 2 de")
            .description("desc 2 de");

    // when
    final var entities = topicConverter.toDTOList(List.of(topicEntity1, topicEntity2));

    // then
    assertThat(entities).containsExactly(topicDTO1, topicDTO2);
  }

  @Test
  void
      toMultilingualDTOList_Should_convertCollectionOfTopicEntitiesToListOfTopicMultilingualDTOs() {
    // given
    final var topicEntity1 =
        TopicEntity.builder()
            .id(1L)
            .status(TopicStatus.ACTIVE)
            .internalIdentifier("identifier")
            .name("{\"de\":\"name de\", \"en\":\"name en\"}")
            .description("{\"de\":\"desc de\", \"en\":\"desc en\"}")
            .build();
    final var topicEntity2 =
        TopicEntity.builder()
            .id(2L)
            .status(TopicStatus.ACTIVE)
            .internalIdentifier("identifier 2")
            .name("{\"de\":\"name 2 de\", \"en\":\"name 2 en\"}")
            .description("{\"de\":\"desc 2 de\", \"en\":\"desc 2 en\"}")
            .build();

    final Map<String, String> name1 = new HashMap<>();
    name1.put("de", "name de");
    name1.put("en", "name en");
    final Map<String, String> description1 = new HashMap<>();
    description1.put("de", "desc de");
    description1.put("en", "desc en");
    final Map<String, String> name2 = new HashMap<>();
    name2.put("de", "name 2 de");
    name2.put("en", "name 2 en");
    final Map<String, String> description2 = new HashMap<>();
    description2.put("de", "desc 2 de");
    description2.put("en", "desc 2 en");
    final var topicDTO1 =
        new TopicMultilingualDTO()
            .id(1L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier")
            .name(name1)
            .description(description1);
    final var topicDTO2 =
        new TopicMultilingualDTO()
            .id(2L)
            .status(TopicStatus.ACTIVE.toString())
            .internalIdentifier("identifier 2")
            .name(name2)
            .description(description2);

    // when
    final var entities = topicConverter.toMultilingualDTOList(List.of(topicEntity1, topicEntity2));

    // then
    assertThat(entities).containsExactly(topicDTO1, topicDTO2);
  }
}
