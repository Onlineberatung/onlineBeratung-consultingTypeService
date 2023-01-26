package de.caritas.cob.consultingtypeservice.api.repository;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

  @Query("select entity from TopicEntity as entity where entity.tenantId = :tenantId ")
  Collection<TopicEntity> findAllForTenant(Long tenantId);

  @Query("select entity from TopicEntity as entity where entity.status = 'ACTIVE'")
  Collection<TopicEntity> findAllActive();

  @Query(
      "select entity from TopicEntity as entity where entity.status = 'ACTIVE' and entity.tenantId = :tenantId")
  Collection<TopicEntity> findAllActiveForTenant(Long tenantId);

  @Query(
      "select entity from TopicEntity as entity where entity.id = :id and entity.tenantId = :tenantId ")
  Optional<TopicEntity> findByIdForTenant(Long id, Long tenantId);

  Optional<TopicEntity> findByName(String name);
}
