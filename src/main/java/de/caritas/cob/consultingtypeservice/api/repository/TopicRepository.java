package de.caritas.cob.consultingtypeservice.api.repository;

import de.caritas.cob.consultingtypeservice.api.model.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

}
