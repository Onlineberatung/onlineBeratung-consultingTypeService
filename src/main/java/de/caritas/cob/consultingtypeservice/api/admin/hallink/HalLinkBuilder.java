package de.caritas.cob.consultingtypeservice.api.admin.hallink;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import de.caritas.cob.consultingtypeservice.api.model.HalLink;
import de.caritas.cob.consultingtypeservice.api.model.HalLink.MethodEnum;
import org.springframework.http.HttpEntity;

public interface HalLinkBuilder {

  default HalLink buildHalLink(HttpEntity<?> httpEntity, MethodEnum method) {
    var link = linkTo(httpEntity).withSelfRel();
    var halLink = new HalLink();
    halLink.setHref(link.getHref());
    halLink.setMethod(method);
    halLink.setTemplated(link.isTemplated());
    return halLink;
  }
}
