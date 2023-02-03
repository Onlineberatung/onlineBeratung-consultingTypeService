package de.caritas.cob.consultingtypeservice.api.auth;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
  TOPIC_ADMIN("topic-admin"),
  TENANT_ADMIN("tenant-admin"),

  SINGLE_TENANT_ADMIN("single-tenant-admin");

  private final String value;

  public static Optional<UserRole> getRoleByValue(String value) {
    return Arrays.stream(values()).filter(userRole -> userRole.value.equals(value)).findFirst();
  }
}
