package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOFurtherInformation;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOGroupChat;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTORegistration;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOTitles;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOUrls;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOMonitoring;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTONotifications;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOSessionDataInitializing;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOWelcomeMessage;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOWhiteSpot;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.model.RequiredComponentsDTO;
import de.caritas.cob.consultingtypeservice.api.model.RolesDTO;
import de.caritas.cob.consultingtypeservice.api.model.WelcomeScreenDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.Age;
import de.caritas.cob.consultingtypeservice.schemas.model.Anonymous;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import de.caritas.cob.consultingtypeservice.schemas.model.FurtherInformation;
import de.caritas.cob.consultingtypeservice.schemas.model.GroupChat;
import de.caritas.cob.consultingtypeservice.schemas.model.MandatoryFields;
import de.caritas.cob.consultingtypeservice.schemas.model.Monitoring;
import de.caritas.cob.consultingtypeservice.schemas.model.NewMessage;
import de.caritas.cob.consultingtypeservice.schemas.model.Notes;
import de.caritas.cob.consultingtypeservice.schemas.model.Notifications;
import de.caritas.cob.consultingtypeservice.schemas.model.Option;
import de.caritas.cob.consultingtypeservice.schemas.model.Registration;
import de.caritas.cob.consultingtypeservice.schemas.model.RequiredComponents;
import de.caritas.cob.consultingtypeservice.schemas.model.Roles;
import de.caritas.cob.consultingtypeservice.schemas.model.SessionDataInitializing;
import de.caritas.cob.consultingtypeservice.schemas.model.State;
import de.caritas.cob.consultingtypeservice.schemas.model.TeamSessions;
import de.caritas.cob.consultingtypeservice.schemas.model.Titles;
import de.caritas.cob.consultingtypeservice.schemas.model.Urls;
import de.caritas.cob.consultingtypeservice.schemas.model.WelcomeMessage;
import de.caritas.cob.consultingtypeservice.schemas.model.WelcomeScreen;
import de.caritas.cob.consultingtypeservice.schemas.model.WhiteSpot;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ConsultingTypeConverter {

  public List<ConsultingType> convertList(List<ConsultingTypeEntity> consultingTypeEntities) {
    return consultingTypeEntities.stream().map(this::copyToConsultingType).collect(
        Collectors.toList());
  }

  private ConsultingType copyToConsultingType(ConsultingTypeEntity entity) {
    ConsultingType target = new ConsultingType();
    BeanUtils.copyProperties(entity, target);
    return target;
  }

  public ConsultingType convert(final ConsultingTypeDTO consultingTypeDTO) {
    return new ConsultingType()
        .withTenantId(consultingTypeDTO.getTenantId())
        .withDescription(consultingTypeDTO.getDescription())
        .withGroups(consultingTypeDTO.getGroups())
        .withFurtherInformation(convert(consultingTypeDTO.getFurtherInformation()))
        .withSlug(consultingTypeDTO.getSlug())
        .withExcludeNonMainConsultantsFromTeamSessions(
            consultingTypeDTO.getExcludeNonMainConsultantsFromTeamSessions())
        .withLockedAgencies(consultingTypeDTO.getLockedAgencies())
        .withWhiteSpot(convert(consultingTypeDTO.getWhiteSpot()))
        .withGroupChat(convert(consultingTypeDTO.getGroupChat()))
        .withConsultantBoundedToConsultingType(
            consultingTypeDTO.getConsultantBoundedToConsultingType())
        .withWelcomeMessage(convert(consultingTypeDTO.getWelcomeMessage()))
        .withSendFurtherStepsMessage(consultingTypeDTO.getSendFurtherStepsMessage())
        .withSendSaveSessionDataMessage(consultingTypeDTO.getSendSaveSessionDataMessage())
        .withIsSetEmailAllowed(consultingTypeDTO.getIsSetEmailAllowed())
        .withSessionDataInitializing(convert(consultingTypeDTO.getSessionDataInitializing()))
        .withMonitoring(convert(consultingTypeDTO.getMonitoring()))
        .withInitializeFeedbackChat(consultingTypeDTO.getInitializeFeedbackChat())
        .withIsPeerChat(consultingTypeDTO.getIsPeerChat())
        .withLanguageFormal(consultingTypeDTO.getLanguageFormal())
        .withRoles(convert(consultingTypeDTO.getRoles()))
        .withNotifications(convert(consultingTypeDTO.getNotifications()))
        .withRegistration(convert(consultingTypeDTO.getRegistration()))
        .withTitles(convert(consultingTypeDTO.getTitles()))
        .withUrls(convert(consultingTypeDTO.getUrls()))
        .withShowAskerProfile(consultingTypeDTO.getShowAskerProfile())
        .withIsVideoCallAllowed(consultingTypeDTO.getIsVideoCallAllowed())
        .withIsSubsequentRegistrationAllowed(consultingTypeDTO.getIsSubsequentRegistrationAllowed())
        .withIsAnonymousConversationAllowed(consultingTypeDTO.getIsAnonymousConversationAllowed())
        .withVoluntaryComponents(consultingTypeDTO.getVoluntaryComponents())
        .withRequiredComponents(convert(consultingTypeDTO.getRequiredComponents()))
        .withWelcomeScreen(convert(consultingTypeDTO.getWelcomeScreen()));

  }

  private WelcomeScreen convert(WelcomeScreenDTO welcomeScreen) {
    return new WelcomeScreen()
        .withAnonymous(new Anonymous(welcomeScreen.getAnonymous().getTitle(),
            welcomeScreen.getAnonymous().getText()));
  }


  private Urls convert(BasicConsultingTypeResponseDTOUrls urls) {
    return new Urls(urls.getRequiredAidMissingRedirectUrl(),
        urls.getRegistrationPostcodeFallbackUrl());
  }

  private Titles convert(BasicConsultingTypeResponseDTOTitles titles) {
    return new Titles()
        .withDefault(titles.getDefault())
        .withLong(titles.getLong())
        .withShort(titles.getShort())
        .withRegistrationDropdown(titles.getRegistrationDropdown())
        .withWelcome(titles.getWelcome());
  }

  private Registration convert(BasicConsultingTypeResponseDTORegistration registration) {
    return new Registration()
        .withAutoSelectAgency(registration.getAutoSelectAgency())
        .withAutoSelectPostcode(registration.getAutoSelectPostcode())
        .withNotes(new Notes(registration.getNotes().getAgencySelection(),
            registration.getNotes().getPassword()))
        .withMandatoryFields(new MandatoryFields(registration.getMandatoryFields().getAge(),
            registration.getMandatoryFields().getState()));
  }

  private Notifications convert(ConsultingTypeDTONotifications notifications) {
    return new Notifications()
        .withTeamSessions(
            new TeamSessions().withNewMessage(
                new NewMessage(
                    notifications.getTeamSessions().getNewMessage().getAllTeamConsultants())));
  }

  private Roles convert(RolesDTO roles) {
    return new Roles(roles.getConsultant().getRoleNames());
  }

  private Monitoring convert(ConsultingTypeDTOMonitoring monitoring) {
    return new Monitoring(monitoring.getInitializeMonitoring(),
        monitoring.getMonitoringTemplateFile());
  }

  private SessionDataInitializing convert(
      ConsultingTypeDTOSessionDataInitializing sessionDataInitializing) {
    return new SessionDataInitializing()
        .withAddictiveDrugs(sessionDataInitializing.getAddictiveDrugs())
        .withAge(sessionDataInitializing.getAge())
        .withGender(sessionDataInitializing.getGender())
        .withState(sessionDataInitializing.getState())
        .withRelation(sessionDataInitializing.getRelation());
  }

  private WelcomeMessage convert(ConsultingTypeDTOWelcomeMessage welcomeMessage) {
    return new WelcomeMessage(welcomeMessage.getSendWelcomeMessage(),
        welcomeMessage.getWelcomeMessageText());

  }

  private GroupChat convert(BasicConsultingTypeResponseDTOGroupChat groupChat) {
    return new GroupChat(groupChat.getIsGroupChat(), groupChat.getGroupChatRules());
  }

  private WhiteSpot convert(ConsultingTypeDTOWhiteSpot whiteSpot) {
    return new WhiteSpot(whiteSpot.getWhiteSpotAgencyAssigned(), whiteSpot.getWhiteSpotAgencyId());
  }

  private RequiredComponents convert(RequiredComponentsDTO requiredComponents) {
    List<Option> options = new ArrayList<>();
    requiredComponents.getAge().getOptions()
        .forEach(optionDTO -> options.add(new Option(optionDTO.getValue(), optionDTO.getLabel())));

    return new RequiredComponents()
        .withAge(new Age()
            .withIsEnabled(requiredComponents.getAge().getIsEnabled())
            .withOptions(options))
        .withState(new State(requiredComponents.getState().getIsEnabled()));
  }

  private FurtherInformation convert(
      BasicConsultingTypeResponseDTOFurtherInformation furtherInformation) {
    return new FurtherInformation(furtherInformation.getLabel(), furtherInformation.getUrl());
  }
}
