package de.caritas.cob.consultingtypeservice.api.converter;

import static de.caritas.cob.consultingtypeservice.api.util.JsonConverter.convertMapFromJson;
import static de.caritas.cob.consultingtypeservice.api.util.JsonConverter.convertToJson;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.service.TranslationService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopicConverter {

  private final @NonNull TranslationService translationService;

  public static final String DE = "de";

  public TopicDTO toDTO(final TopicEntity topic, final String lang) {
    final var topicDTO = new TopicDTO()
        .id(topic.getId())
        .name(getTranslatedStringFromMap(topic.getName(), lang))
        .description(getTranslatedStringFromMap(topic.getDescription(), lang))
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

  public TopicDTO toDTO(final TopicEntity topic) {
    final String lang = translationService.getCurrentLanguageContext();
    return toDTO(topic, lang);
  }

  public TopicMultilingualDTO toMultilingualDTO(final TopicEntity topic) {
    final var topicMultilingualDTO = new TopicMultilingualDTO()
        .id(topic.getId())
        .name(convertMapFromJson(topic.getName()))
        .description(convertMapFromJson(topic.getDescription()))
        .status(topic.getStatus().name())
        .internalIdentifier(topic.getInternalIdentifier());
    if (topic.getCreateDate() != null) {
      topicMultilingualDTO.setCreateDate(topic.getCreateDate().toString());
    }
    if (topic.getUpdateDate() != null) {
      topicMultilingualDTO.setUpdateDate(topic.getUpdateDate().toString());
    }
    return topicMultilingualDTO;
  }

  public List<TopicMultilingualDTO> toMultilingualDTOList(
      final Collection<TopicEntity> topicEntities) {
    return topicEntities.stream()
        .map(this::toMultilingualDTO)
        .collect(Collectors.toList());
  }

  public List<TopicDTO> toDTOList(final Collection<TopicEntity> topicEntities) {
    final String lang = translationService.getCurrentLanguageContext();
    return topicEntities.stream()
        .map(topic -> toDTO(topic, lang))
        .collect(Collectors.toList());
  }

  public TopicEntity toEntity(final TopicMultilingualDTO topicDTO) {
    final TopicEntity topicEntity = new TopicEntity();
    topicEntity.setName(convertToJson(topicDTO.getName()));
    topicEntity.setStatus(TopicStatus.valueOf(topicDTO.getStatus().toUpperCase()));
    topicEntity.setDescription(convertToJson(topicDTO.getDescription()));
    topicEntity.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
    topicEntity.setInternalIdentifier(topicDTO.getInternalIdentifier());
    return topicEntity;
  }

  public TopicEntity toEntity(final TopicEntity targetEntity, final TopicMultilingualDTO topicDTO) {
    targetEntity.setName(convertToJson(topicDTO.getName()));
    targetEntity.setStatus(TopicStatus.valueOf(topicDTO.getStatus()));
    targetEntity.setDescription(convertToJson(topicDTO.getDescription()));
    targetEntity.setInternalIdentifier(topicDTO.getInternalIdentifier());
    targetEntity.setUpdateDate(LocalDateTime.now(ZoneOffset.UTC));
    return targetEntity;
  }

  private static String getTranslatedStringFromMap(final String jsonValue, final String lang) {
    final Map<String, String> translations = convertMapFromJson(jsonValue);
    if (lang == null || !translations.containsKey(lang)) {
      if (translations.containsKey(DE)) {
        return translations.get(DE);
      } else {
        log.warn("Default translation for value not available");
        return "";
      }
    } else {
      return translations.get(lang);
    }
  }
}
