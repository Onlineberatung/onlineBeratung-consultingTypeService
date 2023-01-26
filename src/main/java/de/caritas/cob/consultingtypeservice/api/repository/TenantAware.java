package de.caritas.cob.consultingtypeservice.api.repository;

/** Marker interface for entities that need to support tenant feature. */
public interface TenantAware {

  /**
   * Sets the fields tenantId during save operations.
   *
   * @param tenantId
   */
  void setTenantId(Long tenantId);

  /** Returns tenantId from given entity. */
  Long getTenantId();
}
