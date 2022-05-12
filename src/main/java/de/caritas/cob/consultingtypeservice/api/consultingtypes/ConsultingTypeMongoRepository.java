package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ConsultingTypeMongoRepository extends MongoRepository<ConsultingType, String> {

  List<ConsultingType> findBySlug(String slug);

  @Query(value = "{'id': ?0")
  ConsultingType findByConsultingTypeId(Integer consultingtypeId);
}
