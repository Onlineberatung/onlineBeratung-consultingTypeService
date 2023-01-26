package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import de.caritas.cob.consultingtypeservice.api.exception.httpresponses.NotFoundException;
import de.caritas.cob.consultingtypeservice.api.model.ConsultingTypeEntity;
import de.caritas.cob.consultingtypeservice.api.service.LogService;
import de.caritas.cob.consultingtypeservice.api.tenant.TenantContext;
import de.caritas.cob.consultingtypeservice.schemas.model.ConsultingType;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/** Repository for {@link ConsultingType}. */
@Repository("tenantAware")
@Primary
@ConditionalOnExpression("${multitenancy.enabled:true}")
@AllArgsConstructor
@Slf4j
public class ConsultingTypeMongoTenantAwareRepositoryService
    implements ConsultingTypeRepositoryService {

  private static final Long TECHNICAL_TENANT_ID = 0L;
  private @NonNull ConsultingTypeTenantAwareRepository consultingTypeMongoTenantAwareRepository;
  private @NonNull ConsultingTypeConverter consultingTypeConverter;

  /**
   * Get a complete list of all {@link ConsultingType}.
   *
   * @return a {@link List} of {@link ConsultingType}
   */
  public List<ConsultingType> getListOfConsultingTypes() {
    if (isTechnicalTenantContext()) {
      List<ConsultingTypeEntity> consultingTypeEntities =
          consultingTypeMongoTenantAwareRepository.findAll();
      return consultingTypeConverter.convertList(consultingTypeEntities);
    } else {
      List<ConsultingTypeEntity> allHavingTenantId =
          consultingTypeMongoTenantAwareRepository.findAllHavingTenantId(
              TenantContext.getCurrentTenant());
      return consultingTypeConverter.convertList(allHavingTenantId);
    }
  }

  private boolean isTechnicalTenantContext() {
    return TECHNICAL_TENANT_ID.equals(TenantContext.getCurrentTenant());
  }

  /**
   * Get a consulting type by its id.
   *
   * @param consultingTypeId the id of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeById(Integer consultingTypeId) {

    Optional<ConsultingType> byId = findById(consultingTypeId);

    if (byId.isEmpty()) {
      throw new NotFoundException(
          String.format("Consulting type with id %s not found.", consultingTypeId));
    }
    return byId.get();
  }

  private Optional<ConsultingType> findById(Integer consultingTypeId) {
    if (isTechnicalTenantContext()) {
      return Optional.ofNullable(
          consultingTypeMongoTenantAwareRepository.findByConsultingTypeId(consultingTypeId));
    }
    return Optional.ofNullable(
        consultingTypeMongoTenantAwareRepository.findConsultingTypeByIdAndTenantId(
            consultingTypeId, TenantContext.getCurrentTenant()));
  }

  /**
   * Get a consulting type by its slug.
   *
   * @param slug the slug of the consulting type
   * @return the {@link ConsultingType} instance
   */
  public ConsultingType getConsultingTypeBySlug(String slug) {
    return findBySlug(slug).stream()
        .findFirst()
        .orElseThrow(
            () ->
                new NotFoundException(
                    String.format("Consulting type with slug %s not found.", slug)));
  }

  private List<ConsultingType> findBySlug(String slug) {
    if (isTechnicalTenantContext()) {
      List<ConsultingTypeEntity> bySlug = consultingTypeMongoTenantAwareRepository.findBySlug(slug);
      consultingTypeConverter.convertList(bySlug);
    }
    List<ConsultingTypeEntity> bySlugAndTenantId =
        consultingTypeMongoTenantAwareRepository.findBySlugAndTenantId(
            slug, TenantContext.getCurrentTenant());
    return consultingTypeConverter.convertList(bySlugAndTenantId);
  }

  /**
   * Add a consulting type to the repository.
   *
   * @param consultingType the {@link ConsultingType} to add
   */
  public Optional<ConsultingTypeEntity> addConsultingType(final ConsultingType consultingType) {
    log.debug("Using tenant aware repository service to try to add consulting type");
    if (isConsultingTypeWithGivenIdPresent(consultingType)
        || isConsultingTypeWithGivenSlugPresent(consultingType)) {
      LogService.logWarning(
          String.format(
              "Could not add consulting type. id %s or slug %s is not unique",
              consultingType.getId(), consultingType.getSlug()));
      return Optional.empty();
    } else {
      final ConsultingTypeEntity consultingTypeEntity = new ConsultingTypeEntity();
      BeanUtils.copyProperties(consultingType, consultingTypeEntity);
      return Optional.of(this.consultingTypeMongoTenantAwareRepository.save(consultingTypeEntity));
    }
  }

  @Override
  public ConsultingTypeEntity update(ConsultingType consultingType) {
    return this.consultingTypeMongoTenantAwareRepository.save(
        (ConsultingTypeEntity) consultingType);
  }

  @Override
  public Integer getNextId() {
    final ConsultingTypeEntity consultingType =
        consultingTypeMongoTenantAwareRepository.findFirstByOrderByIdDesc();
    if (consultingType == null) {
      return 0;
    }
    return consultingType.getId() + 1;
  }

  @Override
  public ConsultingType getConsultingTypeByTenantId(Integer tenantId) {
    return consultingTypeMongoTenantAwareRepository.findByTenantId(tenantId);
  }

  private boolean isConsultingTypeWithGivenIdPresent(ConsultingType consultingType) {
    return findById(consultingType.getId()).isPresent();
  }

  protected boolean isConsultingTypeWithGivenSlugPresent(ConsultingType consultingType) {
    return !consultingTypeMongoTenantAwareRepository
        .findBySlugAndTenantId(consultingType.getSlug(), Long.valueOf(consultingType.getTenantId()))
        .isEmpty();
  }
}
