package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
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
import de.caritas.cob.consultingtypeservice.schemas.model.UseOverviewPage;
import de.caritas.cob.consultingtypeservice.schemas.model.UseTenantService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Loader for the consulting types from the file system. */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultApplicationSettingsInitializer {

  @Autowired private ApplicationSettingsRepository applicationSettingsRepository;

  @Value("${multitenancy.enabled}")
  private boolean multitenancy;

  @Value("${feature.multitenancy.with.single.domain.enabled}")
  private boolean multitenancyWithSingleDomainEnabled;

  @Value("${setting.main.tenant.subdomain.for.single.domain.multitenancy}")
  private String mainTenantSubdomainForSingleDomainMultitenancy;

  @PostConstruct
  private void init() {
    if (applicationSettingsRepository.findAll().isEmpty()) {
      log.info("Initializing default application settings in mongo db");
      applicationSettingsRepository.save(createDefaultApplicationSettings());
    }
  }

  private ApplicationSettingsEntity createDefaultApplicationSettings() {
    ApplicationSettingsEntity entity = new ApplicationSettingsEntity();
    entity.setDisableVideoAppointments(
        new DisableVideoAppointments().withValue(true).withReadOnly(false));
    entity.setEnableWalkthrough(new EnableWalkthrough().withValue(false).withReadOnly(false));
    entity.setUseTenantService(
        new UseTenantService()
            .withValue(multitenancyWithSingleDomainEnabled || multitenancy)
            .withReadOnly(false));
    entity.setMultitenancyEnabled(
        new MultitenancyEnabled().withValue(multitenancy).withReadOnly(true));
    entity.setMultitenancyWithSingleDomainEnabled(
        new MultitenancyWithSingleDomainEnabled()
            .withReadOnly(true)
            .withValue(multitenancyWithSingleDomainEnabled));
    entity.setMainTenantSubdomainForSingleDomainMultitenancy(
        new MainTenantSubdomainForSingleDomainMultitenancy()
            .withReadOnly(false)
            .withValue(mainTenantSubdomainForSingleDomainMultitenancy));
    entity.setUseOverviewPage(new UseOverviewPage().withValue(false).withReadOnly(false));
    entity.setCalcomUrl(new CalcomUrl().withValue("calcomUrl").withReadOnly(false));
    entity.setBudibaseAuthClientId(
        new BudibaseAuthClientId().withValue("budibaseAuthClientId").withReadOnly(false));
    entity.setBudibaseUrl(new BudibaseUrl().withValue("budibaseUrl").withReadOnly(false));
    entity.setCalendarAppUrl(new CalendarAppUrl().withValue("calendarAppUrl").withReadOnly(false));
    entity.setLegalContentChangesBySingleTenantAdminsAllowed(
        new LegalContentChangesBySingleTenantAdminsAllowed().withValue(true).withReadOnly(false));
    entity.setDocumentationEnabled(new DocumentationEnabled().withValue(false).withReadOnly(true));
    return entity;
  }
}
