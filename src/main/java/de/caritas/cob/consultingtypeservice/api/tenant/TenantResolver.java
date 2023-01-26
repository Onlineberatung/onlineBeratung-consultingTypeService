package de.caritas.cob.consultingtypeservice.api.tenant;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import de.caritas.cob.consultingtypeservice.api.service.TenantHeaderSupplier;
import de.caritas.cob.consultingtypeservice.api.service.TenantService;
import de.caritas.cob.consultingtypeservice.filter.SubdomainExtractor;
import java.util.Optional;
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

  public static final Long TECHNICAL_TENANT_ID = 0L;
  private final @NonNull TenantHeaderSupplier tenantHeaderSupplier;
  private @NonNull SubdomainExtractor subdomainExtractor;
  private @NonNull TenantService tenantService;

  public Long resolve() {
    return resolveForNonAuthenticatedUser();
  }

  private Long resolveForNonAuthenticatedUser() {
    Optional<Long> tenantId = resolveTenantFromHttpRequest();
    if (tenantId.isEmpty()) {
      log.warn("Tenant id could not be resolved for request");
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
