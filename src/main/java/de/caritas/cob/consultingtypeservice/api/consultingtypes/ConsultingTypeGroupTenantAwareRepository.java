package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.service.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link ConsultingType} groups.
 */
@Repository
@Primary
@ConditionalOnExpression("${multitenancy.enabled:true}")
public class ConsultingTypeGroupTenantAwareRepository extends ConsultingTypeGroupRepository {

  /**
   * Get a map with all grouped consulting types filtered for current tenant.
   *
   * @return a {@link Map} with with the grouped {@link ConsultingType} instances.
   */
  public Map<String, List<ConsultingType>> getConsultingTypesGroupMap() {

    Map<String, List<ConsultingType>> filteredMap = new HashMap<>();

    super.getConsultingTypesGroupMap().forEach((s, consultingTypes) -> {
      filteredMap.put(s, consultingTypes.stream().filter(el -> el.getTenantId().equals(
          TenantContext.getCurrentTenant()))
          .collect(Collectors.toList()));
    });

    return filteredMap;
  }

}
