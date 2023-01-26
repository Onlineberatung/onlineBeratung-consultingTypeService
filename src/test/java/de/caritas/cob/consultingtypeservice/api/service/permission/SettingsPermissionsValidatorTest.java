package de.caritas.cob.consultingtypeservice.api.service.permission;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SettingsPermissionsValidatorTest {

  @Mock private EffectivePermissionsDeterminationService effectivePermissionsDeterminationService;
  private SettingsPermissionsValidator settingsPermissionsValidator;

  @BeforeEach
  void setUp() {
    settingsPermissionsValidator =
        new SettingsPermissionsValidator(effectivePermissionsDeterminationService);
  }

  @Test
  void
      assertUserHasPermissionToChangeSettings_Should_notThrowException_When_userHasAllRequiredPermissions() {}
}
