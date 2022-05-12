package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.mongodb.repository.Query;

@ConditionalOnExpression("${multitenancy.enabled:true}")
public interface ConsultingTypeMongoTenantAwareRepository extends ConsultingTypeMongoRepository {

  @Query(value = "{'tenantId': ?0")
  List<ConsultingType> findAllHavingTenantId(Long tenantId);

  @Query(value = "{'slug': ?0, 'tenantId': ?1")
  List<ConsultingType> findByIdAndTenantId(String slug, Long tenantId);

  @Query(value = "{'slug': ?0, 'tenantId': ?1")
  List<ConsultingType> findBySlugAndTenantId(String slug, Long tenantId);

  @Query(value = "{'_id': ?0, 'tenantId': ?1")
  ConsultingType findConsultingTypeByIdAndTenantId(Integer consultingtypeId, Long tenantId);

}
