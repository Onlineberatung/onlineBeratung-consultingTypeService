package de.caritas.cob.consultingtypeservice.config;

import de.caritas.cob.consultingtypeservice.api.auth.RoleAuthorizationAuthorityMapper;
import de.caritas.cob.consultingtypeservice.filter.HttpTenantFilter;
import de.caritas.cob.consultingtypeservice.filter.StatelessCsrfFilter;
import javax.annotation.Nullable;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

/** Provides the Security configuration. */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  @Value("${csrf.cookie.property}")
  private String csrfCookieProperty;

  @Value("${csrf.header.property}")
  private String csrfHeaderProperty;

  @Value("${multitenancy.enabled}")
  private boolean multitenancy;

  @Autowired(required = false)
  private @Nullable HttpTenantFilter tenantFilter;

  /** Configure spring security filter chain */
  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    var httpSecurity =
        http.csrf()
            .disable()
            .addFilterBefore(
                new StatelessCsrfFilter(csrfCookieProperty, csrfHeaderProperty), CsrfFilter.class);

    httpSecurity = enableTenantFilterIfMultitenancyEnabled(httpSecurity);

    httpSecurity
        .csrf()
        .disable()
        .authenticationProvider(keycloakAuthenticationProvider())
        .addFilterBefore(keycloakAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .sessionAuthenticationStrategy(sessionAuthenticationStrategy())
        .and()
        .authorizeRequests()
        .requestMatchers(new AntPathRequestMatcher("/settings"))
        .permitAll()
        .requestMatchers(new AntPathRequestMatcher("/settings/*"))
        .permitAll()
        .requestMatchers(new AntPathRequestMatcher("/topic/public"))
        .permitAll()
        .requestMatchers(new AntPathRequestMatcher("/topic/public/*"))
        .permitAll()
        .requestMatchers(new AntPathRequestMatcher("/topic"))
        .authenticated()
        .requestMatchers(new AntPathRequestMatcher("/topic/*"))
        .authenticated()
        .requestMatchers(new AntPathRequestMatcher("/topicadmin"))
        .authenticated()
        .requestMatchers(new AntPathRequestMatcher("/topicadmin/*"))
        .authenticated()
        .requestMatchers(new AntPathRequestMatcher("/settingsadmin"))
        .authenticated()
        .requestMatchers(new AntPathRequestMatcher("/settingsadmin/*"))
        .authenticated()
        .requestMatchers(new NegatedRequestMatcher(new AntPathRequestMatcher("/topic")))
        .permitAll()
        .requestMatchers(new NegatedRequestMatcher(new AntPathRequestMatcher("/topic/*")))
        .permitAll()
        .requestMatchers(new NegatedRequestMatcher(new AntPathRequestMatcher("/topicadmin")))
        .permitAll()
        .requestMatchers(new NegatedRequestMatcher(new AntPathRequestMatcher("/topicadmin/*")))
        .permitAll()
        .anyRequest()
        .permitAll()
        .and()
        .headers()
        .xssProtection()
        .and()
        .contentSecurityPolicy("script-src 'self'");
  }

  /**
   * Adds additional filter for tenant feature if enabled that sets tenant_id into current thread.
   *
   * @param httpSecurity
   * @return
   */
  private HttpSecurity enableTenantFilterIfMultitenancyEnabled(HttpSecurity httpSecurity) {
    if (multitenancy) {
      httpSecurity = httpSecurity.addFilterAfter(this.tenantFilter, StatelessCsrfFilter.class);
    }
    return httpSecurity;
  }

  @Bean
  public KeycloakConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new NullAuthenticatedSessionStrategy();
  }

  @Autowired
  public void configureGlobal(
      final AuthenticationManagerBuilder auth, RoleAuthorizationAuthorityMapper authorityMapper) {
    final KeycloakAuthenticationProvider keycloakAuthenticationProvider =
        keycloakAuthenticationProvider();
    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(authorityMapper);
    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Override
  protected KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
    var provider = new KeycloakAuthenticationProvider();
    provider.setGrantedAuthoritiesMapper(new RoleAuthorizationAuthorityMapper());
    return provider;
  }
}
