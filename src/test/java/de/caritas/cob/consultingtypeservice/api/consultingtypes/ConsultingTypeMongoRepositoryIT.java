package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestPropertySource("classpath:application-testing.properties")
@SpringBootTest(
    classes = ConsultingTypeServiceApplication.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class ConsultingTypeMongoRepositoryIT {

  @Container
  static MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:6.0"));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  private final String MONGO_COLLECTION_NAME = "consulting_types";

  @Autowired private ConsultingTypeRepository consultingTypeRepository;

  @Autowired MongoTemplate mongoTemplate;

  @BeforeEach
  public void initializeMongoDbWithData() throws IOException {
    mongoTemplate.dropCollection(MONGO_COLLECTION_NAME);
    insertJsonFromFilename("consulting-type-0.json");
    insertJsonFromFilename("consulting-type-1.json");
    insertJsonFromFilename("consulting-type-2.json");
  }

  private void insertJsonFromFilename(String fileName) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    ConsultingType consultingType =
        objectMapper.readValue(
            new ClassPathResource("consulting-type-settings-tenant-specific/" + fileName).getFile(),
            new TypeReference<>() {});
    mongoTemplate.insert(consultingType, MONGO_COLLECTION_NAME);
  }

  @Test
  void findByConsultingTypeId_Should_ReturnCorrectConsultingType() {
    // given
    Integer consultingTypeId = 10;
    String slug = "consultingtype10";

    // when
    ConsultingType result = consultingTypeRepository.findByConsultingTypeId(consultingTypeId);

    // then
    assertThat(consultingTypeId).isEqualTo(result.getId());
    assertThat(slug).isEqualTo(result.getSlug());
  }

  @Test
  void findBySlug_Should_ReturnCorrectConsultingType() {
    // given
    Integer consultingTypeId = 10;
    String slug = "consultingtype10";

    // when
    List<ConsultingTypeEntity> result = consultingTypeRepository.findBySlug(slug);

    // then
    assertThat(consultingTypeId).isEqualTo(result.get(0).getId());
    assertThat(slug).isEqualTo(result.get(0).getSlug());
  }

  @Test
  void findAll_Should_ReturnAllConsultingTypes() {
    // when
    List<ConsultingTypeEntity> result = consultingTypeRepository.findAll();
    // then
    assertThat(result).hasSize(3);
  }
}
