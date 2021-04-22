package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepository;
import de.caritas.cob.consultingtypeservice.api.mapper.CompactConsultingTypeListMapper;
import de.caritas.cob.consultingtypeservice.api.model.CompactConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for consulting type operations.
 */
@Service
@RequiredArgsConstructor
public class ConsultingTypeService {

  private final @NonNull ConsultingTypeRepository consultingTypeRepository;

  /**
   * Get compact list of all consulting types with only a few attributes.
   *
   * @return a {@link List} of {@link CompactConsultingTypeResponseDTO} instances
   */
  public List<CompactConsultingTypeResponseDTO> getCompactConsultingTypesList() {

    return consultingTypeRepository.getListOfConsultingTypes()
        .stream()
        .map(c -> mapConsultingType(c, CompactConsultingTypeListMapper::mapConsultingType))
        .collect(Collectors.toList());
  }

  private <R> R mapConsultingType(ConsultingType consultingType,
      Function<ConsultingType, R> mapper) {
    return mapper.apply(consultingType);
  }
}
