package de.caritas.cob.consultingtypeservice.filter;

import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantResolver;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Sets tenantId for current thread needed for tenant feature.
 */

@Component
@ConditionalOnExpression("${multitenancy.enabled:true}")
@RequiredArgsConstructor
@Slf4j
public class HttpTenantFilter extends OncePerRequestFilter {

  private final TenantResolver tenantResolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    log.debug("Trying to resolve tenant for request coming from URI {}", request.getRequestURI());
    Long tenantId = tenantResolver.resolve();
    log.debug("Setting current tenant context to: " + tenantId);
    TenantContext.setCurrentTenant(tenantId);
    filterChain.doFilter(request, response);
    TenantContext.clear();
  }
}
