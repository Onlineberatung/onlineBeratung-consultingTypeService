package de.caritas.cob.consultingtypeservice.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;

public class JsonConverter {

  public static String convert(
      TopicDTO topicDTO) {
    try {
      return new ObjectMapper().writeValueAsString(topicDTO);
    } catch (JsonProcessingException e) {
      throw new RuntimeJsonMappingException(e.getMessage());
    }
  }
}
