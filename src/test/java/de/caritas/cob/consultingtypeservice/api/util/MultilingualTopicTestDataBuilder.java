package de.caritas.cob.consultingtypeservice.api.util;

import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class MultilingualTopicTestDataBuilder {

  private static final String NAME = "name";
  private static final long ID = 1L;
  private static final String DESCRIPTION = "description";
  private static final String INTERNAL_IDENTIFIER = "internalIdentifier";
  private static final String STATUS = "ACTIVE";
  private static final String DE = "de";

  TopicMultilingualDTO topicMultilingualDTO = new TopicMultilingualDTO();

  public MultilingualTopicTestDataBuilder topicDTO() {
    topicMultilingualDTO = new TopicMultilingualDTO();
    topicMultilingualDTO.setId(ID);
    topicMultilingualDTO.setStatus(STATUS);
    topicMultilingualDTO.setInternalIdentifier(INTERNAL_IDENTIFIER);
    topicMultilingualDTO.setCreateDate(LocalDateTime.now(ZoneOffset.UTC).toString());
    return this;
  }

  public MultilingualTopicTestDataBuilder withId(final Long id) {
    topicMultilingualDTO.setId(id);
    return this;
  }

  public MultilingualTopicTestDataBuilder withStatus(final String status) {
    topicMultilingualDTO.setStatus(status);
    return this;
  }

  public MultilingualTopicTestDataBuilder withInternalIdentifier(final String internalIdentifier) {
    topicMultilingualDTO.setInternalIdentifier(internalIdentifier);
    return this;
  }

  public MultilingualTopicTestDataBuilder withName() {
    topicMultilingualDTO.setName(translations(NAME, DE));
    return this;
  }

  public MultilingualTopicTestDataBuilder withName(final String name) {
    topicMultilingualDTO.setName(translations(name, DE));
    return this;
  }

  public MultilingualTopicTestDataBuilder withName(final String name, final String lang) {
    topicMultilingualDTO.setName(translations(name, lang));
    return this;
  }

  public MultilingualTopicTestDataBuilder withDescription() {
    topicMultilingualDTO.setDescription(translations(DESCRIPTION, DE));
    return this;
  }

  public MultilingualTopicTestDataBuilder withDescription(final String desc) {
    topicMultilingualDTO.setDescription(translations(desc, DE));
    return this;
  }

  public MultilingualTopicTestDataBuilder withDescription(final String desc, final String lang) {
    topicMultilingualDTO.setDescription(translations(desc, lang));
    return this;
  }

  public TopicMultilingualDTO build() {
    return topicMultilingualDTO;
  }

  private Map<String, String> translations(final String content, final String lang) {
    final var map = new HashMap<String, String>();
    map.put(lang, content);
    return map;
  }

  public String jsonify() {
    final TopicMultilingualDTO build = build();
    return JsonConverter.convertToJson(build);
  }
}
