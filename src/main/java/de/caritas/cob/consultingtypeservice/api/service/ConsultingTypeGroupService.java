package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeGroupRepository;
import de.caritas.cob.consultingtypeservice.api.mapper.group.ConsultingTypeGroupMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.group.ConsultingTypeGroupResponseMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeGroupResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

/** Service for consulting type group operations. */
@Service
@RequiredArgsConstructor
public class ConsultingTypeGroupService {

  private final @NonNull ConsultingTypeGroupRepository consultingTypeGroupRepository;

  /**
   * Fetch a list of all consulting type groups with the assigned consulting types.
   *
   * @return a {@link List} of {@link ConsultingTypeGroupResponseDTO} instances
   */
  public List<ConsultingTypeGroupResponseDTO> fetchConsultingTypeGroupList() {

    return consultingTypeGroupRepository.getConsultingTypesGroupMap().entrySet().stream()
        .map(
            mapEntry ->
                ConsultingTypeGroupMapper.mapConsultingType(
                    mapEntryToPair(mapEntry), ConsultingTypeGroupResponseMapper::mapConsultingType))
        .collect(Collectors.toList());
  }

  private ImmutablePair<String, List<ConsultingType>> mapEntryToPair(
      Entry<String, List<ConsultingType>> mapEntry) {
    return new ImmutablePair<>(mapEntry.getKey(), mapEntry.getValue());
  }
}
