package de.caritas.cob.consultingtypeservice.api.service;

import com.google.common.collect.Maps;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTOMainTenantSubdomainForSingleDomainMultitenancy;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
@Slf4j
public class ApplicationSettingsConverter {

  public ApplicationSettingsDTO toDTO(ApplicationSettingsEntity applicationSettings) {
    var settingsDTO =
        new ApplicationSettingsDTO()
            .multitenancyWithSingleDomainEnabled(
                toFeatureToggleDTO(applicationSettings.getMultitenancyWithSingleDomainEnabled()))
            .multitenancyEnabled(toFeatureToggleDTO(applicationSettings.getMultitenancyEnabled()))
            .enableWalkthrough(toFeatureToggleDTO(applicationSettings.getEnableWalkthrough()))
            .disableVideoAppointments(
                toFeatureToggleDTO(applicationSettings.getDisableVideoAppointments()))
            .useTenantService(toFeatureToggleDTO(applicationSettings.getUseTenantService()))
            .mainTenantSubdomainForSingleDomainMultitenancy(
                toSettingDTO(
                    applicationSettings.getMainTenantSubdomainForSingleDomainMultitenancy()))
            .useOverviewPage(toFeatureToggleDTO(applicationSettings.getUseOverviewPage()))
            .calcomUrl(toSettingDTO(applicationSettings.getCalcomUrl()))
            .budibaseAuthClientId(toSettingDTO(applicationSettings.getBudibaseAuthClientId()))
            .budibaseUrl(toSettingDTO(applicationSettings.getBudibaseUrl()))
            .calendarAppUrl(toSettingDTO(applicationSettings.getCalendarAppUrl()))
            .legalContentChangesBySingleTenantAdminsAllowed(
                toFeatureToggleDTO(
                    applicationSettings.getLegalContentChangesBySingleTenantAdminsAllowed()))
            .documentationEnabled(toFeatureToggleDTO(applicationSettings.getDocumentationEnabled()))
            .releaseToggles(Maps.newHashMap());

    settingsDTO.getReleaseToggles().putAll(applicationSettings.getReleaseToggles());
    return settingsDTO;
  }

  private ApplicationSettingsDTOMainTenantSubdomainForSingleDomainMultitenancy toSettingDTO(
      Object setting) {
    if (setting == null) {
      return null;
    }
    Boolean readOnly = getFieldValue(setting, "readOnly", Boolean.class);
    String value = getFieldValue(setting, "value", String.class);
    return new ApplicationSettingsDTOMainTenantSubdomainForSingleDomainMultitenancy()
        .readOnly(readOnly)
        .value(value);
  }

  private ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled toFeatureToggleDTO(
      Object setting) {
    if (setting == null) {
      return null;
    }
    Boolean readOnly = getFieldValue(setting, "readOnly", Boolean.class);
    Boolean value = getFieldValue(setting, "value", Boolean.class);
    return new ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled()
        .readOnly(readOnly)
        .value(value);
  }

  private <T> T getFieldValue(Object object, String fieldName, Class<T> fieldType) {
    Field field = ReflectionUtils.findField(object.getClass(), fieldName, fieldType);
    if (field != null) {
      ReflectionUtils.makeAccessible(field);
      return (T) ReflectionUtils.getField(field, object);
    } else {
      log.warn("Field value on object {} not found {} ", object, fieldName);
      return null;
    }
  }
}
