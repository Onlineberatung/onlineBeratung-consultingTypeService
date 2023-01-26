package de.caritas.cob.consultingtypeservice.api.service;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantHeaderSupplier {

  public Optional<Long> getTenantFromHeader() {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    try {
      return Optional.of(Long.parseLong(request.getHeader("tenantId")));
    } catch (NumberFormatException exception) {
      log.debug("No tenantId provided via headers.");
      return Optional.empty();
    }
  }
}
