package de.caritas.cob.consultingtypeservice.api.validation;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class TopicInputSanitizer {

  private final @NonNull InputSanitizer inputSanitizer;

  public TopicDTO sanitize(TopicDTO input) {
    log.info("Sanitizing input DTO");
    TopicDTO output = copyNotSanitizedAttributes(input);
    output.setName(inputSanitizer.sanitize(input.getName()));
    output.setStatus(inputSanitizer.sanitize(input.getStatus()));
    output.setDescription(inputSanitizer.sanitize(input.getDescription()));
    output.setInternalIdentifier(inputSanitizer.sanitize(input.getInternalIdentifier()));
    return output;
  }

  private TopicDTO copyNotSanitizedAttributes(TopicDTO input) {
    TopicDTO output = new TopicDTO();
    output.setId(input.getId());
    output.setCreateDate(input.getCreateDate());
    output.setUpdateDate(input.getUpdateDate());
    return output;
  }
}
