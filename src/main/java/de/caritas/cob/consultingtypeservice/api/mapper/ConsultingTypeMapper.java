package de.caritas.cob.consultingtypeservice.api.mapper;

import static java.util.Objects.nonNull;

import de.caritas.cob.consultingtypeservice.api.consultingtypes.roles.Consultant;
import de.caritas.cob.consultingtypeservice.api.model.AnonymousScreenDTO;
import de.caritas.cob.consultingtypeservice.api.model.GroupChatDTO;
import de.caritas.cob.consultingtypeservice.api.model.MonitoringDTO;
import de.caritas.cob.consultingtypeservice.api.model.NewMessageDTO;
import de.caritas.cob.consultingtypeservice.api.model.NotificationsDTO;
import de.caritas.cob.consultingtypeservice.api.model.RegistrationDTO;
import de.caritas.cob.consultingtypeservice.api.model.RegistrationMandatoryFieldsDTO;
import de.caritas.cob.consultingtypeservice.api.model.RegistrationNotesDTO;
import de.caritas.cob.consultingtypeservice.api.model.RolesDTO;
import de.caritas.cob.consultingtypeservice.api.model.SessionDataInitializingDTO;
import de.caritas.cob.consultingtypeservice.api.model.TeamSessionsDTO;
import de.caritas.cob.consultingtypeservice.api.model.TitlesDTO;
import de.caritas.cob.consultingtypeservice.api.model.UrlsDTO;
import de.caritas.cob.consultingtypeservice.api.model.WelcomeMessageDTO;
import de.caritas.cob.consultingtypeservice.api.model.WelcomeScreenDTO;
import de.caritas.cob.consultingtypeservice.api.model.WhiteSpotDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.Anonymous;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import de.caritas.cob.consultingtypeservice.schemas.model.GroupChat;
import de.caritas.cob.consultingtypeservice.schemas.model.Monitoring;
import de.caritas.cob.consultingtypeservice.schemas.model.NewMessage;
import de.caritas.cob.consultingtypeservice.schemas.model.Notifications;
import de.caritas.cob.consultingtypeservice.schemas.model.Registration;
import de.caritas.cob.consultingtypeservice.schemas.model.Roles;
import de.caritas.cob.consultingtypeservice.schemas.model.SessionDataInitializing;
import de.caritas.cob.consultingtypeservice.schemas.model.TeamSessions;
import de.caritas.cob.consultingtypeservice.schemas.model.Titles;
import de.caritas.cob.consultingtypeservice.schemas.model.Urls;
import de.caritas.cob.consultingtypeservice.schemas.model.WelcomeMessage;
import de.caritas.cob.consultingtypeservice.schemas.model.WelcomeScreen;
import de.caritas.cob.consultingtypeservice.schemas.model.WhiteSpot;
import java.util.LinkedHashMap;
import java.util.function.Function;

public class ConsultingTypeMapper {

  protected ConsultingTypeMapper() {
  }

  protected static TitlesDTO mapTitles(Titles titles) {
    return new TitlesDTO()
        ._default(titles.getDefault())
        ._long(titles.getLong())
        ._short(titles.getShort())
        .registrationDropdown(titles.getRegistrationDropdown())
        .welcome(titles.getWelcome());
  }

  protected static UrlsDTO mapUrls(Urls urls) {
    return new UrlsDTO()
        .registrationPostcodeFallbackUrl(urls.getRegistrationPostcodeFallbackUrl())
        .requiredAidMissingRedirectUrl(urls.getRequiredAidMissingRedirectUrl());


  }

  protected static RegistrationDTO mapRegistration(Registration registration) {
    return new RegistrationDTO()
        .minPostcodeSize(registration.getMinPostcodeSize())
        .autoSelectAgency(registration.getAutoSelectAgency())
        .autoSelectPostcode(registration.getAutoSelectPostcode())
        .notes(mapNotes(registration))
        .mandatoryFields(mapMandatoryFields(registration));
  }

  protected static RegistrationMandatoryFieldsDTO mapMandatoryFields(Registration registration) {
    return new RegistrationMandatoryFieldsDTO()
        .age(registration.getMandatoryFields().getAge())
        .state(registration.getMandatoryFields().getState());
  }

  protected static RegistrationNotesDTO mapNotes(Registration registration) {
    return new RegistrationNotesDTO()
        .agencySelection(registration.getNotes().getAgencySelection())
        .password(registration.getNotes().getPassword());
  }

