package de.caritas.cob.consultingtypeservice.api.admin.controller;

import de.caritas.cob.consultingtypeservice.api.admin.hallink.RootDTOBuilder;
import de.caritas.cob.consultingtypeservice.api.admin.service.ConsultingTypeAdminService;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeAdminResultDTO;
import de.caritas.cob.consultingtypeservice.api.model.RootDTO;
import de.caritas.cob.consultingtypeservice.generated.api.admin.controller.ConsultingtypeadminApi;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** Controller to handle all consulting types admin requests. */
@RestController
@RequiredArgsConstructor
@Api(tags = "consulting-type-admin-controller")
public class ConsultingTypeAdminController implements ConsultingtypeadminApi {

  private final @NonNull ConsultingTypeAdminService consultingTypeAdminService;

  /**
   * Creates the root hal based navigation entity.
   *
   * @return an entity containing the available navigation hal links
   */
  @Override
  public ResponseEntity<RootDTO> getRoot() {
    var rootDTO = new RootDTOBuilder().buildRootDTO();
    return ResponseEntity.ok(rootDTO);
  }

  /**
   * Entry point to retrieve all consulting types.
   *
   * @param page Number of page where to start in the query (1 = first page) (required)
   * @param perPage Number of items which are being returned per page (required)
   * @return an entity containing the consulting types as {@link ConsultingTypeAdminResultDTO}
   */
  @Override
  public ResponseEntity<ConsultingTypeAdminResultDTO> getConsultingTypes(
      @NotNull @Valid Integer page, @NotNull @Valid Integer perPage) {
    var consultingTypeAdminResultDTO =
        this.consultingTypeAdminService.findConsultingTypes(page, perPage);

    return ResponseEntity.ok(consultingTypeAdminResultDTO);
  }
}
