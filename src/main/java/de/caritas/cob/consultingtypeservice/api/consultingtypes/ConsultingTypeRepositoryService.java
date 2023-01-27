package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Optional;

public interface ConsultingTypeRepositoryService {

  List<ConsultingType> getListOfConsultingTypes();

  ConsultingType getConsultingTypeById(Integer consultingTypeId);

  ConsultingType getConsultingTypeBySlug(String slug);

  Optional<ConsultingTypeEntity> addConsultingType(ConsultingType consultingType);

  ConsultingTypeEntity update(ConsultingType consultingType);

  Integer getNextId();

  ConsultingType getConsultingTypeByTenantId(Integer tenantId);
}
