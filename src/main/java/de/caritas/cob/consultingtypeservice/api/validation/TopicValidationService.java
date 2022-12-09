package de.caritas.cob.consultingtypeservice.api.validation;

import com.google.common.collect.Lists;
import de.caritas.cob.consultingtypeservice.api.exception.TopicValidationException;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.BadRequestException;
import de.caritas.cob.consultingtypeservice.api.model.TopicMultilingualDTO;
import de.caritas.cob.consultingtypeservice.api.model.TopicStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class TopicValidationService {

  public void validate(final TopicMultilingualDTO input) {
    validateStatus(input);
    validateTranslations(input);
  }

  private static void validateStatus(final TopicMultilingualDTO input) {
    try {
      TopicStatus.valueOf(input.getStatus().toUpperCase());
    } catch (final Exception ex) {
      throw new BadRequestException("Invalid topic status: " + input.getStatus());
    }
  }

  private void validateTranslations(final TopicMultilingualDTO input) {
    final var isoLanguages = Arrays.stream(Locale.getISOLanguages()).collect(Collectors.toSet());
    validateTranslationKeys(isoLanguages, getLanguageLowercaseKeys(input.getName()));
    validateTranslationKeys(isoLanguages, getLanguageLowercaseKeys(input.getDescription()));
  }

  private static List<String> getLanguageLowercaseKeys(final Map<String, String> translatedMap) {
    if (translatedMap == null) {
      return Lists.newArrayList();
    }
    return translatedMap.keySet().stream().map(String::toLowerCase).collect(Collectors.toList());
  }

  private void validateTranslationKeys(final Set<String> isoLanguages, final List<String> keys) {
    if (!keys.isEmpty() && !isoLanguages.containsAll(keys)) {
      throw new TopicValidationException("Language key is not valid");
    }
  }
}
