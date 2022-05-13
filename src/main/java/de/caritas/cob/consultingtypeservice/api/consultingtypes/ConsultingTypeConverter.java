package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ConsultingTypeConverter {

  public List<ConsultingType> convertList(List<ConsultingTypeEntity> consultingTypeEntities) {
    return consultingTypeEntities.stream().map(this::copyToConsultingType).collect(
        Collectors.toList());
  }

  private ConsultingType copyToConsultingType(ConsultingTypeEntity entity) {
    ConsultingType target = new ConsultingType();
    BeanUtils.copyProperties(entity, target);
    return target;
  }
}
