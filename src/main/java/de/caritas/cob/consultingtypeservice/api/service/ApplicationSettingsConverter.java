package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.model.FeatureToggleDTO;
import de.caritas.cob.consultingtypeservice.api.model.SettingDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.MainTenantSubdomainForSingleDomainMultitenancy;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
@Slf4j
public class ApplicationSettingsConverter {

  public ApplicationSettingsDTO toDTO(ApplicationSettingsEntity applicationSettings) {
    return new ApplicationSettingsDTO()
        .multitenancyWithSingleDomainEnabled(
            toFeatureToggleDTO(applicationSettings.getMultitenancyWithSingleDomainEnabled()))
        .multitenancyEnabled(
            toFeatureToggleDTO(applicationSettings.getMultitenancyEnabled()))
        .enableWalkthrough(
            toFeatureToggleDTO(applicationSettings.getEnableWalkthrough()))
        .disableVideoAppointments(
            toFeatureToggleDTO(applicationSettings.getDisableVideoAppointments()))
        .useTenantService(
            toFeatureToggleDTO(applicationSettings.getUseTenantService()))
        .mainTenantSubdomainForSingleDomainMultitenancy(
            toSettingDTO(applicationSettings.getMainTenantSubdomainForSingleDomainMultitenancy()))
        .useOverviewPage(toFeatureToggleDTO(applicationSettings.getUseOverviewPage()))
        .calcomUrl(toSettingDTO(applicationSettings.getCalcomUrl()))
        .budibaseAuthClientId(toSettingDTO(applicationSettings.getBudibaseAuthClientId()))
        .budibaseUrl(toSettingDTO(applicationSettings.getBudibaseUrl()))
        .calendarAppUrl(toSettingDTO(applicationSettings.getCalendarAppUrl()));
  }

  private SettingDTO toSettingDTO(Object setting) {
    if (setting == null) {
      return null;
    }
    Boolean readOnly = getFieldValue(setting, "readOnly", Boolean.class);
    String value = getFieldValue(setting, "value", String.class);
    return new SettingDTO()
        .readOnly(readOnly)
        .value(value);
  }

  private FeatureToggleDTO toFeatureToggleDTO(
      Object setting) {
    if (setting == null) {
      return null;
    }
    Boolean readOnly = getFieldValue(setting, "readOnly", Boolean.class);
    Boolean value = getFieldValue(setting, "value", Boolean.class);
    return new FeatureToggleDTO()
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
