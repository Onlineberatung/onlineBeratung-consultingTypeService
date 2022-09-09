package de.caritas.cob.consultingtypeservice.api.model;

import de.caritas.cob.consultingtypeservice.schemas.model.ApplicationSettings;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application_settings")
public class ApplicationSettingsEntity extends ApplicationSettings {

}
