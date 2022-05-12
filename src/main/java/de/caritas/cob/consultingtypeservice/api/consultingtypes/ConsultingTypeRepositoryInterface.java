package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;

public interface ConsultingTypeRepositoryInterface {

  List<ConsultingType> getListOfConsultingTypes();

  ConsultingType getConsultingTypeById(Integer consultingTypeId);

  ConsultingType getConsultingTypeBySlug(String slug);

  void addConsultingType(ConsultingType consultingType);

}
