package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static java.util.Objects.nonNull;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/** Repository for {@link ConsultingType} groups. */
@Repository
@Primary
@ConditionalOnExpression("${multitenancy.enabled:true}")
@RequiredArgsConstructor
@Slf4j
public class ConsultingTypeGroupTenantAwareRepositoryImpl implements ConsultingTypeGroupRepository {

  private static final Long TECHNICAL_TENANT_ID = 0L;

  private final @NonNull ConsultingTypeTenantAwareRepository
      consultingTypeMongoTenantAwareRepository;

  /* it may not be cached, as the result is different for each tenant */
  public Map<String, List<ConsultingType>> getConsultingTypesGroupMap() {
    var consultingTypesGroupMap = new HashMap<String, List<ConsultingType>>();
    findAllConsultingTypes()
        .forEach(
            consultingTypeEntity ->
                addConsultingType(consultingTypesGroupMap, consultingTypeEntity));
    return consultingTypesGroupMap;
  }

  private List<ConsultingTypeEntity> findAllConsultingTypes() {
    if (isTechnicalTenantContext()) {
      return consultingTypeMongoTenantAwareRepository.findAll();
    } else {
      log.info(
          "Finding all consulting types with tenantId equal to {}",
          TenantContext.getCurrentTenant());
      return consultingTypeMongoTenantAwareRepository.findAllHavingTenantId(
          TenantContext.getCurrentTenant());
    }
  }

  private boolean isTechnicalTenantContext() {
    return TECHNICAL_TENANT_ID.equals(TenantContext.getCurrentTenant());
  }

  private void addConsultingType(
      Map<String, List<ConsultingType>> consultingTypesGroupMap, ConsultingType consultingType) {
    if (nonNull(consultingType.getGroups())) {
      consultingType
          .getGroups()
          .forEach(
              group -> {
                consultingTypesGroupMap.computeIfAbsent(group, newGroup -> new ArrayList<>());
                consultingTypesGroupMap.get(group).add(consultingType);
              });
    }
  }
}
