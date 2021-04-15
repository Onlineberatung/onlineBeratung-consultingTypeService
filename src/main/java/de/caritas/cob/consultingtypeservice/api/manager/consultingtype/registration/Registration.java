package de.caritas.cob.consultingtypeservice.api.manager.consultingtype.registration;

import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.ConsultingTypeSettings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link ConsultingTypeSettings} for registration
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Registration {

  private int minPostcodeSize;
}
