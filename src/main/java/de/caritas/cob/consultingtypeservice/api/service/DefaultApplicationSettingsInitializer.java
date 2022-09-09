package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
import de.caritas.cob.consultingtypeservice.schemas.model.DisableVideoAppointments;
import de.caritas.cob.consultingtypeservice.schemas.model.EnableTenantTheming;
import de.caritas.cob.consultingtypeservice.schemas.model.EnableWalkthrough;
import de.caritas.cob.consultingtypeservice.schemas.model.MultitenancyEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.MultitenancyWithSingleDomainEnabled;
import de.caritas.cob.consultingtypeservice.schemas.model.UseTenantService;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Loader for the consulting types from the file system.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultApplicationSettingsInitializer {

  @Autowired
  private ApplicationSettingsRepository applicationSettingsRepository;

  @Value("${multitenancy.enabled}")
  private boolean multitenancy;

  @Value("${feature.multitenancy.with.single.domain.enabled}")
  private boolean multitenancyWithSingleDomainEnabled;

  @PostConstruct
  private void init() {
    if (applicationSettingsRepository.findAll().isEmpty()) {
      log.info("Initializing default application settings in mongo db");
      applicationSettingsRepository.save(createDefaultApplicationSettings());
    }
  }

  private ApplicationSettingsEntity createDefaultApplicationSettings() {
    ApplicationSettingsEntity entity = new ApplicationSettingsEntity();
    entity.setDisableVideoAppointments(new DisableVideoAppointments().withValue(true).withReadOnly(false));
    entity.setEnableWalkthrough(new EnableWalkthrough().withValue(false).withReadOnly(false));
    entity.setUseTenantService(new UseTenantService().withValue(multitenancy).withReadOnly(false));
    entity.setEnableTenantTheming(new EnableTenantTheming().withValue(multitenancy).withReadOnly(false));
    entity.setMultitenancyEnabled(new MultitenancyEnabled().withValue(multitenancy).withReadOnly(true));
    entity.setMultitenancyWithSingleDomainEnabled(new MultitenancyWithSingleDomainEnabled().withReadOnly(true).withValue(multitenancyWithSingleDomainEnabled));
    return entity;
  }
}
