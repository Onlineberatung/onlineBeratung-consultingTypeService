package de.caritas.cob.consultingtypeservice.api.mapper;

import de.caritas.cob.consultingtypeservice.api.model.CompactConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeTitlesDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import de.caritas.cob.consultingtypeservice.schemas.model.Titles;

/**
 * Mapping from {@link ConsultingType} to {@link CompactConsultingTypeResponseDTO}.
 */
public class CompactConsultingTypeListMapper {

  private CompactConsultingTypeListMapper() {}

  /**
   * Mapper for {@link ConsultingType} to {@link CompactConsultingTypeResponseDTO}
   *
   * @param consultingType the {@link ConsultingType}
   * @return a instance of an {@link CompactConsultingTypeResponseDTO}
   */
  public static CompactConsultingTypeResponseDTO mapConsultingType(ConsultingType consultingType) {
    return new CompactConsultingTypeResponseDTO()
        .id(consultingType.getId())
        .isSubsequentAdditionAllowed(consultingType.getIsSubsequentRegistrationAllowed())
        .titles(mapTitles(consultingType.getTitles()));
  }

  private static ConsultingTypeTitlesDTO mapTitles(Titles titles) {
    return new ConsultingTypeTitlesDTO()
        ._default(titles.getDefault())
        ._long(titles.getLong())
        ._short(titles.getShort())
        .registrationDropdown(titles.getRegistrationDropdown())
        .welcome(titles.getWelcome());
  }

}
