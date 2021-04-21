package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.ConsultingTypeServiceApplication;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingTypeSettings;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link ConsultingTypeSettings}
 */
@Repository
public class ConsultingTypeSettingsRepository {

  private Map<Integer, ConsultingTypeSettings> consultingTypeSettingsMap = new HashMap<>();

  /**
   * Get settings for a specific consulting type by it's id
   *
   * @param consultingTypeId the id of the consulting type
   * @return the {@link ConsultingTypeSettings} instance
   */
  public ConsultingTypeSettings getConsultantTypeSettingById(Integer consultingTypeId) {
    return consultingTypeSettingsMap.computeIfAbsent(consultingTypeId, i -> {
      throw new NotFoundException(
          String.format("Consulting type settings for id %s not found.", consultingTypeId)
      );
    });

  }

  /**
   * Get settings for a specific consulting type by it's slug
   *
   * @param slug the slug of the consulting type
   * @return the {@link ConsultingTypeSettings} instance
   */
  public ConsultingTypeSettings getConsultantTypeSettingBySlug(String slug) {
    return consultingTypeSettingsMap
        .entrySet()
        .stream()
        .filter(e -> e.getValue().getSlug().equals(slug))
        .findFirst()
        .orElseThrow(() -> new NotFoundException(
            String.format("Consulting type settings for slug %s not found.", slug))).getValue();
  }

  protected void addConsultingTypeSetting(ConsultingTypeSettings consultingTypeSettings) {
    if (isConsultingTypeWithGivenIdPresent(consultingTypeSettings)
        || isConsultingTypeWithGivenSlugIsPresent(consultingTypeSettings)) {
      LogService.logError(String
          .format("Could not initialize consulting type settings. id %s or slug %s is not unique",
              consultingTypeSettings.getId(), consultingTypeSettings.getSlug()));
      ConsultingTypeServiceApplication.exitServiceWithErrorStatus();
    }

    this.consultingTypeSettingsMap.put(consultingTypeSettings.getId(), consultingTypeSettings);
  }

  private boolean isConsultingTypeWithGivenIdPresent(
      ConsultingTypeSettings consultingTypeSettings) {
    return this.consultingTypeSettingsMap.containsKey(consultingTypeSettings.getId());
  }

  private boolean isConsultingTypeWithGivenSlugIsPresent(
      ConsultingTypeSettings consultingTypeSettings) {
    return consultingTypeSettingsMap
        .entrySet()
        .stream()
        .anyMatch(e -> e.getValue().getSlug().equals(consultingTypeSettings.getSlug()));
  }

}
