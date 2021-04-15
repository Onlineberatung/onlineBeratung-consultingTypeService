package de.caritas.cob.consultingtypeservice.api.authorization;

import java.util.stream.Stream;

/**
 *
 * Definition of all authorities and of the role-authority-mapping.
 *
 */
public enum Authority {

  CONSULTING_TYPE_ADMIN("consulting-type-admin", "AUTHORIZATION_CONSULTING_TYPE_ADMIN");

  private final String roleName;
  private final String authorityName;

  Authority(final String roleName, final String authorityName) {
    this.roleName = roleName;
    this.authorityName = authorityName;
  }

  /**
   * Finds a {@link Authority} instance by given roleName.
   *
   * @param roleName the role name to search for
   * @return the {@link Authority} instance
   */
  public static Authority fromRoleName(String roleName) {
    return Stream.of(values())
        .filter(authority -> authority.roleName.equals(roleName))
        .findFirst()
        .orElse(null);
  }

  /**
   * Get all authorities for a specific role.
   *
   * @return the authorities for current role
   **/
  public String getAuthority() {
    return this.authorityName;
  }

}
