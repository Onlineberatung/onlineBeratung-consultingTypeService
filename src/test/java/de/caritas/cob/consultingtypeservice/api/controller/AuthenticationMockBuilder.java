package de.caritas.cob.consultingtypeservice.api.controller;

import com.google.common.collect.Lists;
import java.util.Collection;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationMockBuilder {

  private String authority;
  private String tenantId;

  AuthenticationMockBuilder withAuthority(String authority) {
    this.authority = authority;
    return this;
  }

  AuthenticationMockBuilder withTenantIdAttribute(String tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public Authentication build() {
    return new Authentication() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.newArrayList((GrantedAuthority) () -> authority);
      }

      @Override
      public Object getCredentials() {
        return null;
      }

      @Override
      public Object getDetails() {
        return null;
      }

      @Override
      public Object getPrincipal() {
        AccessToken token = new AccessToken();
        token.setOtherClaims("tenantId", tenantId);
        KeycloakSecurityContext keycloakSecurityContext = new KeycloakSecurityContext("", token,
            null, null);
        return new KeycloakPrincipal<>("name", keycloakSecurityContext);
      }

      @Override
      public boolean isAuthenticated() {
        return true;
      }

      @Override
      public void setAuthenticated(boolean b) throws IllegalArgumentException {

      }

      @Override
      public String getName() {
        return null;
      }
    };
  }
}
