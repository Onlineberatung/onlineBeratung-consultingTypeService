package de.caritas.cob.consultingtypeservice.config;

import de.caritas.cob.consultingtypeservice.filter.HttpTenantFilter;
import de.caritas.cob.consultingtypeservice.filter.StatelessCsrfFilter;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * Provides the Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${csrf.cookie.property}")
  private String csrfCookieProperty;

  @Value("${csrf.header.property}")
  private String csrfHeaderProperty;

  @Value("${multitenancy.enabled}")
  private boolean multitenancy;

  @Autowired
  private @Nullable HttpTenantFilter tenantFilter;

  /**
   * Configure spring security filter chain
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    var httpSecurity = http.csrf().disable()
        .addFilterBefore(new StatelessCsrfFilter(csrfCookieProperty, csrfHeaderProperty),
            CsrfFilter.class);

    httpSecurity = enableTenantFilterIfMultitenancyEnabled(httpSecurity);

    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy()).and()
        .authorizeRequests().anyRequest().permitAll();
  }

  /**
   * Adds additional filter for tenant feature if enabled that sets tenant_id into current thread.
   *
   * @param httpSecurity
   * @return
   */
  private HttpSecurity enableTenantFilterIfMultitenancyEnabled(HttpSecurity httpSecurity) {
    if (multitenancy) {
      httpSecurity = httpSecurity
          .addFilterAfter(this.tenantFilter, StatelessCsrfFilter.class);
    }
    return httpSecurity;
  }
}
