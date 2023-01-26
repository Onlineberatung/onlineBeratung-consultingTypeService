package de.caritas.cob.consultingtypeservice.api.admin.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.consultingtypes.ConsultingTypeRepositoryService;
import de.caritas.cob.consultingtypeservice.api.model.ExtendedConsultingTypeResponseDTO;
import de.caritas.cob.consultingtypeservice.api.model.PaginationLinks;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsultingTypeServiceApplication.class)
@TestPropertySource(properties = "spring.profiles.active=testing")
public class ConsultingTypeAdminServiceIT {

  @Autowired private ConsultingTypeAdminService consultingTypeAdminService;
  @Autowired private ConsultingTypeRepositoryService consultingTypeRepositoryService;

  @Test
  public void findConsultingTypes_Should_returnOneResult_When_perPageIsSetToOneAndPageIsSetToOne() {

    List<ExtendedConsultingTypeResponseDTO> consultingTypes =
        this.consultingTypeAdminService.findConsultingTypes(1, 1).getEmbedded();

    assertThat(consultingTypes, hasSize(1));
  }

  @Test
  public void findConsultingTypes_Should_returnOneResult_When_paginationParamsAreZero() {
    List<ExtendedConsultingTypeResponseDTO> consultingTypes =
        this.consultingTypeAdminService.findConsultingTypes(0, 0).getEmbedded();

    assertThat(consultingTypes, hasSize(1));
  }

  @Test
  public void findConsultingTypes_Should_returnOneResult_When_paginationParamsAreNegative() {
    List<ExtendedConsultingTypeResponseDTO> consultingTypes =
        this.consultingTypeAdminService.findConsultingTypes(-100, -1000).getEmbedded();

    assertThat(consultingTypes, hasSize(1));
  }

  @Test
  public void
      findConsultingTypes_Should_returnPaginatedEntities_When_paginationParamsAreSplitted() {
    List<ExtendedConsultingTypeResponseDTO> firstPage =
        this.consultingTypeAdminService.findConsultingTypes(0, 4).getEmbedded();
    List<ExtendedConsultingTypeResponseDTO> secondPage =
        this.consultingTypeAdminService.findConsultingTypes(2, 2).getEmbedded();

    assertThat(firstPage, hasSize(4));
    assertThat(secondPage, hasSize(2));
  }

  @Test
  public void findConsultingTypes_Should_haveExpectedLinks_When_AllParamsAreProvided() {
    PaginationLinks paginationLinks =
        this.consultingTypeAdminService.findConsultingTypes(1, 1).getLinks();

    assertThat(paginationLinks.getSelf(), notNullValue());
    assertThat(
        paginationLinks.getSelf().getHref(),
        endsWith("/consultingtypeadmin/consultingtypes?page=1&perPage=1"));
    assertThat(paginationLinks.getPrevious(), nullValue());
    assertThat(paginationLinks.getNext(), notNullValue());
    assertThat(
        paginationLinks.getNext().getHref(),
        endsWith("/consultingtypeadmin/consultingtypes?page=2&perPage=1"));
  }

  @Test
  public void
      findConsultingTypes_Should_returnAllConsultingTypes_When_ProvidedWithMaxPerPagesParam() {
    List<ExtendedConsultingTypeResponseDTO> page =
        this.consultingTypeAdminService.findConsultingTypes(0, Integer.MAX_VALUE).getEmbedded();

    assertThat(page, hasSize(5));
  }
}
