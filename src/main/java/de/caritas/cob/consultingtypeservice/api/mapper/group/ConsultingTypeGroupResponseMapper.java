package de.caritas.cob.consultingtypeservice.api.mapper.group;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeGroupResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;

/**
 * Mapping from a {@link List} of {@link ConsultingType} to {@link ConsultingTypeGroupResponseMapper}.
 */
public class ConsultingTypeGroupResponseMapper extends ConsultingTypeGroupMapper {

  /**
   * Mapper for a {@link List} of {@link ConsultingType} to {@link ConsultingTypeGroupResponseDTO}.
   *
   * @return a instance of an {@link ConsultingTypeGroupResponseDTO}
   */
  public static ConsultingTypeGroupResponseDTO mapConsultingType(
      List<ConsultingType> consultingTypeList) {

    return new ConsultingTypeGroupResponseDTO()
        .title(determineGroupTitle(consultingTypeList))
        .consultingTypes(mapConsultingTypeCoreDtoList(consultingTypeList));
  }

}
