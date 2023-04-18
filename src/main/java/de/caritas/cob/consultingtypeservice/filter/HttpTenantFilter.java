package de.caritas.cob.consultingtypeservice.filter;

import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantResolver;
import de.caritas.cob.consultingtypeservice.config.SpringFoxConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Sets tenantId for current thread needed for tenant feature. */
@Component
@ConditionalOnExpression("${multitenancy.enabled:true}")
@RequiredArgsConstructor
@Slf4j
public class HttpTenantFilter extends OncePerRequestFilter {

  private static final String[] TENANCY_FILTER_WHITELIST = new String[] { "/actuator/health",
      "/actuator/health/**", "/swagger-ui.html"};
  private final TenantResolver tenantResolver;

  private final DefaultRequiresTenantFilterMatcher requiresTenantFilterMatcher = new DefaultRequiresTenantFilterMatcher();

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (requiresTenantFilterMatcher.matches(request)) {
      log.debug("Trying to resolve tenant for request coming from URI {}", request.getRequestURI());
      Long tenantId = tenantResolver.resolve();
      log.debug("Setting current tenant context to: " + tenantId);
      TenantContext.setCurrentTenant(tenantId);
      filterChain.doFilter(request, response);
      TenantContext.clear();
    } else {
      log.info("Skipping tenant filter for request ");
    }
  }

  class DefaultRequiresTenantFilterMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest request) {

      List<String> tenantWhitelist = new ArrayList<>(Arrays.asList(TENANCY_FILTER_WHITELIST));
      return tenantWhitelist.parallelStream()
          .anyMatch(request.getRequestURI().toLowerCase()::contains);
    }
  }
}
