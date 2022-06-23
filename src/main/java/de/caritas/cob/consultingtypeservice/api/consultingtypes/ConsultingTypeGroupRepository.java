package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Map;

public interface ConsultingTypeGroupRepository {
  Map<String, List<ConsultingType>> getConsultingTypesGroupMap();
}
