package de.caritas.cob.consultingtypeservice.api.mapper.group;

import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeCoreMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeCoreDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class ConsultingTypeGroupMapper {

  protected ConsultingTypeGroupMapper() {}

  public static <R> R mapConsultingType(
      ImmutablePair<String, List<ConsultingType>> groupConsultingTypeListPair,
      Function<ImmutablePair<String, List<ConsultingType>>, R> mapper) {
    return mapper.apply(groupConsultingTypeListPair);
  }

  protected static List<ConsultingTypeCoreDTO> mapConsultingTypeCoreDtoList(
      List<ConsultingType> consultingTypeList) {
    return consultingTypeList.stream()
        .map(
            listOfConsultingTypes ->
                ConsultingTypeMapper.mapConsultingType(
                    listOfConsultingTypes, ConsultingTypeCoreMapper::mapConsultingType))
        .collect(Collectors.toList());
  }
}
