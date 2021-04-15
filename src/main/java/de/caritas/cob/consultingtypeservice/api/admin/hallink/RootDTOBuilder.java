package de.caritas.cob.consultingtypeservice.api.admin.hallink;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import de.caritas.cob.consultingtypeservice.api.model.HalLink;
import de.caritas.cob.consultingtypeservice.api.model.HalLink.MethodEnum;
import de.caritas.cob.consultingtypeservice.api.model.RootDTO;
import de.caritas.cob.consultingtypeservice.api.model.RootLinks;
import de.caritas.cob.consultingtypeservice.generated.api.admin.controller.ConsultingtypeadminApi;

/**
 * Builder to create the root navigation hal DTO.
 */
public class RootDTOBuilder implements HalLinkBuilder {

  public static final Integer DEFAULT_PAGE = 1;
  public static final Integer DEFAULT_PER_PAGE = 20;
  public static final Integer DEFAULT_TOTAL_PAGES = 0;

  /**
   * Builds the root navigation DTO.
   *
   * @return the {@link RootDTO} containing hal links
   */
  public RootDTO buildRootDTO() {
    return new RootDTO()
        .links(new RootLinks()
            .self(buildSelfLink()));
  }

  private HalLink buildSelfLink() {
    return buildHalLink(methodOn(ConsultingtypeadminApi.class).getRoot(), MethodEnum.GET);
  }

}
