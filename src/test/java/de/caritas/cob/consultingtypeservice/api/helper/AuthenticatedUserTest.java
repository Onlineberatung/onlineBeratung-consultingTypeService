package de.caritas.cob.consultingtypeservice.api.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticatedUserTest {

  @Test(expected = NullPointerException.class)
  public void AuthenticatedUser_Should_ThrowNullPointerExceptionWhenArgumentsAreNull() {
    new AuthenticatedUser(null, null, null);
  }

  @Test(expected = NullPointerException.class)
  public void AuthenticatedUser_Should_ThrowNullPointerExceptionWhenUserIdIsNull() {
    AuthenticatedUser authenticatedUser = new AuthenticatedUser();
    authenticatedUser.setUserId(null);
  }

  @Test(expected = NullPointerException.class)
  public void AuthenticatedUser_Should_ThrowNullPointerExceptionWhenUsernameIsNull() {
    AuthenticatedUser authenticatedUser = new AuthenticatedUser();
    authenticatedUser.setUsername(null);
  }
}
