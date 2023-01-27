package de.caritas.cob.consultingtypeservice.api.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import de.caritas.cob.consultingtypeservice.api.util.MultilingualTopicTestDataBuilder;
import org.junit.jupiter.api.Test;

class TopicInputSanitizerTest {

  public static final String SCRIPT_MALICIOUS_CONTENT = "<script>malicious content</script>";

  TopicInputSanitizer topicInputSanitizer = new TopicInputSanitizer(new InputSanitizer());

  @Test
  void sanitize_Should_sanitizeTopicDTO() {
    // given
    final TopicMultilingualDTO topicMultilingualDTO =
        new MultilingualTopicTestDataBuilder()
            .topicDTO()
            .withName("name" + SCRIPT_MALICIOUS_CONTENT)
            .withDescription("desc" + SCRIPT_MALICIOUS_CONTENT)
            .withInternalIdentifier(SCRIPT_MALICIOUS_CONTENT + "internalIdent")
            .withStatus(TopicStatus.ACTIVE.toString())
            .build();
    // when
    final TopicMultilingualDTO sanitized = topicInputSanitizer.sanitize(topicMultilingualDTO);

    // then
    assertThat(sanitized.getName().get("de"), is("name"));
    assertThat(sanitized.getDescription().get("de"), is("desc"));
    assertThat(sanitized.getInternalIdentifier(), is("internalIdent"));
    assertThat(sanitized.getStatus(), is(TopicStatus.ACTIVE.toString()));
  }
}
