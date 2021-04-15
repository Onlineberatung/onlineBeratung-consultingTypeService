package de.caritas.cob.consultingtypeservice.api.authorization;

import static de.caritas.cob.consultingtypeservice.api.authorization.Authority.CONSULTING_TYPE_ADMIN;
import static de.caritas.cob.consultingtypeservice.api.authorization.Authority.fromRoleName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

public class AuthorityTest {

  @Test
  public void getAuthority_Should_returnExpectedAuthority_When_authorityIsConsultingTypeAdmin() {
    String authority = CONSULTING_TYPE_ADMIN.getAuthority();

    assertThat(authority, is("AUTHORIZATION_CONSULTING_TYPE_ADMIN"));
  }

  @Test
  public void fromRoleName_Should_returnNull_When_roleNameIsNull() {
    Authority authority = fromRoleName(null);

    assertThat(authority, nullValue());
  }

  @Test
  public void fromRoleName_Should_returnNull_When_roleNameDoesNotExist() {
    Authority authority = fromRoleName("not existing");

    assertThat(authority, nullValue());
  }

  @Test
  public void fromRoleName_Should_returnConsultingTypeAdmin_When_roleNameIsConsultingTypeAdmin() {
    Authority authority = fromRoleName("consulting-type-admin");

    assertThat(authority, is(CONSULTING_TYPE_ADMIN));
  }

}
