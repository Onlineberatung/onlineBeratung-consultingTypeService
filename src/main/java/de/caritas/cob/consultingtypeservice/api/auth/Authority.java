package de.caritas.cob.consultingtypeservice.api.auth;

import static java.util.Collections.emptyList;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority {
  TENANT_ADMIN(
      UserRole.TENANT_ADMIN,
      Lists.newArrayList(
          AuthorityValue.PATCH_APPLICATION_SETTINGS,
          AuthorityValue.CREATE_CONSULTING_TYPE,
          AuthorityValue.PATCH_CONSULTING_TYPE)),
  SINGLE_TENANT_ADMIN(
      UserRole.SINGLE_TENANT_ADMIN,
      Lists.newArrayList(AuthorityValue.LIMITED_PATCH_CONSULTING_TYPE)),

  TOPIC_ADMIN(
      UserRole.TOPIC_ADMIN,
      Lists.newArrayList(
          AuthorityValue.CREATE_TOPIC,
          AuthorityValue.UPDATE_TOPIC,
          AuthorityValue.GET_ALL_TOPICS,
          AuthorityValue.GET_TOPICS_TRANSLATION_BY_ID));

  private final UserRole userRole;
  private final List<String> grantedAuthorities;

  public static List<String> getAuthoritiesByUserRole(UserRole userRole) {
    Optional<Authority> authorityByUserRole =
        Stream.of(values()).filter(authority -> authority.userRole.equals(userRole)).findFirst();

    return authorityByUserRole.isPresent()
        ? authorityByUserRole.get().getGrantedAuthorities()
        : emptyList();
  }

  public static class AuthorityValue {

    private AuthorityValue() {}

    public static final String PREFIX = "AUTHORIZATION_";
    public static final String PATCH_APPLICATION_SETTINGS = PREFIX + "PATCH_APPLICATION_SETTINGS";
    public static final String CREATE_CONSULTING_TYPE = PREFIX + "CREATE_CONSULTING_TYPE";
    public static final String PATCH_CONSULTING_TYPE = PREFIX + "PATCH_CONSULTING_TYPE";

    public static final String LIMITED_PATCH_CONSULTING_TYPE =
        PREFIX + "LIMITED_PATCH_CONSULTING_TYPE";
    public static final String CREATE_TOPIC = PREFIX + "CREATE_TOPIC";
    public static final String UPDATE_TOPIC = PREFIX + "UPDATE_TOPIC";
    public static final String GET_ALL_TOPICS = PREFIX + "GET_ALL_TOPICS";
    public static final String GET_TOPICS_TRANSLATION_BY_ID =
        PREFIX + "GET_TOPICS_TRANSLATION_BY_ID";
  }
}
