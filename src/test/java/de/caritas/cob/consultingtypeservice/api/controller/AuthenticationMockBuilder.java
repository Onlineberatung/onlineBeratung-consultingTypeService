package de.caritas.cob.consultingtypeservice.api.controller;

import com.google.common.collect.Lists;
import de.caritas.cob.consultingtypeservice.api.auth.RoleAuthorizationAuthorityMapper;
import java.util.Collection;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationMockBuilder {

  private String userRole;
  private Integer tenantId;

  AuthenticationMockBuilder withUserRole(String userRole) {
    this.userRole = userRole;
    return this;
  }

  public AuthenticationMockBuilder withTenantId(Integer tenantId) {
    this.tenantId = tenantId;
    return this;
  }

  public Authentication build() {
    return new Authentication() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return new RoleAuthorizationAuthorityMapper()
            .mapAuthorities(Lists.newArrayList(() -> userRole));
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
        token.setRealmAccess(new AccessToken.Access().addRole(userRole));
        KeycloakSecurityContext keycloakSecurityContext =
            new KeycloakSecurityContext("", token, null, null);
        return new KeycloakPrincipal<>("name", keycloakSecurityContext);
      }

      @Override
      public boolean isAuthenticated() {
        return true;
      }

      @Override
      public void setAuthenticated(boolean b) throws IllegalArgumentException {}

      @Override
      public String getName() {
        return null;
      }
    };
  }
}
