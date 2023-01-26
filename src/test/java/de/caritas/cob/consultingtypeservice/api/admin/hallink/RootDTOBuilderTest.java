package de.caritas.cob.consultingtypeservice.api.admin.hallink;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import de.caritas.cob.consultingtypeservice.api.model.HalLink.MethodEnum;
import de.caritas.cob.consultingtypeservice.api.model.RootDTO;
import de.caritas.cob.consultingtypeservice.api.model.RootLinks;
import org.junit.Test;

public class RootDTOBuilderTest {

  @Test
  public void buildRootDTO_Should_returnRootDTOWithConsultingTypeHalLinks() {
    RootDTO rootDTO = new RootDTOBuilder().buildRootDTO();

    assertThat(rootDTO, notNullValue());
    RootLinks rootLinks = rootDTO.getLinks();
    assertThat(rootLinks, notNullValue());
    assertThat(rootLinks.getConsultingtypes(), notNullValue());
    assertThat(
        rootLinks.getConsultingtypes().getHref(),
        is("/consultingtypeadmin/consultingtypes?page=1&perPage=20"));
    assertThat(rootLinks.getConsultingtypes().getMethod(), is(MethodEnum.GET));
  }

  @Test
  public void buildRootDTO_Should_returnRootDTOWithSelfHalLink() {
    RootDTO rootDTO = new RootDTOBuilder().buildRootDTO();

    assertThat(rootDTO, notNullValue());
    RootLinks rootLinks = rootDTO.getLinks();
    assertThat(rootLinks, notNullValue());
    assertThat(rootLinks.getSelf().getHref(), is("/consultingtypeadmin"));
    assertThat(rootLinks.getSelf().getMethod(), is(MethodEnum.GET));
  }
}
