package de.caritas.cob.consultingtypeservice.api.manager.consultingtype.whiteSpot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Settings for white spot agency
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WhiteSpot {

  private boolean whiteSpotAgencyAssigned;
  private Long whiteSpotAgencyId;
}
