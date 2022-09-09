package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.schemas.model.DisableVideoAppointments;
import de.caritas.cob.consultingtypeservice.schemas.model.EnableTenantTheming;
import de.caritas.cob.consultingtypeservice.schemas.model.EnableWalkthrough;
import de.caritas.cob.consultingtypeservice.schemas.model.MultitenancyEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.MultitenancyWithSingleDomainEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.UseTenantService;
import org.junit.jupiter.api.Test;

class ApplicationSettingsConverterTest {
  ApplicationSettingsConverter converter = new ApplicationSettingsConverter();

  @Test
  void toDTO_Should_ConvertToDTEmptySettings() {
    // when
    ApplicationSettingsDTO applicationSettingsDTO = converter.toDTO(
        new ApplicationSettingsEntity());

    // then
    assertThat(applicationSettingsDTO.getDisableVideoAppointments()).isNull();
    assertThat(applicationSettingsDTO.getMultitenancyWithSingleDomainEnabled()).isNull();
    assertThat(applicationSettingsDTO.getMultitenancyEnabled()).isNull();
    assertThat(applicationSettingsDTO.getEnableTenantTheming()).isNull();
    assertThat(applicationSettingsDTO.getEnableWalkthrough()).isNull();
    assertThat(applicationSettingsDTO.getUseTenantService()).isNull();
  }

  @Test
  void toDTO_Should_ConvertToDTOAllSettings() {
    // given
    ApplicationSettingsEntity applicationSettings = giveApplicationSettings();

    // when
    ApplicationSettingsDTO applicationSettingsDTO = converter.toDTO(
        applicationSettings);

    // then
    assertThat(applicationSettingsDTO.getMultitenancyEnabled().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getMultitenancyEnabled().getReadOnly()).isTrue();
    assertThat(applicationSettingsDTO.getMultitenancyWithSingleDomainEnabled().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getMultitenancyWithSingleDomainEnabled().getReadOnly()).isTrue();
    assertThat(applicationSettingsDTO.getEnableTenantTheming().getValue()).isFalse();
    assertThat(applicationSettingsDTO.getEnableTenantTheming().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getUseTenantService().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getUseTenantService().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getEnableWalkthrough().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getEnableWalkthrough().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getDisableVideoAppointments().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getDisableVideoAppointments().getReadOnly()).isFalse();
  }

  private ApplicationSettingsEntity giveApplicationSettings() {
    var settings = new ApplicationSettingsEntity();
    settings.setMultitenancyWithSingleDomainEnabled(new MultitenancyWithSingleDomainEnabled().withReadOnly(true).withValue(true));
    settings.setMultitenancyEnabled(new MultitenancyEnabled().withReadOnly(true).withValue(true));
    settings.setEnableTenantTheming(new EnableTenantTheming().withReadOnly(false).withValue(false));
    settings.setUseTenantService(new UseTenantService().withReadOnly(false).withValue(true));
    settings.setEnableWalkthrough(new EnableWalkthrough().withReadOnly(false).withValue(true));
    settings.setDisableVideoAppointments(new DisableVideoAppointments().withReadOnly(false).withValue(true));
    return settings;
  }
}