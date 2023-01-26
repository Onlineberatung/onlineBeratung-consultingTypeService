package de.caritas.cob.consultingtypeservice.api.mapper;

import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;

/** Mapping from {@link ConsultingType} to {@link BasicConsultingTypeResponseDTO}. */
public class BasicConsultingTypeMapper extends ConsultingTypeMapper {

  /**
   * Mapper for {@link ConsultingType} to {@link BasicConsultingTypeResponseDTO}.
   *
   * @param consultingType the {@link ConsultingType}
   * @return a instance of an {@link BasicConsultingTypeResponseDTO}
   */
  public static BasicConsultingTypeResponseDTO mapConsultingType(ConsultingType consultingType) {
    return new BasicConsultingTypeResponseDTO()
        .id(consultingType.getId())
        .tenantId(consultingType.getTenantId() != null ? consultingType.getTenantId() : null)
        .description(consultingType.getDescription())
        .furtherInformation(mapFurtherInformation(consultingType.getFurtherInformation()))
        .isSubsequentRegistrationAllowed(consultingType.getIsSubsequentRegistrationAllowed())
        .isAnonymousConversationAllowed(consultingType.getIsAnonymousConversationAllowed())
        .isSetEmailAllowed(consultingType.getIsSetEmailAllowed())
        .titles(mapTitles(consultingType.getTitles()))
        .urls(mapUrls(consultingType.getUrls()))
        .registration(mapRegistration(consultingType.getRegistration()))
        .groupChat(mapGroupChat(consultingType.getGroupChat()))
        .showAskerProfile(consultingType.getShowAskerProfile())
        .isVideoCallAllowed(consultingType.getIsVideoCallAllowed())
        .languageFormal(consultingType.getLanguageFormal());
  }
}
