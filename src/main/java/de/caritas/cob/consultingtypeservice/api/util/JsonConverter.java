package de.caritas.cob.consultingtypeservice.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JsonConverter {

  public static String convertToJson(final Object object) {
    return serializeToJsonString(object);
  }

  public static Map<String, String> convertMapFromJson(final String jsonString) {
    if (jsonString == null) {
      return Maps.newHashMap();
    }
    final var result =
        deserializeMapFromJsonString(jsonString, new TypeReference<Map<String, String>>() {});
    if (result == null) {
      log.warn("Could not deserialize map from json.");
      return Maps.newHashMap();
    }
    return result;
  }

  private static <T, Y> Map<T, Y> deserializeMapFromJsonString(
      final String jsonString, final TypeReference<Map<T, Y>> typeReference) {
    try {
      final var objectMapper =
          new ObjectMapper()
              .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
              .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
      return objectMapper.readValue(jsonString, typeReference);
    } catch (final JsonProcessingException e) {
      throw new RuntimeJsonMappingException(e.getMessage());
    }
  }

  private static <T> String serializeToJsonString(final T object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (final JsonProcessingException e) {
      throw new RuntimeJsonMappingException(e.getMessage());
    }
  }
}
