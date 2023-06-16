package de.caritas.cob.consultingtypeservice.api.model;

import de.caritas.cob.consultingtypeservice.api.repository.TenantAware;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "topic")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Access(AccessType.FIELD)
@FilterDef(
    name = "tenantFilter",
    parameters = {@ParamDef(name = "tenantId", type = "long")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class TopicEntity implements TenantAware {

  @Id
  @SequenceGenerator(name = "id_seq", allocationSize = 1, sequenceName = "SEQUENCE_TOPIC")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "status", length = 11)
  @Enumerated(EnumType.STRING)
  private TopicStatus status;

  @Column(name = "internal_identifier", nullable = false)
  private String internalIdentifier;

  @Column(name = "tenant_id")
  private Long tenantId;

  @Column(name = "create_date", nullable = false)
  private LocalDateTime createDate;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  @ManyToMany(mappedBy = "topicEntities")
  Set<TopicGroupEntity> topicGroupEntities;

  @Column(name = "fallback_agency_id")
  private Integer fallbackAgencyId;

  @Column(name = "fallback_url")
  private String fallbackUrl;

  @Column(name = "welcome_message")
  private String welcomeMessage;

  @Column(name = "send_next_step_message")
  private Boolean sendNextStepMessage;
}
