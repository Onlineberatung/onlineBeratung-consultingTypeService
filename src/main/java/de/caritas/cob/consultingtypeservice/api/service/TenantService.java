package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.config.CacheManagerConfig;
import de.caritas.cob.consultingtypeservice.config.apiclient.TenantServiceApiControllerFactory;
import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.model.RestrictedTenantDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantService {

  private final @NonNull TenantServiceApiControllerFactory tenantServiceApiControllerFactory;

  @Cacheable(cacheNames = CacheManagerConfig.TENANT_CACHE, key = "#subdomain")
  public RestrictedTenantDTO getRestrictedTenantDataBySubdomain(String subdomain) {
    return getRestrictedTenantDTO(subdomain);
  }

  public RestrictedTenantDTO getRestrictedTenantDataBySubdomainNoCache(String subdomain) {
    return getRestrictedTenantDTO(subdomain);
  }

  private RestrictedTenantDTO getRestrictedTenantDTO(String subdomain) {
    Long tenantIdToOverride = null;
    return tenantServiceApiControllerFactory
        .createControllerApi()
        .getRestrictedTenantDataBySubdomainWithHttpInfo(subdomain, tenantIdToOverride)
        .getBody();
  }

  @Cacheable(cacheNames = CacheManagerConfig.TENANT_CACHE, key = "#tenantId")
  public RestrictedTenantDTO getRestrictedTenantData(Long tenantId) {
    return getRestrictedTenantDTO(tenantId);
  }

  public RestrictedTenantDTO getRestrictedTenantDataNoCache(Long tenantId) {
    return getRestrictedTenantDTO(tenantId);
  }

  private RestrictedTenantDTO getRestrictedTenantDTO(Long tenantId) {
    log.info("Calling tenant service to get tenant data for tenantId {}", tenantId);
    return tenantServiceApiControllerFactory
        .createControllerApi()
        .getRestrictedTenantDataByTenantId(tenantId);
  }
}
