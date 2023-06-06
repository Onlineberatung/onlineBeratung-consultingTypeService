package de.caritas.cob.consultingtypeservice.api.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic_group")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TopicGroupEntity {

  @Id
  @SequenceGenerator(name = "id_seq", allocationSize = 1, sequenceName = "SEQUENCE_TOPIC_GROUP")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "create_date", nullable = false)
  private LocalDateTime createDate;

  @Column(name = "update_date")
  private LocalDateTime updateDate;

  @JoinTable(
      name = "topic_group_x_topic",
      joinColumns = @JoinColumn(name = "group_id"),
      inverseJoinColumns = @JoinColumn(name = "topic_id"))
  private Set<TopicEntity> topicEntities = new HashSet<>();
}
