package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Repository for {@link ConsultingType}.
 */
@Component
@RequiredArgsConstructor
public class ConsultingTypeMongoRepositoryService implements ConsultingTypeRepositoryService {

  private @NonNull ConsultingTypeMongoRepository consultingTypeMongoRepository;
  private @NonNull ConsultingTypeConverter consultingTypeConverter;

  /**
   * Get a complete list of all {@link ConsultingType}.
   * @return a {@link List} of {@link ConsultingType}
   */
  public List<ConsultingType> getListOfConsultingTypes() {
    var consultingTypeEntities = consultingTypeMongoRepository.findAll();
    return consultingTypeConverter.convertList(consultingTypeEntities);
  }

  /**
   * Get a consulting type by its id.
   *
   * @param consultingTypeId the id of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeById(Integer consultingTypeId) {
    var byId = Optional.ofNullable(consultingTypeMongoRepository.findByConsultingTypeId(
        consultingTypeId));

    if (byId.isEmpty()) {
      throw new NotFoundException(
          String.format("Consulting type with id %s not found.", consultingTypeId)
      );
    }
    return byId.get();
  }

  /**
   * Get a consulting type by its slug.
   *
   * @param slug the slug of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeBySlug(String slug) {
    return consultingTypeMongoRepository.findBySlug(slug).stream().findFirst()
        .orElseThrow(() -> new NotFoundException(
            String.format("Consulting type with slug %s not found.", slug)));
  }

  /**
   * Add a consulting type to the repository.
   *
   * @param consultingType the {@link ConsultingType} to add
   */
  public void addConsultingType(ConsultingType consultingType) {
    if (isConsultingTypeWithGivenIdPresent(consultingType)
        || isConsultingTypeWithGivenSlugPresent(consultingType)) {
      LogService.logError(String
          .format("Could not initialize consulting type. id %s or slug %s is not unique",
              consultingType.getId(), consultingType.getSlug()));
      throw new UnexpectedErrorException();
    }
    var consultingTypeEntity = new ConsultingTypeEntity();
    BeanUtils.copyProperties(consultingType, consultingTypeEntity);
    this.consultingTypeMongoRepository.save(consultingTypeEntity);
  }

  private boolean isConsultingTypeWithGivenIdPresent(ConsultingType consultingType) {
    return consultingTypeMongoRepository.findById(consultingType.getId().toString()).isPresent();
  }

  protected boolean isConsultingTypeWithGivenSlugPresent(ConsultingType consultingType) {
    return !consultingTypeMongoRepository.findBySlug(consultingType.getSlug()).isEmpty();
  }
}
