package de.caritas.cob.consultingtypeservice.api.service.tenant;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import de.caritas.cob.consultingtypeservice.api.service.TenantHeaderSupplier;
import de.caritas.cob.consultingtypeservice.api.service.TenantService;
import de.caritas.cob.consultingtypeservice.filter.SubdomainExtractor;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@ConditionalOnExpression("${multitenancy.enabled:true}")
@Component
public class TenantResolver {

  private final @NonNull TenantHeaderSupplier tenantHeaderSupplier;
  private @NonNull SubdomainExtractor subdomainExtractor;
  private @NonNull TenantService tenantService;

  public Long resolve(HttpServletRequest request) {
    return resolveForNonAuthenticatedUser();
  }

  private Long resolveForNonAuthenticatedUser() {
    Optional<Long> tenantId = resolveTenantFromHttpRequest();
    if (tenantId.isEmpty()) {
      throw new AccessDeniedException("Tenant id could not be resolved");
    }
    return tenantId.get();
  }

  private Optional<Long> resolveTenantFromHttpRequest() {

    Optional<Long> tenantFromHeader = tenantHeaderSupplier.getTenantFromHeader();
    if (tenantFromHeader.isPresent()) {
      return tenantFromHeader;
    }

    Optional<String> currentSubdomain = subdomainExtractor.getCurrentSubdomain();
    if (currentSubdomain.isPresent()) {
      return of(getTenantIdBySubdomain(currentSubdomain.get()));
    } else {
      return empty();
    }
  }

  private Long getTenantIdBySubdomain(String currentSubdomain) {
    return tenantService.getRestrictedTenantDataBySubdomain(currentSubdomain).getId();
  }

}

