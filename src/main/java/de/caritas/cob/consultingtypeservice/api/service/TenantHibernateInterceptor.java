package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.repository.TenantAware;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;

@RequiredArgsConstructor
@Slf4j
public class TenantHibernateInterceptor extends EmptyInterceptor {

  private static final Long TECHNICAL_TENANT_ID = 0L;

  private static Boolean multiTenancyEnabled;

  @Override
  public void preFlush(Iterator entities) {

    if (multiTenancyEnabled == null) {
      multiTenancyEnabled = resolveMultiTenancy();
    }

    if (multiTenancyEnabled) {
      Object entity;
      while (entities.hasNext()) {
        entity = entities.next();
        if (entity instanceof TenantAware) {
          var tenantAware = (TenantAware) entity;
          if (tenantAware.getTenantId() == null
              && !TECHNICAL_TENANT_ID.equals(TenantContext.getCurrentTenant())) {
            ((TenantAware) entity).setTenantId(TenantContext.getCurrentTenant());
          }
        }
      }
    }

    super.preFlush(entities);
  }

  private boolean resolveMultiTenancy() {
    var multiTenancyValue = getParamFromEnvVariable();

    if (multiTenancyValue == null) {
      multiTenancyValue = getParamFromPropertyFile();
    }

    return Boolean.parseBoolean(multiTenancyValue);
  }

  private String getParamFromPropertyFile() {
    try {
      Properties prop = new Properties();
      prop.load(
          TenantHibernateInterceptor.class
              .getClassLoader()
              .getResourceAsStream("application-local.properties"));
      return prop.getProperty("multitenancy.enabled");

    } catch (IOException ex) {
      throw new RuntimeException("No application-local.properties file defined");
    }
  }

  private String getParamFromEnvVariable() {
    return System.getenv().get("MULTITENANCY_ENABLED");
  }
}
