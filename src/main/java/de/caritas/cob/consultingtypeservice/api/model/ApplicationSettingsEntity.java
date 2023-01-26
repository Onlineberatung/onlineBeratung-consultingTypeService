package de.caritas.cob.consultingtypeservice.api.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import de.caritas.cob.consultingtypeservice.schemas.model.ApplicationSettings;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "application_settings")
public class ApplicationSettingsEntity extends ApplicationSettings {

  Map<String, Object> releaseToggles = new LinkedHashMap<>();

  @JsonAnySetter
  public void setReleaseToggles(String key, Object value) {
    releaseToggles.put(key, value);
  }

  public Map<String, Object> getReleaseToggles() {
    return releaseToggles;
  }
}
