package de.caritas.cob.consultingtypeservice.api.mapper;

import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;

/** Mapping from {@link ConsultingType} to {@link ExtendedConsultingTypeResponseDTO}. */
public class ExtendedConsultingTypeMapper extends ConsultingTypeMapper {

  /**
   * Mapper for {@link ConsultingType} to {@link ExtendedConsultingTypeResponseDTO}.
   *
   * @param consultingType the {@link ConsultingType}
   * @return a instance of an {@link ExtendedConsultingTypeResponseDTO}
   */
  public static ExtendedConsultingTypeResponseDTO mapConsultingType(ConsultingType consultingType) {
    return new ExtendedConsultingTypeResponseDTO()
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
        .whiteSpot(mapWhiteSpot(consultingType.getWhiteSpot()))
        .groupChat(mapGroupChat(consultingType.getGroupChat()))
        .consultantBoundedToConsultingType(consultingType.getConsultantBoundedToConsultingType())
        .welcomeMessage(mapWelcomeMessage(consultingType.getWelcomeMessage()))
        .sendFurtherStepsMessage(consultingType.getSendFurtherStepsMessage())
        .isSubsequentRegistrationAllowed(consultingType.getIsSubsequentRegistrationAllowed())
        .isAnonymousConversationAllowed(consultingType.getIsAnonymousConversationAllowed())
        .isSetEmailAllowed(consultingType.getIsSetEmailAllowed())
        .sessionDataInitializing(
            mapSessionDataInitializing(consultingType.getSessionDataInitializing()))
        .initializeFeedbackChat(consultingType.getInitializeFeedbackChat())
        .isPeerChat(consultingType.getIsPeerChat())
        .languageFormal(consultingType.getLanguageFormal())
        .monitoring(mapMonitoring(consultingType.getMonitoring()))
        .roles(mapRoles(consultingType.getRoles()))
        .notifications(mapNotifications(consultingType.getNotifications()))
        .showAskerProfile(consultingType.getShowAskerProfile())
        .isVideoCallAllowed(consultingType.getIsVideoCallAllowed());
  }
}
