package de.caritas.cob.consultingtypeservice.api.mapper;

import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;

/** Mapping from {@link ConsultingType} to {@link FullConsultingTypeResponseDTO}. */
public class FullConsultingTypeMapper extends ConsultingTypeMapper {

  /**
   * Mapper for {@link ConsultingType} to {@link FullConsultingTypeResponseDTO}.
   *
   * @param consultingType the {@link ConsultingType}
   * @return a instance of an {@link FullConsultingTypeResponseDTO}
   */
  public static FullConsultingTypeResponseDTO mapConsultingType(ConsultingType consultingType) {
    return new FullConsultingTypeResponseDTO()
        .id(consultingType.getId())
        .tenantId(consultingType.getTenantId() != null ? consultingType.getTenantId() : null)
        .description(consultingType.getDescription())
        .furtherInformation(mapFurtherInformation(consultingType.getFurtherInformation()))
        .slug(consultingType.getSlug())
        .titles(mapTitles(consultingType.getTitles()))
        .urls(mapUrls(consultingType.getUrls()))
        .registration(mapRegistration(consultingType.getRegistration()))
        .excludeNonMainConsultantsFromTeamSessions(
            consultingType.getExcludeNonMainConsultantsFromTeamSessions())
        .lockedAgencies(consultingType.getLockedAgencies())
        .whiteSpot(mapWhiteSpot(consultingType.getWhiteSpot()))
        .groupChat(mapGroupChat(consultingType.getGroupChat()))
        .consultantBoundedToConsultingType(consultingType.getConsultantBoundedToConsultingType())
        .welcomeMessage(mapWelcomeMessage(consultingType.getWelcomeMessage()))
        .sendFurtherStepsMessage(consultingType.getSendFurtherStepsMessage())
        .sendSaveSessionDataMessage(consultingType.getSendSaveSessionDataMessage())
        .isSubsequentRegistrationAllowed(consultingType.getIsSubsequentRegistrationAllowed())
        .isAnonymousConversationAllowed(consultingType.getIsAnonymousConversationAllowed())
        .isSetEmailAllowed(consultingType.getIsSetEmailAllowed())
        .sessionDataInitializing(
            mapSessionDataInitializing(consultingType.getSessionDataInitializing()))
        .initializeFeedbackChat(consultingType.getInitializeFeedbackChat())
        .isPeerChat(consultingType.getIsPeerChat())
        .languageFormal(consultingType.getLanguageFormal())
        .showAskerProfile(consultingType.getShowAskerProfile())
        .monitoring(mapMonitoring(consultingType.getMonitoring()))
        .roles(mapRoles(consultingType.getRoles()))
        .notifications(mapNotifications(consultingType.getNotifications()))
        .voluntaryComponents(consultingType.getVoluntaryComponents())
        .requiredComponents(consultingType.getRequiredComponents())
        .welcomeScreen(mapWelcomeScreen(consultingType.getWelcomeScreen()))
        .isVideoCallAllowed(consultingType.getIsVideoCallAllowed());
  }
}
