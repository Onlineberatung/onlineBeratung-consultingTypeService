package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.model.BasicConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeGroupResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypePatchDTO;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.FullConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.service.ConsultingTypeGroupService;
import de.caritas.cob.consultingtypeservice.api.service.ConsultingTypeService;
import de.caritas.cob.consultingtypeservice.generated.api.controller.ConsultingtypesApi;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/** Controller for consulting type API requests. */
@RestController
@RequiredArgsConstructor
@Api(tags = "consulting-type-controller")
public class ConsultingTypeController implements ConsultingtypesApi {

  private final @NonNull ConsultingTypeService consultingTypeService;
  private final @NonNull ConsultingTypeGroupService consultingTypeGroupService;

  /**
   * Returns a list of all consulting types with basic properties.
   *
   * @return {@link ResponseEntity} containing {@link List} of {@link
   *     BasicConsultingTypeResponseDTO}
   */
  @Override
  public ResponseEntity<List<BasicConsultingTypeResponseDTO>> getBasicConsultingTypeList() {

    List<BasicConsultingTypeResponseDTO> basicConsultingTypeResponseDTOList =
        consultingTypeService.fetchBasicConsultingTypesList();

    return !CollectionUtils.isEmpty(basicConsultingTypeResponseDTOList)
        ? new ResponseEntity<>(basicConsultingTypeResponseDTOList, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Returns the consulting type with all properties for a given consulting type id.
   *
   * @param consultingTypeId consulting type id (required)
   * @return {@link ResponseEntity} containing {@link FullConsultingTypeResponseDTO}
   */
  @Override
  public ResponseEntity<FullConsultingTypeResponseDTO> getFullConsultingTypeById(
      @PathVariable Integer consultingTypeId) {
    return new ResponseEntity<>(
        consultingTypeService.fetchFullConsultingTypeSettingsById(consultingTypeId), HttpStatus.OK);
  }

  /**
   * Returns the consulting type with all properties for a given consulting type slug.
   *
   * @param slug cnsulting type slug (required)
   * @return {@link ResponseEntity} containing {@link FullConsultingTypeResponseDTO}
   */
  @Override
  public ResponseEntity<FullConsultingTypeResponseDTO> getFullConsultingTypeBySlug(
      @PathVariable String slug) {
    return new ResponseEntity<>(
        consultingTypeService.fetchFullConsultingTypeSettingsBySlug(slug), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<FullConsultingTypeResponseDTO> getFullConsultingTypeByTenantId(
      @PathVariable Integer tenantId) {

    var fullConsultingTypeResponseDTO =
        consultingTypeService.fetchFullConsultingTypeSettingsByTenantId(tenantId);
    return fullConsultingTypeResponseDTO.isPresent()
        ? new ResponseEntity<>(fullConsultingTypeResponseDTO.get(), HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Returns the consulting type with extended properties for a given consulting type id.
   *
   * @param consultingTypeId consulting type id (required)
   * @return {@link ResponseEntity} containing {@link ExtendedConsultingTypeResponseDTO}
   */
  @Override
  public ResponseEntity<ExtendedConsultingTypeResponseDTO> getExtendedConsultingTypeById(
      @PathVariable Integer consultingTypeId) {
    return new ResponseEntity<>(
        consultingTypeService.fetchExtendedConsultingTypeSettingsById(consultingTypeId),
        HttpStatus.OK);
  }

  /**
   * Returns the consulting type with basic properties for a given consulting type id.
   *
   * @param consultingTypeId consulting type id (required)
   * @return {@link ResponseEntity} containing {@link BasicConsultingTypeResponseDTO}
   */
  @Override
  public ResponseEntity<BasicConsultingTypeResponseDTO> getBasicConsultingTypeById(
      @PathVariable Integer consultingTypeId) {
    return new ResponseEntity<>(
        consultingTypeService.fetchBasicConsultingTypeSettingsById(consultingTypeId),
        HttpStatus.OK);
  }

  /**
   * Returns the consulting type groups with the assigned consulting types.
   *
   * @return {@link ResponseEntity} containing a {@link List} of {@link
   *     ConsultingTypeGroupResponseDTO}
   */
  @Override
  public ResponseEntity<List<ConsultingTypeGroupResponseDTO>> getConsultingTypeGroups() {

    List<ConsultingTypeGroupResponseDTO> consultingTypeGroupResponseDtoList =
        consultingTypeGroupService.fetchConsultingTypeGroupList();

    return !CollectionUtils.isEmpty(consultingTypeGroupResponseDtoList)
        ? new ResponseEntity<>(consultingTypeGroupResponseDtoList, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  @PreAuthorize("hasAuthority('AUTHORIZATION_CREATE_CONSULTING_TYPE')")
  public ResponseEntity<FullConsultingTypeResponseDTO> createConsultingType(
      final ConsultingTypeDTO consultingTypeDTO) {
    return ResponseEntity.ok(consultingTypeService.createConsultingType(consultingTypeDTO));
  }

  @Override
  @PreAuthorize(
      "hasAnyAuthority('AUTHORIZATION_PATCH_CONSULTING_TYPE', "
          + "'AUTHORIZATION_LIMITED_PATCH_CONSULTING_TYPE')")
  public ResponseEntity<FullConsultingTypeResponseDTO> patchConsultingType(
      Integer consultingTypeId, final ConsultingTypePatchDTO consultingTypeDTO) {
    return ResponseEntity.ok(
        consultingTypeService.updateConsultingType(consultingTypeId, consultingTypeDTO));
  }
}
