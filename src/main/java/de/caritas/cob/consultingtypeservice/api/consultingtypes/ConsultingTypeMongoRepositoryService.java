package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/** Repository for {@link ConsultingType}. */
@Repository("tenantUnaware")
@RequiredArgsConstructor
@Slf4j
public class ConsultingTypeMongoRepositoryService implements ConsultingTypeRepositoryService {

  private final @NonNull ConsultingTypeRepository consultingTypeRepository;
  private final @NonNull ConsultingTypeConverter consultingTypeConverter;

  @Value("${feature.multitenancy.with.single.domain.enabled}")
  private boolean multitenancyWithSingleDomainEnabled;

  /**
   * Get a complete list of all {@link ConsultingType}.
   *
   * @return a {@link List} of {@link ConsultingType}
   */
  public List<ConsultingType> getListOfConsultingTypes() {
    var consultingTypeEntities = consultingTypeRepository.findAll();
    return consultingTypeConverter.convertList(consultingTypeEntities);
  }

  /**
   * Get a consulting type by its id.
   *
   * @param consultingTypeId the id of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeById(Integer consultingTypeId) {
    var byId =
        Optional.ofNullable(consultingTypeRepository.findByConsultingTypeId(consultingTypeId));

    if (byId.isEmpty()) {
      throw new NotFoundException(
          String.format("Consulting type with id %s not found.", consultingTypeId));
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
    return consultingTypeRepository.findBySlug(slug).stream()
        .findFirst()
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format("Consulting type with slug %s not found.", slug)));
  }

  @Override
  /*
  This method implementation is required for single-domain-mode.
  In this mode only consultingTypeService is deployed with multitenancyEnabled=false,
  and this functionality is needed for super-admins in admin console.
  */
  public ConsultingType getConsultingTypeByTenantId(Integer tenantId) {
    return consultingTypeRepository.findByTenantId(tenantId);
  }

  /**
   * Add a consulting type to the repository.
   *
   * @param consultingType the {@link ConsultingType} to add
   */
  public Optional<ConsultingTypeEntity> addConsultingType(final ConsultingType consultingType) {
    log.debug("Using tenant unaware repository service to try to add a consulting type");
    if (isConsultingTypeWithGivenIdPresent(consultingType) || slugIsNotValid(consultingType)) {
      LogService.logWarning(
          String.format(
              "Could not add consulting type. id %s or slug %s is not unique",
              consultingType.getId(), consultingType.getSlug()));
      return Optional.empty();
    } else {
      final var consultingTypeEntity = new ConsultingTypeEntity();
      BeanUtils.copyProperties(consultingType, consultingTypeEntity);
      return Optional.of(this.consultingTypeRepository.save(consultingTypeEntity));
    }
  }

  @Override
  public Integer getNextId() {
    final ConsultingTypeEntity consultingType = consultingTypeRepository.findFirstByOrderByIdDesc();
    if (consultingType == null) {
      return 0;
    }
    return consultingType.getId() + 1;
  }

  @Override
  public ConsultingTypeEntity update(ConsultingType consultingType) {
    return this.consultingTypeRepository.save((ConsultingTypeEntity) consultingType);
  }

  private boolean slugIsNotValid(ConsultingType consultingType) {
    return nonSingleDomainMode() && isConsultingTypeWithGivenSlugPresent(consultingType);
  }

  private boolean nonSingleDomainMode() {
    return !multitenancyWithSingleDomainEnabled;
  }

  private boolean isConsultingTypeWithGivenIdPresent(ConsultingType consultingType) {
    return consultingTypeRepository.findById(consultingType.getId().toString()).isPresent();
  }

  protected boolean isConsultingTypeWithGivenSlugPresent(ConsultingType consultingType) {
    return !consultingTypeRepository.findBySlug(consultingType.getSlug()).isEmpty();
  }
}
