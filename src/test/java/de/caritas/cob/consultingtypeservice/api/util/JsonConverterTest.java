package de.caritas.cob.consultingtypeservice.api.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsMapContaining.hasEntry;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class JsonConverterTest {

  @Test
  void convertToJson_Should_returnCorrectJson() {
    // given
    final Map<String, String> translations = new HashMap<>();
    translations.put("en", "en translation");

    // when
    final String json = JsonConverter.convertToJson(translations);

    // then
    assertThat(json, is("{\"en\":\"en translation\"}"));
  }

  @Test
  void convertMapFromJson_Should_returnCorrectMap() {
    // given
    final String json = "{\"en\":\"en translation\", \"de\":\"de translation\"}";

    // when
    final Map<String, String> translations = JsonConverter.convertMapFromJson(json);

    // then
    assertThat(translations.size(), is(2));
    assertThat(translations, hasEntry("en", "en translation"));
    assertThat(translations, hasEntry("de", "de translation"));
  }

  @Test
  void convertMapFromJson_Should_returnEmptyMap_When_jsonIsNull() {
    // given
    // when
    final Map<String, String> translations = JsonConverter.convertMapFromJson(null);

    // then
    assertThat(translations.size(), is(0));
  }

  @Test
  void convertMapFromJson_Should_returnResult_When_jsonContainsNewLineOrTabulation() {
    // given
    final String json = "{\"de\": \"test\n new line \t tabulation\"}";

    // when
    final Map<String, String> result = JsonConverter.convertMapFromJson(json);

    // then
    assertThat(result, notNullValue());
  }
}
