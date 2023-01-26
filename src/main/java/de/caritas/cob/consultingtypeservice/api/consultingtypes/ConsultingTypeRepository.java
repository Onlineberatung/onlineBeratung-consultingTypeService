package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ConsultingTypeRepository extends MongoRepository<ConsultingTypeEntity, String> {

  List<ConsultingTypeEntity> findBySlug(String slug);

  @Query(value = "{'id': ?0 }")
  ConsultingTypeEntity findByConsultingTypeId(Integer consultingtypeId);

  ConsultingTypeEntity findFirstByOrderByIdDesc();

  ConsultingTypeEntity findByTenantId(Integer tenantId);
}
