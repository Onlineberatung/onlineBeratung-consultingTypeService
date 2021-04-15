package de.caritas.cob.consultingtypeservice.api.admin.controller;

import de.caritas.cob.consultingtypeservice.api.admin.hallink.RootDTOBuilder;
import de.caritas.cob.consultingtypeservice.api.model.RootDTO;
import de.caritas.cob.consultingtypeservice.generated.api.admin.controller.ConsultingtypeadminApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle all consulting type admin requests.
 */
@RestController
@Api(tags = "admin-consulting-type-controller")
@RequiredArgsConstructor
public class ConsultingTypeAdminController implements ConsultingtypeadminApi {

  /**
   * Creates the root hal based navigation entity.
   *
   * @return a entity containing the available navigation hal links
   */
  @Override
  public ResponseEntity<RootDTO> getRoot() {
    RootDTO rootDTO = new RootDTOBuilder().buildRootDTO();
    return new ResponseEntity<>(rootDTO, HttpStatus.OK);
  }

}
