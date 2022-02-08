package de.caritas.cob.consultingtypeservice.api.service.tenant;

import static java.util.Optional.empty;

import de.caritas.cob.consultingtypeservice.filter.SubdomainExtractor;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.TenantControllerApi;
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

  private @NonNull SubdomainExtractor subdomainExtractor;
  private @NonNull TenantControllerApi tenantControllerApi;

  public Long resolve(HttpServletRequest request) {
    return resolveForNonAuthenticatedUser();
  }

  private Long resolveForNonAuthenticatedUser() {
    Optional<Long> tenantId = resolveTenantFromSubdomain();
    if (tenantId.isEmpty()) {
      throw new AccessDeniedException("Tenant id could not be resolved");
    }
    return tenantId.get();
  }

  private Optional<Long> resolveTenantFromSubdomain() {
    Optional<String> currentSubdomain = subdomainExtractor.getCurrentSubdomain();
    if (currentSubdomain.isPresent()) {
      log.info("Current subdomain: " + currentSubdomain.get());
      return Optional.of(getTenantIdBySubdomain(currentSubdomain.get()));
    } else {
      return empty();
    }
  }

  private Long getTenantIdBySubdomain(String currentSubdomain) {
    return tenantControllerApi.getRestrictedTenantDataBySubdomain(currentSubdomain).getId();
  }

}
