package de.caritas.cob.consultingtypeservice.api.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import de.caritas.cob.consultingtypeservice.api.exception.TopicValidationException;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.BadRequestException;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.util.MultilingualTopicTestDataBuilder;
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
    topicValidationService.validate(new TopicMultilingualDTO());
  }

  @Test
  void validate_Should_NotThrowBadRequestException_IfStatusIsValid() {
    topicValidationService.validate(new TopicMultilingualDTO().status("ACTIVE"));
  }

  @Test
  void validate_Should_NotThrowBadRequestException_IfStatusIsValidLowercase() {
    topicValidationService.validate(new TopicMultilingualDTO().status("active"));
  }

  @Test
  void validate_Should_ThrowTopicValidationException_IfNameTranslationKeyIsInvalid() {
    assertThrows(TopicValidationException.class,
        () -> topicValidationService.validate(
            new MultilingualTopicTestDataBuilder().topicDTO()
                .withName("name", "invalidLanguage")
                .withDescription("desc", "en").build()));
  }

  @Test
  void validate_Should_ThrowTopicValidationException_IfDescriptionTranslationKeyIsInvalid() {
    assertThrows(TopicValidationException.class,
        () -> topicValidationService.validate(
            new MultilingualTopicTestDataBuilder().topicDTO()
                .withName("name", "en")
                .withDescription("desc", "invalidLanguage").build()));
  }

  @Test
  void validate_Should_NotThrowTopicValidationException_IfNameAndDescTranslationKeysAreValid() {
    topicValidationService.validate(
        new MultilingualTopicTestDataBuilder().topicDTO()
            .withName("name", "en")
            .withDescription("desc", "de")
            .build());
  }
}