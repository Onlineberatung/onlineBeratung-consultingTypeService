package de.caritas.cob.consultingtypeservice.api.mapper;

import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;

/**
 * Mapping from {@link ConsultingType} to {@link FullConsultingTypeResponseDTO}.
 */
public class FullConsultingTypeListMapper {

  private FullConsultingTypeListMapper() {}

  /**
   * Mapper for {@link ConsultingType} to {@link FullConsultingTypeResponseDTO}
   *
   * @param consultingType the {@link ConsultingType}
   * @return a instance of an {@link FullConsultingTypeResponseDTO}
   */
  public static FullConsultingTypeResponseDTO mapConsultingType(ConsultingType consultingType) {
    return new FullConsultingTypeResponseDTO()
        .id(consultingType.getId());
  }

}
