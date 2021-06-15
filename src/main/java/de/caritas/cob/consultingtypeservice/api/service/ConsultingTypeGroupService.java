package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeGroupRepository;
import de.caritas.cob.consultingtypeservice.api.mapper.group.ConsultingTypeGroupMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.group.ConsultingTypeGroupResponseMapper;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeGroupResponseDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for consulting type group operations.
 */
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

    return consultingTypeGroupRepository
        .getConsultingTypesGroupMap()
        .values()
        .stream()
        .map(consultingTypes -> ConsultingTypeGroupMapper
            .mapConsultingType(consultingTypes,
                ConsultingTypeGroupResponseMapper::mapConsultingType))
        .collect(Collectors.toList());
  }

}
