package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link ConsultingType}.
 */
@Repository
public class ConsultingTypeRepository {

  protected final Map<Integer, ConsultingType> consultingTypesMap = new HashMap<>();

  /**
   * Get a complete list of all {@link ConsultingType}.
   * @return a {@link List} of {@link ConsultingType}
   */
  public List<ConsultingType> getListOfConsultingTypes() {
    return new ArrayList<>(this.getConsultingTypesMap().values());
  }

  /**
   * Get a consulting type by its id.
   *
   * @param consultingTypeId the id of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeById(Integer consultingTypeId) {
    return this.getConsultingTypesMap().computeIfAbsent(consultingTypeId, i -> {
      throw new NotFoundException(
          String.format("Consulting type with id %s not found.", consultingTypeId)
      );
    });
  }

  /**
   * Get a consulting type by its slug.
   *
   * @param slug the slug of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeBySlug(String slug) {
    return this.getConsultingTypesMap()
        .entrySet()
        .stream()
        .filter(e -> e.getValue().getSlug().equals(slug))
        .findFirst()
        .orElseThrow(() -> new NotFoundException(
            String.format("Consulting type with slug %s not found.", slug))).getValue();
  }

  /**
   * Add a consulting type to the repository.
   *
   * @param consultingType the {@link ConsultingType} to add
   */
  protected void addConsultingType(ConsultingType consultingType) {
    if (isConsultingTypeWithGivenIdPresent(consultingType)
        || isConsultingTypeWithGivenSlugPresent(consultingType)) {
      LogService.logError(String
          .format("Could not initialize consulting type. id %s or slug %s is not unique",
              consultingType.getId(), consultingType.getSlug()));
      throw new UnexpectedErrorException();
    }
    this.consultingTypesMap.put(consultingType.getId(), consultingType);
  }

  private boolean isConsultingTypeWithGivenIdPresent(ConsultingType consultingType) {
    return this.consultingTypesMap.containsKey(consultingType.getId());
  }

  protected boolean isConsultingTypeWithGivenSlugPresent(ConsultingType consultingType) {
    return consultingTypesMap
        .entrySet()
        .stream()
        .anyMatch(e -> e.getValue().getSlug().equals(consultingType.getSlug()));
  }

  protected Map<Integer, ConsultingType> getConsultingTypesMap() {
    return this.consultingTypesMap;
  }
}
