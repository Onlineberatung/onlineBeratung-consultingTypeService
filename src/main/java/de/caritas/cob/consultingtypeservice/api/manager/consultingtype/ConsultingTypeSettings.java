package de.caritas.cob.consultingtypeservice.api.manager.consultingtype;

import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.registration.Registration;
import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.whiteSpot.WhiteSpot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultingTypeSettings {

  private ConsultingType consultingType;
  private WhiteSpot whiteSpot;
  private Registration registration;

}
