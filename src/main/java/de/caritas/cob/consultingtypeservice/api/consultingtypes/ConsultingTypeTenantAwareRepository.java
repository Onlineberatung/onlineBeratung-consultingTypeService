package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.service.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
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

    Map<Integer, ConsultingType> filteredConsultingTypes = new HashMap<>();

    super.getConsultingTypesMap().forEach((consultingTypeId, consultingType) -> {
      if (consultingType.getTenantId() != null && TenantContext.getCurrentTenant()
          .equals(Long.valueOf(consultingType.getTenantId()))) {
        filteredConsultingTypes.put(consultingTypeId, consultingType);
      }
    });

    return filteredConsultingTypes;
  }

}
