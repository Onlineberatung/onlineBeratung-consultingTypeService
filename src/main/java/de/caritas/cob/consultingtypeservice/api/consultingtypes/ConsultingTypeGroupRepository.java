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
    if (nonNull(consultingType.getGroups())) {
      consultingType
          .getGroups()
          .forEach(group -> {
            initializeGroupInMapIfNotExists(group);
            addConsultingTypeToMap(consultingType, group);
          });
    }
  }

  private void addConsultingTypeToMap(ConsultingType consultingType, String group) {
    this.consultingTypesGroupMap
        .get(group).add(consultingType);
  }

  private void initializeGroupInMapIfNotExists(String group) {
    this.consultingTypesGroupMap.computeIfAbsent(
        group,
        newGroup -> new ArrayList<>());
  }

}
