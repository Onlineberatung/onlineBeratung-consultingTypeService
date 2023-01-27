package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import static java.util.Objects.nonNull;

import de.caritas.cob.consultingtypeservice.config.CacheManagerConfig;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/** Repository for {@link ConsultingType} groups. */
@Repository
@RequiredArgsConstructor
public class ConsultingTypeGroupRepositoryImpl implements ConsultingTypeGroupRepository {

  private final @NonNull ConsultingTypeRepository consultingTypeRepository;

  /**
   * Get a map with all grouped consulting types.
   *
   * @return a {@link Map} with with the grouped {@link ConsultingType} instances.
   */
  @Cacheable(cacheNames = CacheManagerConfig.CONSULTING_TYPE_GROUPS_CACHE)
  public Map<String, List<ConsultingType>> getConsultingTypesGroupMap() {
    var consultingTypeEntities = consultingTypeRepository.findAll();
    var consultingTypesGroupMap = new HashMap<String, List<ConsultingType>>();
    consultingTypeEntities.forEach(
        consultingTypeEntity -> addConsultingType(consultingTypesGroupMap, consultingTypeEntity));
    return consultingTypesGroupMap;
  }

  /**
   * Add a consulting type to the group repository.
   *
   * @param consultingType the {@link ConsultingType} to add
   */
  void addConsultingType(
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