  protected static WhiteSpotDTO mapWhiteSpot(WhiteSpot whiteSpot) {
    return nonNull(whiteSpot)
        ? new WhiteSpotDTO()
        .whiteSpotAgencyAssigned(whiteSpot.getWhiteSpotAgencyAssigned())
        .whiteSpotAgencyId(getWhiteSpotAgencyIdNullSafe(whiteSpot))
        : new WhiteSpotDTO()
            .whiteSpotAgencyAssigned(false);
  }

  private static Integer getWhiteSpotAgencyIdNullSafe(WhiteSpot whiteSpot) {
    return nonNull(whiteSpot.getWhiteSpotAgencyId()) ? whiteSpot.getWhiteSpotAgencyId()
        : null;
  }

  protected static GroupChatDTO mapGroupChat(GroupChat groupChat) {
    return nonNull(groupChat)
        ? new GroupChatDTO()
        .isGroupChat(groupChat.getIsGroupChat())
        .groupChatRules(groupChat.getGroupChatRules())
        : new GroupChatDTO()
            .isGroupChat(false);
  }

  protected static WelcomeMessageDTO mapWelcomeMessage(WelcomeMessage welcomeMessage) {
    return nonNull(welcomeMessage)
        ? new WelcomeMessageDTO()
        .sendWelcomeMessage(welcomeMessage.getSendWelcomeMessage())
        .welcomeMessageText(welcomeMessage.getWelcomeMessageText())
        : new WelcomeMessageDTO()
            .sendWelcomeMessage(false);
  }

  protected static SessionDataInitializingDTO mapSessionDataInitializing(
      SessionDataInitializing sessionDataInitializing) {
    var sessionDataInitializingTmp =
        getSessionDataInitializingOrIfNullWithDefaults(sessionDataInitializing);
    return new SessionDataInitializingDTO()
        .age(sessionDataInitializingTmp.getAge())
        .addictiveDrugs(sessionDataInitializingTmp.getAddictiveDrugs())
        .state(sessionDataInitializingTmp.getState())
        .gender(sessionDataInitializingTmp.getGender())
        .relation(sessionDataInitializingTmp.getRelation());
  }

  private static SessionDataInitializing getSessionDataInitializingOrIfNullWithDefaults(
      SessionDataInitializing sessionDataInitializing) {
    return nonNull(sessionDataInitializing)
        ? sessionDataInitializing
        : new SessionDataInitializing();
  }

  protected static MonitoringDTO mapMonitoring(Monitoring monitoring) {
    return nonNull(monitoring)
        ? new MonitoringDTO()
        .initializeMonitoring(monitoring.getInitializeMonitoring())
        .monitoringTemplateFile(monitoring.getMonitoringTemplateFile())
        : new MonitoringDTO()
            .initializeMonitoring(false);
  }

  protected static RolesDTO mapRoles(Roles roles) {
    return new RolesDTO()
        .consultant(
            Consultant.builder().roleNames(new LinkedHashMap<>(roles.getConsultant())).build());
  }

  protected static NotificationsDTO mapNotifications(Notifications notifications) {
    return nonNull(notifications)
        ? new NotificationsDTO()
        .teamSessions(mapTeamSessions(notifications.getTeamSessions()))
        : createDefaultNotifications();
  }

  private static NotificationsDTO createDefaultNotifications() {
    return new NotificationsDTO()
        .teamSessions(new TeamSessionsDTO()
            .newMessage(new NewMessageDTO().allTeamConsultants(true)));
  }

  protected static TeamSessionsDTO mapTeamSessions(TeamSessions teamSessions) {
    return new TeamSessionsDTO()
        .newMessage(mapNewMessage(teamSessions.getNewMessage()));
  }

  protected static NewMessageDTO mapNewMessage(NewMessage newMessage) {
    return new NewMessageDTO()
        .allTeamConsultants(newMessage.getAllTeamConsultants());
  }

  protected static WelcomeScreenDTO mapWelcomeScreen(WelcomeScreen welcomeScreen) {
    return nonNull(welcomeScreen)
        ? new WelcomeScreenDTO()
        .anonymous(mapAnonymousScreen(welcomeScreen.getAnonymous()))
        : null;
  }

  protected static AnonymousScreenDTO mapAnonymousScreen(Anonymous anonymous) {
    return new AnonymousScreenDTO()
        .itemTitle(anonymous.getItemTitle())
        .itemText(anonymous.getItemText());
  }

  public static <R> R mapConsultingType(ConsultingType consultingType,
      Function<ConsultingType, R> mapper) {
    return mapper.apply(consultingType);
  }

}
