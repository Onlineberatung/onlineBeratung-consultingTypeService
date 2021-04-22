package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.api.model.CompactConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.service.ConsultingTypeService;
import de.caritas.cob.consultingtypeservice.generated.api.controller.ConsultingtypesApi;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for consulting type API requests
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "consulting-type-controller")
public class ConsultingTypeController implements ConsultingtypesApi {

  private final @NonNull ConsultingTypeService consultingTypeService;

  /**
   * Returns a compact list of all consulting types.
   *
   * @return {@link ResponseEntity} containing {@link List} of {@link CompactConsultingTypeResponseDTO}
   */
  @Override
  public ResponseEntity<List<CompactConsultingTypeResponseDTO>> getCompactConsultingTypeList() {

    List<CompactConsultingTypeResponseDTO> compactConsultingTypeResponseDTOList =
        this.consultingTypeService.getCompactConsultingTypesList();

    return new ResponseEntity<>(compactConsultingTypeResponseDTOList, HttpStatus.OK);
  }
}
