package de.caritas.cob.consultingtypeservice.api.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
@TestPropertySource(properties = "feature.multitenancy.with.single.domain.enabled=true")
@AutoConfigureMockMvc(addFilters = false)
class ApplicationSettingsControllerIT {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ApplicationSettingsRepository applicationSettingsRepository;

  @BeforeEach
  public void setup() {
    TenantContext.clear();
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(springSecurity())
        .build();
  }

  @Test
  void getApplicationSettings_Should_ReturnApplicationSettings_When_UserIsNotAuthenticated()
      throws Exception {
    mockMvc.perform(
            get("/settings")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.multitenancyWithSingleDomainEnabled.value").value(true))
        .andExpect(jsonPath("$.multitenancyWithSingleDomainEnabled.readOnly").value(true))
        .andExpect(jsonPath("$.multitenancyEnabled.value").value(false))
        .andExpect(jsonPath("$.multitenancyEnabled.readOnly").value(true))
        .andExpect(jsonPath("$.useTenantService.value").value(true))
        .andExpect(jsonPath("$.useTenantService.readOnly").value(false))
        .andExpect(jsonPath("$.enableWalkthrough.value").value(false))
        .andExpect(jsonPath("$.enableWalkthrough.readOnly").value(false))
        .andExpect(jsonPath("$.disableVideoAppointments.value").value(true))
        .andExpect(jsonPath("$.disableVideoAppointments.readOnly").value(false))
        .andExpect(jsonPath("$.mainTenantSubdomainForSingleDomainMultitenancy.value").value("app"))
        .andExpect(jsonPath("$.mainTenantSubdomainForSingleDomainMultitenancy.readOnly").value(false))
        .andExpect(jsonPath("$.budibaseAuthClientId.value").value("budibaseAuthClientId"))
        .andExpect(jsonPath("$.budibaseAuthClientId.readOnly").value(false))
        .andExpect(jsonPath("$.calcomUrl.value").value("calcomUrl"))
        .andExpect(jsonPath("$.calcomUrl.readOnly").value(false))
        .andExpect(jsonPath("$.budibaseUrl.value").value("budibaseUrl"))
        .andExpect(jsonPath("$.budibaseUrl.readOnly").value(false))
        .andExpect(jsonPath("$.calendarAppUrl.value").value("calendarAppUrl"))
        .andExpect(jsonPath("$.calendarAppUrl.readOnly").value(false))
        .andExpect(jsonPath("$.useOverviewPage.value").value(false))
        .andExpect(jsonPath("$.useOverviewPage.readOnly").value(false))
            .andExpect(jsonPath("$.legalContentChangesBySingleTenantAdminsAllowed.value").value(true))
            .andExpect(jsonPath("$.legalContentChangesBySingleTenantAdminsAllowed.readOnly").value(false));
  }

}
