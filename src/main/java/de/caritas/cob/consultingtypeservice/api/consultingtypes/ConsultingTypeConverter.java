package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOFurtherInformation;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOGroupChat;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTORegistration;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOTitles;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTOUrls;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTONotifications;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOSessionDataInitializing;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOWelcomeMessage;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTOWhiteSpot;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypePatchDTO;
import de.caritas.cob.consultingtypeservice.api.model.RequiredComponentsDTO;
import de.caritas.cob.consultingtypeservice.api.model.RolesDTO;
import de.caritas.cob.consultingtypeservice.api.model.WelcomeScreenDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.Age;
import de.caritas.cob.consultingtypeservice.schemas.model.Anonymous;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import de.caritas.cob.consultingtypeservice.schemas.model.FurtherInformation;
import de.caritas.cob.consultingtypeservice.schemas.model.GroupChat;
import de.caritas.cob.consultingtypeservice.schemas.model.MandatoryFields;
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
    return consultingTypeEntities.stream()
        .map(this::copyToConsultingType)
        .collect(Collectors.toList());
  }

  private ConsultingType copyToConsultingType(ConsultingTypeEntity entity) {
    ConsultingType target = new ConsultingType();
    BeanUtils.copyProperties(entity, target);
    return target;
  }

  public ConsultingType convert(final ConsultingTypeDTO consultingTypeDTO) {

    return this.convert(new ConsultingType(), consultingTypeDTO);
  }

  public ConsultingType convert(
      ConsultingType consultingTypeEntity, final ConsultingTypeDTO consultingTypeDTO) {
    return consultingTypeEntity
        .withTenantId(consultingTypeDTO.getTenantId())
        .withDescription(consultingTypeDTO.getDescription())
        .withGroups(consultingTypeDTO.getGroups())
        .withFurtherInformation(convert(consultingTypeDTO.getFurtherInformation()))
        .withSlug(consultingTypeDTO.getSlug())
        .withExcludeNonMainConsultantsFromTeamSessions(
            consultingTypeDTO.getExcludeNonMainConsultantsFromTeamSessions())
        .withWhiteSpot(convert(consultingTypeDTO.getWhiteSpot()))
        .withGroupChat(convert(consultingTypeDTO.getGroupChat()))
        .withConsultantBoundedToConsultingType(
            consultingTypeDTO.getConsultantBoundedToConsultingType())
        .withWelcomeMessage(convert(consultingTypeDTO.getWelcomeMessage()))
        .withSendFurtherStepsMessage(consultingTypeDTO.getSendFurtherStepsMessage())
        .withSessionDataInitializing(convert(consultingTypeDTO.getSessionDataInitializing()))
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
        .withRequiredComponents(convert(consultingTypeDTO.getRequiredComponents()))
        .withWelcomeScreen(convert(consultingTypeDTO.getWelcomeScreen()));
  }

  public ConsultingType convert(
      ConsultingType consultingTypeEntity, final ConsultingTypePatchDTO consultingTypeDTO) {
    return consultingTypeEntity
        .withWelcomeMessage(convert(consultingTypeDTO.getWelcomeMessage()))
        .withSendFurtherStepsMessage(consultingTypeDTO.getSendFurtherStepsMessage())
        .withLanguageFormal(consultingTypeDTO.getLanguageFormal())
        .withNotifications(convert(consultingTypeDTO.getNotifications()))
        .withIsVideoCallAllowed(consultingTypeDTO.getIsVideoCallAllowed());
  }

  private WelcomeScreen convert(WelcomeScreenDTO welcomeScreen) {
    if (welcomeScreen == null) {
      return null;
    }
    return new WelcomeScreen()
        .withAnonymous(
            new Anonymous(
                welcomeScreen.getAnonymous().getTitle(), welcomeScreen.getAnonymous().getText()));
  }

  private Urls convert(BasicConsultingTypeResponseDTOUrls urls) {
    if (urls == null) {
      return null;
    }
    return new Urls(
        urls.getRequiredAidMissingRedirectUrl(), urls.getRegistrationPostcodeFallbackUrl());
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
    if (registration == null) {
      return null;
    }
    return new Registration()
        .withAutoSelectAgency(registration.getAutoSelectAgency())
        .withAutoSelectPostcode(registration.getAutoSelectPostcode())
        .withNotes(
            new Notes(
                registration.getNotes().getAgencySelection(),
                registration.getNotes().getPassword()))
        .withMandatoryFields(
            new MandatoryFields(
                registration.getMandatoryFields().getAge(),
                registration.getMandatoryFields().getState()));
  }

  private Notifications convert(ConsultingTypeDTONotifications notifications) {
    if (notifications == null) {
      return null;
    }
    return new Notifications()
        .withTeamSessions(
            new TeamSessions()
                .withNewMessage(
                    new NewMessage(
                        notifications.getTeamSessions().getNewMessage().getAllTeamConsultants())));
  }

  private Roles convert(RolesDTO roles) {
    if (roles == null) {
      return null;
    }
    return new Roles(roles.getConsultant().getRoleNames());
  }

  private SessionDataInitializing convert(
      ConsultingTypeDTOSessionDataInitializing sessionDataInitializing) {
    if (sessionDataInitializing == null) {
      return null;
    }
    return new SessionDataInitializing()
        .withAddictiveDrugs(sessionDataInitializing.getAddictiveDrugs())
        .withAge(sessionDataInitializing.getAge())
        .withGender(sessionDataInitializing.getGender())
        .withState(sessionDataInitializing.getState())
        .withRelation(sessionDataInitializing.getRelation());
  }

  private WelcomeMessage convert(ConsultingTypeDTOWelcomeMessage welcomeMessage) {
    if (welcomeMessage == null) {
      return null;
    }
    return new WelcomeMessage(
        welcomeMessage.getSendWelcomeMessage(), welcomeMessage.getWelcomeMessageText());
  }

  private GroupChat convert(BasicConsultingTypeResponseDTOGroupChat groupChat) {
    if (groupChat == null) {
      return null;
    }
    return new GroupChat(groupChat.getIsGroupChat(), groupChat.getGroupChatRules());
  }

  private WhiteSpot convert(ConsultingTypeDTOWhiteSpot whiteSpot) {
    if (whiteSpot == null) {
      return null;
    }
    return new WhiteSpot(whiteSpot.getWhiteSpotAgencyAssigned(), whiteSpot.getWhiteSpotAgencyId());
  }

  private RequiredComponents convert(RequiredComponentsDTO requiredComponents) {
    if (requiredComponents == null) {
      return null;
    }
    List<Option> options = new ArrayList<>();
    requiredComponents
        .getAge()
        .getOptions()
        .forEach(optionDTO -> options.add(new Option(optionDTO.getValue(), optionDTO.getLabel())));

    return new RequiredComponents()
        .withAge(
            new Age()
                .withIsEnabled(requiredComponents.getAge().getIsEnabled())
                .withOptions(options))
        .withState(new State(requiredComponents.getState().getIsEnabled()));
  }

  private FurtherInformation convert(
      BasicConsultingTypeResponseDTOFurtherInformation furtherInformation) {
    if (furtherInformation == null) {
      return null;
    }
    return new FurtherInformation(furtherInformation.getLabel(), furtherInformation.getUrl());
  }
}
