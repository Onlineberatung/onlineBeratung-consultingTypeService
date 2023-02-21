package de.caritas.cob.consultingtypeservice.api.service;

import de.caritas.cob.consultingtypeservice.api.auth.AuthenticatedUser;
import de.caritas.cob.consultingtypeservice.api.auth.Authority.AuthorityValue;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeConverter;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepositoryService;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.InternalServerErrorException;
import de.caritas.cob.consultingtypeservice.api.mapper.BasicConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.ExtendedConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.mapper.FullConsultingTypeMapper;
import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypePatchDTO;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/** Service for consulting type operations. */
@Service
@RequiredArgsConstructor
public class ConsultingTypeService {

  private final @NonNull ConsultingTypeRepositoryService consultingTypeRepositoryService;
  private final @NonNull ConsultingTypeConverter consultingTypeConverter;

  private final @NonNull AuthenticatedUser authenticatedUser;

  @Value("${feature.multitenancy.with.single.domain.enabled}")
  private boolean multitenancyWithSingleDomainEnabled;

  /**
   * Fetch a list of all consulting types with basic properties.
   *
   * @return a {@link List} of {@link BasicConsultingTypeResponseDTO} instances
   */
  public List<BasicConsultingTypeResponseDTO> fetchBasicConsultingTypesList() {

    return consultingTypeRepositoryService.getListOfConsultingTypes().stream()
        .map(
            c ->
                ConsultingTypeMapper.mapConsultingType(
                    c, BasicConsultingTypeMapper::mapConsultingType))
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
    return ConsultingTypeMapper.mapConsultingType(
        consultingTypeRepositoryService.getConsultingTypeById(consultingTypeId),
        FullConsultingTypeMapper::mapConsultingType);
  }

  /**
   * Fetch a consulting type with all properties by slug.
   *
   * @param slug the consulting type slug
   * @return a {@link FullConsultingTypeResponseDTO} instance
   */
  public FullConsultingTypeResponseDTO fetchFullConsultingTypeSettingsBySlug(String slug) {
    return ConsultingTypeMapper.mapConsultingType(
        consultingTypeRepositoryService.getConsultingTypeBySlug(slug),
        FullConsultingTypeMapper::mapConsultingType);
  }

  public Optional<FullConsultingTypeResponseDTO> fetchFullConsultingTypeSettingsByTenantId(
      Integer tenantId) {
    ConsultingType consultingTypeByTenantId =
        consultingTypeRepositoryService.getConsultingTypeByTenantId(tenantId);
    if (consultingTypeByTenantId != null) {
      return Optional.of(
          ConsultingTypeMapper.mapConsultingType(
              consultingTypeByTenantId, FullConsultingTypeMapper::mapConsultingType));
    } else {
      return Optional.empty();
    }
  }

  /**
   * Fetch a consulting type with extended set of properties by id.
   *
   * @param consultingTypeId the consulting type id
   * @return a {@link FullConsultingTypeResponseDTO} instance
   */
  public ExtendedConsultingTypeResponseDTO fetchExtendedConsultingTypeSettingsById(
      Integer consultingTypeId) {
    return ConsultingTypeMapper.mapConsultingType(
        consultingTypeRepositoryService.getConsultingTypeById(consultingTypeId),
        ExtendedConsultingTypeMapper::mapConsultingType);
  }

  /**
   * Fetch a consulting type with basic set of properties by id.
   *
   * @param consultingTypeId the consulting type id
   * @return a {@link BasicConsultingTypeResponseDTO} instance
   */
  public BasicConsultingTypeResponseDTO fetchBasicConsultingTypeSettingsById(
      Integer consultingTypeId) {
    return ConsultingTypeMapper.mapConsultingType(
        consultingTypeRepositoryService.getConsultingTypeById(consultingTypeId),
        BasicConsultingTypeMapper::mapConsultingType);
  }

  public FullConsultingTypeResponseDTO createConsultingType(
      final ConsultingTypeDTO consultingTypeDTO) {
    ConsultingType consultingType = consultingTypeConverter.convert(consultingTypeDTO);
    consultingType.setId(consultingTypeRepositoryService.getNextId());
    final Optional<ConsultingTypeEntity> createdConsultingType =
        consultingTypeRepositoryService.addConsultingType(consultingType);
    if (createdConsultingType.isPresent()) {
      return ConsultingTypeMapper.mapConsultingType(
          createdConsultingType.get(), FullConsultingTypeMapper::mapConsultingType);
    } else {
      throw new InternalServerErrorException(
          String.format(
              "Could not create a new consulting type with slug %s", consultingTypeDTO.getSlug()));
    }
  }

  public FullConsultingTypeResponseDTO updateConsultingType(
      Integer consultingTypeId, ConsultingTypePatchDTO consultingTypePatchDTO) {

    ConsultingType consultingType =
        consultingTypeRepositoryService.getConsultingTypeById(consultingTypeId);

    if (hasOnlyLimitedPatchPriviliges()) {
      assertChangesAreAllowedForUsersWithLimitedPatchPermission(
          consultingTypePatchDTO, consultingType);
    }

    consultingType = consultingTypeConverter.convert(consultingType, consultingTypePatchDTO);
    var updated = consultingTypeRepositoryService.update(consultingType);
    return ConsultingTypeMapper.mapConsultingType(
        updated, FullConsultingTypeMapper::mapConsultingType);
  }

  void assertChangesAreAllowedForUsersWithLimitedPatchPermission(
      ConsultingTypePatchDTO consultingTypePatchDTO, ConsultingType consultingType) {

    if (multitenancyWithSingleDomainEnabled
        && isChanged(
            consultingTypePatchDTO.getLanguageFormal(), consultingType.getLanguageFormal())) {
      throw new AccessDeniedException("Not allowed to change language formal");
    }

    if (isChanged(
        consultingTypePatchDTO.getIsVideoCallAllowed(), consultingType.getIsVideoCallAllowed())) {
      throw new AccessDeniedException("Not allowed to change vide call settings");
    }
  }

  private boolean isChanged(Boolean inputSettings, boolean existingSettingsToCompare) {
    return nullAsFalse(inputSettings) != existingSettingsToCompare;
  }

  boolean nullAsFalse(Boolean value) {
    return value != null && value;
  }

  private boolean hasOnlyLimitedPatchPriviliges() {
    return authenticatedUser
            .getGrantedAuthorities()
            .contains(AuthorityValue.LIMITED_PATCH_CONSULTING_TYPE)
        && !authenticatedUser
            .getGrantedAuthorities()
            .contains(AuthorityValue.PATCH_CONSULTING_TYPE);
  }
}
