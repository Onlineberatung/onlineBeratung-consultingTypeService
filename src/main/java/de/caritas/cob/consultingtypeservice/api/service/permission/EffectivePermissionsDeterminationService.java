package de.caritas.cob.consultingtypeservice.api.service.permission;

import static de.caritas.cob.consultingtypeservice.api.auth.UserRole.TENANT_ADMIN;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EffectivePermissionsDeterminationService {

  public static final Set<Permission> ALL_SETTINGS_PERMISSIONS =
      Sets.newHashSet(
          Permission.WELCOME_MESSAGE_SETTINGS,
          Permission.SEND_FURTHER_STEPS_MESSAGE_SETTINGS,
          Permission.SEND_SAVE_SESSION_DATA_MESSAGE_SETTINGS,
          Permission.NOTIFICATION_SETTINGS,
          Permission.VIDEO_CALL_ALLOWED_SETTINGS,
          Permission.LANGUAGE_FORMAL_SETTINGS);

  @Value("${multitenancy.enabled}")
  private boolean multitenancyEnabled;

  public Set<Permission> resolveFromCurrentUser() {
    if (isSuperAdmin()) {
      return ALL_SETTINGS_PERMISSIONS;
    } else if (isTenantAdmin()) {
      return tenantAdminPermissions();
    }
    return Collections.emptySet();
  }

  private Set<Permission> tenantAdminPermissions() {
    if (multitenancyEnabled) {
      return ALL_SETTINGS_PERMISSIONS;
    }
    final Set<Permission> permissions = new HashSet<>(ALL_SETTINGS_PERMISSIONS);
    permissions.remove(Permission.LANGUAGE_FORMAL_SETTINGS);

    return permissions;
  }

  public boolean isSuperAdmin() {
    var tenantId = findTenantIdInAccessToken();
    if (tenantId.isEmpty() || !tenantId.get().equals(0L)) {
      return false;
    }
    return hasRole(TENANT_ADMIN.getValue());
  }

  public boolean isTenantAdmin() {
    return hasRole(TENANT_ADMIN.getValue());
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public boolean hasRole(String roleName) {
    Set<String> roles =
        getPrincipal().getKeycloakSecurityContext().getToken().getRealmAccess().getRoles();
    return roles != null && roles.contains(roleName);
  }

  private KeycloakPrincipal getPrincipal() {
    return (KeycloakPrincipal) getAuthentication().getPrincipal();
  }

  private Optional<Long> findTenantIdInAccessToken() {
    Integer tenantId =
        (Integer)
            getPrincipal().getKeycloakSecurityContext().getToken().getOtherClaims().get("tenantId");
    if (tenantId == null) {
      throw new AccessDeniedException("tenantId attribute not found in the access token");
    }
    return Optional.of(Long.valueOf(tenantId));
  }
}
