package de.caritas.cob.consultingtypeservice.api.validation;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.BadRequestException;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class TopicValidationService {

  public void validate(TopicDTO input) {
    try {
      TopicStatus.valueOf(input.getStatus().toUpperCase());
    } catch (Exception ex) {
      throw new BadRequestException("Invalid topic status: " + input.getStatus());
    }
  }

  public void validate(TopicMultilingualDTO input) {
    try {
      TopicStatus.valueOf(input.getStatus().toUpperCase());
    } catch (Exception ex) {
      throw new BadRequestException("Invalid topic status: " + input.getStatus());
    }
  }
}
