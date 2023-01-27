package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.Assert.assertEquals;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = "spring.profiles.active=testing")
public class ConsultingTypeRespositoryIT {

  @Autowired private ConsultingTypeLoader consultingTypeLoader;
  @Autowired private ConsultingTypeRepositoryService consultingTypeRepositoryService;

  @Test
  public void getConsultingTypeById_Should_ReturnCorrectConsultingType() {

    Integer consultingTypeId = 0;
    String slug = "consultingtype0";
    ConsultingType result = consultingTypeRepositoryService.getConsultingTypeById(consultingTypeId);
    assertEquals(consultingTypeId, result.getId());
    assertEquals(slug, result.getSlug());
  }

  @Test
  public void getConsultingTypeBySlug_Should_ReturnCorrectConsultingType() {

    Integer consultingTypeId = 0;
    String slug = "consultingtype0";
    ConsultingType result = consultingTypeRepositoryService.getConsultingTypeBySlug(slug);
    assertEquals(consultingTypeId, result.getId());
    assertEquals(slug, result.getSlug());
  }

  @Test
  public void getListOfConsultingTypes_Should_ReturnCompleteListOfConsultingTypes() {

    List<ConsultingType> result = consultingTypeRepositoryService.getListOfConsultingTypes();
    assertEquals(5, result.size());
  }
}
