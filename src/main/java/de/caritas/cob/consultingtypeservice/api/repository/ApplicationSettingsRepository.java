package de.caritas.cob.consultingtypeservice.api.repository;

import de.caritas.cob.consultingtypeservice.api.model.ApplicationSettingsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationSettingsRepository
    extends MongoRepository<ApplicationSettingsEntity, String> {}
