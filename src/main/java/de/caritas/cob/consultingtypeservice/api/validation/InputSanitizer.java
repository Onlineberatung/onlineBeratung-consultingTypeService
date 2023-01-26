package de.caritas.cob.consultingtypeservice.api.validation;

import static java.util.Objects.isNull;

import java.util.Map;
import org.owasp.html.HtmlPolicyBuilder;
import org.springframework.stereotype.Component;

@Component
public class InputSanitizer {

  public String sanitize(final String input) {
    final var sanitizer = new HtmlPolicyBuilder().toFactory();
    return sanitizer.sanitize(input);
  }

  public Map<String, String> sanitize(final Map<String, String> inputByLanguage) {
    if (!isNull(inputByLanguage)) {
      inputByLanguage.entrySet().forEach(entry -> entry.setValue(sanitize(entry.getValue())));
    }
    return inputByLanguage;
  }
}
