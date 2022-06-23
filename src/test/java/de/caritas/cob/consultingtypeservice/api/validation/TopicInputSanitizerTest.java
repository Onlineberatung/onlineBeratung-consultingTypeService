package de.caritas.cob.consultingtypeservice.api.validation;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.TopicDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import org.junit.jupiter.api.Test;

class TopicInputSanitizerTest {

  public static final String SCRIPT_MALICIOUS_CONTENT = "<script>malicious content</script>";

  TopicInputSanitizer topicInputSanitizer = new TopicInputSanitizer(new InputSanitizer());

  @Test
  void sanitize_Should_sanitizeTopicDTO() {
    // given
    TopicDTO topicDTO = new TopicDTO().name("name" + SCRIPT_MALICIOUS_CONTENT)
        .description("desc" + SCRIPT_MALICIOUS_CONTENT)
        .internalIdentifier(SCRIPT_MALICIOUS_CONTENT + "internalIdent")
        .status(TopicStatus.ACTIVE.toString());

    // when
    TopicDTO sanitized = topicInputSanitizer.sanitize(
        topicDTO);

    // then
    assertThat(sanitized.getName()).isEqualTo("name");
    assertThat(sanitized.getDescription()).isEqualTo("desc");
    assertThat(sanitized.getInternalIdentifier()).isEqualTo("internalIdent");
    assertThat(sanitized.getStatus()).isEqualTo(TopicStatus.ACTIVE.toString());
  }

}