package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepository;
import de.caritas.cob.consultingtypeservice.api.mapper.BasicConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ExtendedConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.FullConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import java.util.List;
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
   * Fetch a list of all consulting types with basic properties.
   *
   * @return a {@link List} of {@link BasicConsultingTypeResponseDTO} instances
   */
  public List<BasicConsultingTypeResponseDTO> fetchBasicConsultingTypesList() {

    return consultingTypeRepository.getListOfConsultingTypes()
        .stream()
        .map(c -> ConsultingTypeMapper.mapConsultingType(c, BasicConsultingTypeMapper::mapConsultingType))
        .collect(Collectors.toList());
  }

  /**
   * Fetch a consulting type with all properties by id.
   *
   * @param consultingTypeId the consulting type id
   * @return a {@link FullConsultingTypeResponseDTO} instance
   */
  public FullConsultingTypeResponseDTO fetchFullConsultingTypeSettingsById(
      Integer consultingTypeId) {
    return ConsultingTypeMapper.mapConsultingType(consultingTypeRepository.getConsultingTypeById(consultingTypeId),
        FullConsultingTypeMapper::mapConsultingType);
  }

  /**
   * Fetch a consulting type with all properties by slug.
   *
   * @param slug the consulting type slug
   * @return a {@link FullConsultingTypeResponseDTO} instance
   */
  public FullConsultingTypeResponseDTO fetchFullConsultingTypeSettingsBySlug(String slug) {
    return ConsultingTypeMapper.mapConsultingType(consultingTypeRepository.getConsultingTypeBySlug(slug),
        FullConsultingTypeMapper::mapConsultingType);
  }

  /**
   * Fetch a consulting type with extended set of properties by id.
   *
   * @param consultingTypeId the consulting type id
   * @return a {@link FullConsultingTypeResponseDTO} instance
   */
  public ExtendedConsultingTypeResponseDTO fetchExtendedConsultingTypeSettingsById(
      Integer consultingTypeId) {
    return ConsultingTypeMapper.mapConsultingType(consultingTypeRepository.getConsultingTypeById(consultingTypeId),
        ExtendedConsultingTypeMapper::mapConsultingType);
  }

  /**
   * Fetch a consulting type with basic set of properties by id.
   *
   * @param consultingTypeId the consulting type id
   * @return a {@link BasicConsultingTypeResponseDTO} instance
   */
  public BasicConsultingTypeResponseDTO fetchBasicConsultingTypeSettingsById(Integer consultingTypeId) {
    return ConsultingTypeMapper.mapConsultingType(consultingTypeRepository.getConsultingTypeById(consultingTypeId),
        BasicConsultingTypeMapper::mapConsultingType);
  }
}
