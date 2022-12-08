package de.caritas.cob.consultingtypeservice.api.validation;

import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class TopicInputSanitizer {

  private final @NonNull InputSanitizer inputSanitizer;

  public TopicMultilingualDTO sanitize(final TopicMultilingualDTO input) {
    log.info("Sanitizing input DTO");
    final TopicMultilingualDTO output = copyNotSanitizedAttributes(input);
    output.setName(inputSanitizer.sanitize(input.getName()));
    output.setStatus(inputSanitizer.sanitize(input.getStatus()));
    output.setDescription(inputSanitizer.sanitize(input.getDescription()));
    output.setInternalIdentifier(inputSanitizer.sanitize(input.getInternalIdentifier()));
    return output;
  }

  private TopicMultilingualDTO copyNotSanitizedAttributes(final TopicMultilingualDTO input) {
    final TopicMultilingualDTO output = new TopicMultilingualDTO();
    output.setId(input.getId());
    output.setCreateDate(input.getCreateDate());
    output.setUpdateDate(input.getUpdateDate());
    return output;
  }
}
