package de.caritas.cob.consultingtypeservice.api.controller;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.auth.UserRole;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled;
import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsPatchDTO;
import de.caritas.cob.consultingtypeservice.api.repository.ApplicationSettingsRepository;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.api.util.JsonConverter;
import de.caritas.cob.consultingtypeservice.testHelper.TopicPathConstants;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.util.Map;

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
                    MockMvcRequestBuilders.get("/settings").accept(APPLICATION_JSON))
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

  @Test
  void patchApplicationSettings_Should_ReturnUpdatedApplicationSettings_When_PatchOperationSuccessful()
          throws Exception {
    final Authentication authentication = givenMockAuthentication(UserRole.TENANT_ADMIN);
    ApplicationSettingsPatchDTO patchDTO = new ApplicationSettingsPatchDTO();
    patchDTO.setLegalContentChangesBySingleTenantAdminsAllowed(new ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled().value(false).readOnly(true));
    String jsonRequest = JsonConverter.convertToJson(patchDTO);
    mockMvc.perform(patch("/settingsadmin")
            .with(authentication(authentication))
                    .header("csrfHeader", "csrfToken")
                    .cookie(new Cookie("csrfCookie", "csrfToken"))
            .contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON)
            .content(jsonRequest)
            .contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON))
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
            .andExpect(jsonPath("$.legalContentChangesBySingleTenantAdminsAllowed.value").value(false))
            .andExpect(jsonPath("$.legalContentChangesBySingleTenantAdminsAllowed.readOnly").value(false));

    // clean up
    resetSettingsToPreviousState(authentication);
  }

  private void resetSettingsToPreviousState(Authentication authentication) throws Exception {
    var patchDTO = new ApplicationSettingsPatchDTO();
    patchDTO.setLegalContentChangesBySingleTenantAdminsAllowed(new ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled().value(true).readOnly(true));
    var jsonRequest = JsonConverter.convertToJson(patchDTO);
    mockMvc.perform(patch("/settingsadmin")
                    .with(authentication(authentication))
                    .header("csrfHeader", "csrfToken")
                    .cookie(new Cookie("csrfCookie", "csrfToken"))
                    .contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
                    .contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
  }


  @Test
  void patchApplicationSettings_Should_ReturnForbidden_When_UserNameDoesNotHavePermissionToPatchSettings()
          throws Exception {
    final Authentication authentication = givenMockAuthentication(UserRole.TOPIC_ADMIN);
    ApplicationSettingsPatchDTO patchDTO = new ApplicationSettingsPatchDTO();
    patchDTO.setLegalContentChangesBySingleTenantAdminsAllowed(new ApplicationSettingsDTOMultitenancyWithSingleDomainEnabled().value(false).readOnly(true));
    String jsonRequest = JsonConverter.convertToJson(patchDTO);
    mockMvc.perform(patch("/settingsadmin")
                    .with(authentication(authentication))
                    .header("csrfHeader", "csrfToken")
                    .cookie(new Cookie("csrfCookie", "csrfToken"))
                    .contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                    .content(jsonRequest)
                    .contentType(javax.ws.rs.core.MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
  }

  private Authentication givenMockAuthentication(final UserRole authority) {
    final var securityContext = mock(
            RefreshableKeycloakSecurityContext.class);
    when(securityContext.getTokenString()).thenReturn("tokenString");
    final var token = mock(AccessToken.class, Mockito.RETURNS_DEEP_STUBS);
    when(securityContext.getToken()).thenReturn(token);
    givenOtherClaimsAreDefinedForToken(token);
    final KeycloakAccount mockAccount = new SimpleKeycloakAccount(() -> "user", Sets.newHashSet(),
            securityContext);
    final Authentication authentication = new KeycloakAuthenticationToken(mockAccount, true,
            Lists.newArrayList((GrantedAuthority) () -> authority.getValue()));
    return authentication;
  }

  private void givenOtherClaimsAreDefinedForToken(final AccessToken token) {
    final Map<String, Object> claimMap = Maps.newHashMap("username", "test");
    claimMap.put("userId", "some userid");
    when(token.getOtherClaims()).thenReturn(claimMap);
  }
}
