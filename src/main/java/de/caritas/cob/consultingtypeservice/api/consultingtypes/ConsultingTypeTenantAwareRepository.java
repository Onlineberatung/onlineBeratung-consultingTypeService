package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.Query;

@Primary
@ConditionalOnExpression("${multitenancy.enabled:true}")
public interface ConsultingTypeTenantAwareRepository extends ConsultingTypeRepository {

  @Query(value = "{'tenantId': ?0 }")
  List<ConsultingTypeEntity> findAllHavingTenantId(Long tenantId);

  List<ConsultingTypeEntity> findBySlugAndTenantId(String slug, Long tenantId);

  @Query(value = "{'_id': ?0, 'tenantId': ?1 }")
  ConsultingTypeEntity findConsultingTypeByIdAndTenantId(Integer consultingtypeId, Long tenantId);
}
