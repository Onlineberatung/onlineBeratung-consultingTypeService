package de.caritas.cob.consultingtypeservice.config.apiclient;

import de.caritas.cob.consultingtypeservice.tenantservice.generated.web.TenantControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TenantServiceApiControllerFactory {

  @Value("${tenant.service.api.url}")
  private String tenantServiceApiUrl;

  @Autowired private RestTemplate restTemplate;

  public TenantControllerApi createControllerApi() {
    var apiClient = new TenantServiceApiClient(restTemplate).setBasePath(this.tenantServiceApiUrl);
    return new TenantControllerApi(apiClient);
  }
}
