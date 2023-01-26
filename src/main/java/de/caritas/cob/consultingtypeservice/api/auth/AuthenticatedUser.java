package de.caritas.cob.consultingtypeservice.api.auth;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/** Representation of the via Keycloak authenticated user */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticatedUser {

  @NonNull private String userId;

  @NonNull private String username;

  private Set<String> roles;

  @NonNull private String accessToken;

  private Set<String> grantedAuthorities;

  @JsonIgnore
  public boolean isTopicAdmin() {
    return nonNull(roles) && roles.contains(UserRole.TOPIC_ADMIN.getValue());
  }
}
