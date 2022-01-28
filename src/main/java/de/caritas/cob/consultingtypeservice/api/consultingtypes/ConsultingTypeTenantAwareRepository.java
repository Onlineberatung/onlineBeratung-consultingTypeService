package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.service.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link ConsultingType}.
 */
@Repository
@Primary
@ConditionalOnExpression("${multitenancy.enabled:true}")
public class ConsultingTypeTenantAwareRepository extends ConsultingTypeRepository {

  public Map<Integer, ConsultingType> getConsultingTypesMap() {

    Map<Integer, ConsultingType> filteredMap = new HashMap<>();

    super.getConsultingTypesMap().forEach((s, consultingType) -> {
      if (Long.valueOf(consultingType.getTenantId()).equals(TenantContext.getCurrentTenant())) {
        filteredMap.put(s, consultingType);
      }
    });

    return filteredMap;
  }

}
