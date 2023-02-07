package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.exception.TopicAccessDeniedException;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.Settings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TopicFeatureAuthorisationService {

  @Value("${multitenancy.enabled}")
  private boolean multitenancy;

  @Value("${feature.multitenancy.with.single.domain.enabled}")
  private boolean multitenancyWithSingleDomainEnabled;

  private final @NonNull TenantService tenantService;

  private final @NonNull ApplicationSettingsService applicationSettingsService;

  public void assertTopicFeatureIsEnabled() throws TopicAccessDeniedException {
    if (!determineIfTopicIsEnabled()) {
      throw new TopicAccessDeniedException("feature topics disabled");
    }
  }

  private boolean determineIfTopicIsEnabled() {
    if (multitenancy) {
      return isTopicFeatureEnabledForMultitenancy();
    }
    if (multitenancyWithSingleDomainEnabled) {
      return isTopicFeatureEnabledForSingleDomainMultitenancy();
    }
    return false;
  }

  private boolean isTopicFeatureEnabledForSingleDomainMultitenancy() {
    ApplicationSettingsEntity entity =
        applicationSettingsService.getApplicationSettings().orElseThrow();
    String mainTenantSubdomain =
        entity.getMainTenantSubdomainForSingleDomainMultitenancy().getValue();
    RestrictedTenantDTO restrictedTenantDataBySubdomain =
        tenantService.getRestrictedTenantDataBySubdomainNoCache(mainTenantSubdomain);
    return isTopicFeatureEnabled(restrictedTenantDataBySubdomain);
  }

  private boolean isTopicFeatureEnabledForMultitenancy() {
    Long currentTenant = TenantContext.getCurrentTenant();
    RestrictedTenantDTO restrictedTenantData =
        tenantService.getRestrictedTenantDataNoCache(currentTenant);
    return isTopicFeatureEnabled(restrictedTenantData);
  }

  private boolean isTopicFeatureEnabled(RestrictedTenantDTO restrictedTenantData) {
    Settings settings = restrictedTenantData.getSettings();
    if (settings == null) {
      log.warn("Tenant has no settings!");
      return false;
    }
    Boolean featureTopicsEnabled = settings.getFeatureTopicsEnabled();
    return featureTopicsEnabled != null && featureTopicsEnabled;
  }
}
