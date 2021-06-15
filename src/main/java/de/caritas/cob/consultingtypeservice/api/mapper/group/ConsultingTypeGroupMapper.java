package de.caritas.cob.consultingtypeservice.api.mapper.group;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeCoreMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeCoreDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsultingTypeGroupMapper {

  protected ConsultingTypeGroupMapper() { }

  public static <R> R mapConsultingType(List<ConsultingType> consultingTypeList,
      Function<List<ConsultingType>, R> mapper) {
    return mapper.apply(consultingTypeList);
  }

  protected static String determineGroupTitle(List<ConsultingType> consultingTypeList) {
    return consultingTypeList
        .stream()
        .findFirst()
        .orElseThrow(() -> new UnexpectedErrorException(
            "Could not determine group title for consulting type group list")).getGroup();
  }

  protected static List<ConsultingTypeCoreDTO> mapConsultingTypeCoreDtoList(
      List<ConsultingType> consultingTypeList) {
    return consultingTypeList
        .stream()
        .map(listOfConsultingTypes -> ConsultingTypeMapper
            .mapConsultingType(listOfConsultingTypes, ConsultingTypeCoreMapper::mapConsultingType))
        .collect(Collectors.toList());
  }

}
