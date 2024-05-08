package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.junit.Assert.assertEquals;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-testing.properties")
@SpringBootTest(
    classes = ConsultingTypeServiceApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class ConsultingTypeRespositoryIT {

  @Autowired private ConsultingTypeLoader consultingTypeLoader;
  @Autowired private ConsultingTypeRepositoryService consultingTypeRepositoryService;

  @Container
  static MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:6.0"));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void getConsultingTypeById_Should_ReturnCorrectConsultingType() {

    Integer consultingTypeId = 0;
    String slug = "consultingtype0";
    ConsultingType result = consultingTypeRepositoryService.getConsultingTypeById(consultingTypeId);
    assertEquals(consultingTypeId, result.getId());
    assertEquals(slug, result.getSlug());
  }

  @Test
  void getConsultingTypeBySlug_Should_ReturnCorrectConsultingType() {

    Integer consultingTypeId = 0;
    String slug = "consultingtype0";
    ConsultingType result = consultingTypeRepositoryService.getConsultingTypeBySlug(slug);
    assertEquals(consultingTypeId, result.getId());
    assertEquals(slug, result.getSlug());
  }

  @Test
  void getListOfConsultingTypes_Should_ReturnCompleteListOfConsultingTypes() {

    List<ConsultingType> result = consultingTypeRepositoryService.getListOfConsultingTypes();
    assertEquals(5, result.size());
  }
}
