package de.caritas.cob.consultingtypeservice.api.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.BadRequestException;
import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicValidationServiceTest {

  @InjectMocks
  TopicValidationService topicValidationService;

  @Test
  void validate_Should_ThrowBadRequestException_IfStatusIsNotValid() {
    assertThrows(BadRequestException.class, this::callValidationMethod);
  }

  @Test
  void validate_Should_ThrowBadRequestException_IfStatusIsNull() {
    assertThrows(BadRequestException.class, this::callValidationMethod);
  }

  private void callValidationMethod() {
    topicValidationService.validate(new TopicDTO());
  }

  @Test
  void validate_Should_NotThrowBadRequestException_IfStatusIsValid() {
    topicValidationService.validate(new TopicDTO().status("ACTIVE"));
  }

  @Test
  void validate_Should_NotThrowBadRequestException_IfStatusIsValidLowercase() {
    topicValidationService.validate(new TopicDTO().status("active"));
  }

}