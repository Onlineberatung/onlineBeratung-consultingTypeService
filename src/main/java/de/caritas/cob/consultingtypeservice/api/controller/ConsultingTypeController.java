package de.caritas.cob.consultingtypeservice.api.controller;

import de.caritas.cob.consultingtypeservice.generated.api.controller.ConsultingtypesApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for consulting type API requests
 *
 */
@RestController
@Api(tags = "consulting-type-controller")
public class ConsultingTypeController implements ConsultingtypesApi {

}
