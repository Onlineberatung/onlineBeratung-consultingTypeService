package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.Assert.assertEquals;

import de.caritas.cob.consultingtypeservice.api.service.tenant.TenantContext;
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
@TestPropertySource(properties = "multitenancy.enabled=true")
@TestPropertySource(properties = "consulting.types.json.path=src/test/resources/consulting-type-settings-tenant-specific")
public class ConsultingTypeRespositoryTenantAwareIT {

  @Autowired
  private ConsultingTypeRepository consultingTypeRepository;

  @Test
  public void getConsultingTypeById_Should_ReturnCorrectConsultingType() {
    TenantContext.setCurrentTenant(1L);
    Integer consultingTypeId = 10;
    String slug = "consultingtype10";
    ConsultingType result = consultingTypeRepository.getConsultingTypeById(consultingTypeId);
    assertEquals(consultingTypeId, result.getId());
    assertEquals(slug, result.getSlug());
    TenantContext.clear();
  }

  @Test
  public void getConsultingTypeBySlug_Should_ReturnCorrectConsultingType() {
    TenantContext.setCurrentTenant(1L);
    Integer consultingTypeId = 10;
    String slug = "consultingtype10";
    ConsultingType result = consultingTypeRepository.getConsultingTypeBySlug(slug);
    assertEquals(consultingTypeId, result.getId());
    assertEquals(slug, result.getSlug());
    TenantContext.clear();
  }

  @Test
  public void getListOfConsultingTypes_Should_ReturnFilteredListOfConsultingTypes() {
    TenantContext.setCurrentTenant(1L);
    List<ConsultingType> result1 = consultingTypeRepository.getListOfConsultingTypes();
    assertEquals(1, result1.size());
    TenantContext.clear();
    TenantContext.setCurrentTenant(2L);
    List<ConsultingType> result2 = consultingTypeRepository.getListOfConsultingTypes();
    assertEquals(2, result2.size());
    TenantContext.clear();

  }

}
