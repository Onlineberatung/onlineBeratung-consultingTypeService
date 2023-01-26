package de.caritas.cob.consultingtypeservice.api.service.permission;

import com.google.common.collect.Sets;
import de.caritas.cob.consultingtypeservice.api.exception.SettingsPermissionException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypePatchDTO;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsPermissionsValidator {

  private final @NonNull EffectivePermissionsDeterminationService
      effectivePermissionsDeterminationService;

  public void assertUserHasPermissionToChangeSettings(
      ConsultingTypePatchDTO consultingTypePatchDTO) {
    final Set<Permission> requiredPermissions = resolveRequiredPermissions(consultingTypePatchDTO);
    final Set<Permission> permissionsOfCurrentUser =
        effectivePermissionsDeterminationService.resolveFromCurrentUser();
    final Set<Permission> missingPermissions =
        Sets.difference(requiredPermissions, permissionsOfCurrentUser);
    if (!missingPermissions.isEmpty()) {
      final String missingPermissionsNames =
          missingPermissions.stream().map(Permission::name).collect(Collectors.joining(", "));
      throw new SettingsPermissionException(
          String.format(
              "User has not the required permissions to change the settings. [%s] are missing",
              missingPermissionsNames));
    }
  }

  private Set<Permission> resolveRequiredPermissions(
      final ConsultingTypePatchDTO consultingTypePatchDTO) {
    final Set<Permission> requiredPermissions = new HashSet<>();
    if (consultingTypePatchDTO.getWelcomeMessage() != null) {
      requiredPermissions.add(Permission.WELCOME_MESSAGE_SETTINGS);
    }
    if (consultingTypePatchDTO.getLanguageFormal() != null) {
      requiredPermissions.add(Permission.LANGUAGE_FORMAL_SETTINGS);
    }
    if (consultingTypePatchDTO.getSendFurtherStepsMessage() != null) {
      requiredPermissions.add(Permission.SEND_FURTHER_STEPS_MESSAGE_SETTINGS);
    }
    if (consultingTypePatchDTO.getIsVideoCallAllowed() != null) {
      requiredPermissions.add(Permission.VIDEO_CALL_ALLOWED_SETTINGS);
    }
    if (consultingTypePatchDTO.getSendSaveSessionDataMessage() != null) {
      requiredPermissions.add(Permission.SEND_SAVE_SESSION_DATA_MESSAGE_SETTINGS);
    }
    if (consultingTypePatchDTO.getNotifications() != null) {
      requiredPermissions.add(Permission.NOTIFICATION_SETTINGS);
    }
    return requiredPermissions;
  }
}
