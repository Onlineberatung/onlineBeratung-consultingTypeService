package de.caritas.cob.consultingtypeservice.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTO;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.schemas.model.BudibaseAuthClientId;
import de.caritas.cob.consultingtypeservice.schemas.model.BudibaseUrl;
import de.caritas.cob.consultingtypeservice.schemas.model.CalcomUrl;
import de.caritas.cob.consultingtypeservice.schemas.model.CalendarAppUrl;
import de.caritas.cob.consultingtypeservice.schemas.model.DisableVideoAppointments;
import de.caritas.cob.consultingtypeservice.schemas.model.DocumentationEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.EnableWalkthrough;
import de.caritas.cob.consultingtypeservice.schemas.model.LegalContentChangesBySingleTenantAdminsAllowed;
import de.caritas.cob.consultingtypeservice.schemas.model.MainTenantSubdomainForSingleDomainMultitenancy;
import de.caritas.cob.consultingtypeservice.schemas.model.MultitenancyEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.MultitenancyWithSingleDomainEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.UseConsultingTypesForAgencies;
import de.caritas.cob.consultingtypeservice.schemas.model.UseOverviewPage;
import de.caritas.cob.consultingtypeservice.schemas.model.UseTenantService;
import org.junit.jupiter.api.Test;

class ApplicationSettingsConverterTest {
  ApplicationSettingsConverter converter = new ApplicationSettingsConverter();

  @Test
  void toDTO_Should_ConvertToDTEmptySettings() {
    // when
    ApplicationSettingsDTO applicationSettingsDTO =
        converter.toDTO(new ApplicationSettingsEntity());

    // then
    assertThat(applicationSettingsDTO.getDisableVideoAppointments()).isNull();
    assertThat(applicationSettingsDTO.getMultitenancyWithSingleDomainEnabled()).isNull();
    assertThat(applicationSettingsDTO.getMultitenancyEnabled()).isNull();
    assertThat(applicationSettingsDTO.getEnableWalkthrough()).isNull();
    assertThat(applicationSettingsDTO.getUseTenantService()).isNull();
    assertThat(applicationSettingsDTO.getUseConsultingTypesForAgencies()).isNull();
    assertThat(applicationSettingsDTO.getBudibaseAuthClientId()).isNull();
    assertThat(applicationSettingsDTO.getCalcomUrl()).isNull();
    assertThat(applicationSettingsDTO.getUseOverviewPage()).isNull();
    assertThat(applicationSettingsDTO.getLegalContentChangesBySingleTenantAdminsAllowed()).isNull();
    assertThat(applicationSettingsDTO.getDocumentationEnabled()).isNull();
  }

  @Test
  void toDTO_Should_ConvertToDTOAllSettings() {
    // given
    ApplicationSettingsEntity applicationSettings = giveApplicationSettings();

    // when
    ApplicationSettingsDTO applicationSettingsDTO = converter.toDTO(applicationSettings);

    // then
    assertThat(applicationSettingsDTO.getMultitenancyEnabled().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getMultitenancyEnabled().getReadOnly()).isTrue();
    assertThat(applicationSettingsDTO.getMultitenancyWithSingleDomainEnabled().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getMultitenancyWithSingleDomainEnabled().getReadOnly())
        .isTrue();
    assertThat(applicationSettingsDTO.getUseTenantService().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getUseTenantService().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getUseConsultingTypesForAgencies().getValue()).isFalse();
    assertThat(applicationSettingsDTO.getUseConsultingTypesForAgencies().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getEnableWalkthrough().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getEnableWalkthrough().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getDisableVideoAppointments().getValue()).isTrue();
    assertThat(applicationSettingsDTO.getDisableVideoAppointments().getReadOnly()).isFalse();
    assertThat(
            applicationSettingsDTO.getMainTenantSubdomainForSingleDomainMultitenancy().getValue())
        .isEqualTo("app");
    assertThat(
            applicationSettingsDTO
                .getMainTenantSubdomainForSingleDomainMultitenancy()
                .getReadOnly())
        .isFalse();
    assertThat(applicationSettingsDTO.getBudibaseAuthClientId().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getBudibaseAuthClientId().getValue())
        .isEqualTo("budibaseAuthClientId");
    assertThat(applicationSettingsDTO.getCalcomUrl().getValue()).isEqualTo("calcomUrl");
    assertThat(applicationSettingsDTO.getBudibaseUrl().getValue()).isEqualTo("budibaseUrl");
    assertThat(applicationSettingsDTO.getCalendarAppUrl().getValue()).isEqualTo("calendarAppUrl");
    assertThat(applicationSettingsDTO.getCalcomUrl().getReadOnly()).isFalse();
    assertThat(applicationSettingsDTO.getUseOverviewPage().getValue()).isFalse();
    assertThat(applicationSettingsDTO.getUseOverviewPage().getReadOnly()).isFalse();
    assertThat(
            applicationSettingsDTO.getLegalContentChangesBySingleTenantAdminsAllowed().getValue())
        .isTrue();
    assertThat(
            applicationSettingsDTO
                .getLegalContentChangesBySingleTenantAdminsAllowed()
                .getReadOnly())
        .isFalse();
  }

  private ApplicationSettingsEntity giveApplicationSettings() {
    var settings = new ApplicationSettingsEntity();
    settings.setMultitenancyWithSingleDomainEnabled(
        new MultitenancyWithSingleDomainEnabled().withReadOnly(true).withValue(true));
    settings.setMultitenancyEnabled(new MultitenancyEnabled().withReadOnly(true).withValue(true));
    settings.setUseTenantService(new UseTenantService().withReadOnly(false).withValue(true));
    settings.setUseConsultingTypesForAgencies(
        new UseConsultingTypesForAgencies().withReadOnly(false).withValue(false));
    settings.setEnableWalkthrough(new EnableWalkthrough().withReadOnly(false).withValue(true));
    settings.setDisableVideoAppointments(
        new DisableVideoAppointments().withReadOnly(false).withValue(true));
    settings.setMainTenantSubdomainForSingleDomainMultitenancy(
        new MainTenantSubdomainForSingleDomainMultitenancy().withReadOnly(false).withValue("app"));
    settings.setBudibaseAuthClientId(
        new BudibaseAuthClientId().withReadOnly(false).withValue("budibaseAuthClientId"));
    settings.setCalcomUrl(new CalcomUrl().withReadOnly(false).withValue("calcomUrl"));
    settings.setBudibaseUrl(new BudibaseUrl().withReadOnly(false).withValue("budibaseUrl"));
    settings.setCalendarAppUrl(
        new CalendarAppUrl().withReadOnly(false).withValue("calendarAppUrl"));
    settings.setUseOverviewPage(new UseOverviewPage().withReadOnly(false).withValue(false));
    settings.setLegalContentChangesBySingleTenantAdminsAllowed(
        new LegalContentChangesBySingleTenantAdminsAllowed().withReadOnly(false).withValue(true));
    settings.setDocumentationEnabled(new DocumentationEnabled().withReadOnly(true).withValue(true));
    return settings;
  }
}
