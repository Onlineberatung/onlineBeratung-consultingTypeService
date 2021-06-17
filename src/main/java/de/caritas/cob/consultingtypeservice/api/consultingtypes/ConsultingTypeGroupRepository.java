package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static java.util.Objects.nonNull;

import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link ConsultingType} groups.
 */
@Repository
public class ConsultingTypeGroupRepository {

  private final Map<String, List<ConsultingType>> consultingTypesGroupMap = new HashMap<>();

  /**
   * Get a map with all grouped consulting types.
   *
   * @return a {@link Map} with with the grouped {@link ConsultingType} instances.
   */
  public Map<String, List<ConsultingType>> getConsultingTypesGroupMap() {
    return this.consultingTypesGroupMap;
  }

  /**
   * Add a consulting type to the group repository.
   *
   * @param consultingType the {@link ConsultingType} to add
   */
  protected void addConsultingType(ConsultingType consultingType) {
    if (nonNull(consultingType.getGroup())) {
      initializeGroupInMapIfNotExists(consultingType);
      addConsultingTypeToMap(consultingType);
    }
  }

  private void addConsultingTypeToMap(ConsultingType consultingType) {
    this.consultingTypesGroupMap
        .get(consultingType.getGroup()).add(consultingType);
  }

  private void initializeGroupInMapIfNotExists(ConsultingType consultingType) {
    this.consultingTypesGroupMap.computeIfAbsent(
        consultingType.getGroup(),
        group -> new ArrayList<>());
  }

}
