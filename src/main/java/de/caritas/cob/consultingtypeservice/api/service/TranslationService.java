package de.caritas.cob.consultingtypeservice.api.service;

import static java.util.Objects.nonNull;

import java.util.Arrays;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class TranslationService {

  private static final String DEFAULT_LANGUAGE = "de";
  private static final String LANGUAGE_COOKIE_NAME = "lang";

  public String getCurrentLanguageContext() {
    final HttpServletRequest currentRequest =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    if (nonNull(currentRequest.getCookies())) {
      final Optional<Cookie> languageCookie = findLanguageCookie(currentRequest);
      if (languageCookie.isPresent()) {
        return languageCookie.get().getValue();
      }
    }
    return DEFAULT_LANGUAGE;
  }

  private Optional<Cookie> findLanguageCookie(final HttpServletRequest currentRequest) {
    return Arrays.stream(currentRequest.getCookies())
        .filter(cookie -> LANGUAGE_COOKIE_NAME.equals(cookie.getName())).findFirst();
  }
}
