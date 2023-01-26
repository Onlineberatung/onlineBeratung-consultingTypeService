package de.caritas.cob.consultingtypeservice.api.consultingtypes.roles;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Role settings of the consulting type settings for consultants. */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Consultant {

  private LinkedHashMap<String, List<String>> roleNames;

  @JsonAnyGetter
  public Map<String, List<String>> getRoleNames() {
    return roleNames;
  }

  @JsonAnySetter
  public void addRoleNames(String key, List<String> value) {
    if (this.roleNames == null) {
      this.roleNames = new LinkedHashMap<>();
    }

    this.roleNames.put(key, value);
  }
}
