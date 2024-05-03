package de.caritas.cob.consultingtypeservice.api.auth;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthorisationService {

  private final RoleAuthorizationAuthorityMapper roleAuthorizationAuthorityMapper =
      new RoleAuthorizationAuthorityMapper();

  public Object getUsername() {
    return getPrincipal().getClaims().get("username");
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  private Jwt getPrincipal() {
    return (Jwt) getAuthentication().getPrincipal();
  }

  public Collection<GrantedAuthority> extractRealmAuthorities(Jwt jwt) {
    var roles = extractRealmRoles(jwt);
    return roleAuthorizationAuthorityMapper.mapAuthorities(
        roles.stream().collect(Collectors.toSet()));
  }

  public Collection<String> extractRealmRoles(Jwt jwt) {
    Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
    if (realmAccess != null) {
      var roles = (List<String>) realmAccess.get("roles");
      if (roles != null) {
        return roles;
      }
    }
    return Lists.newArrayList();
  }
}
