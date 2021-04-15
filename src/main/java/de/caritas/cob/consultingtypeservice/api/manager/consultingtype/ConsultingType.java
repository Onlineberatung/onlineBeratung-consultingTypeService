package de.caritas.cob.consultingtypeservice.api.manager.consultingtype;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Consulting types.
 */
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.NUMBER)
public enum ConsultingType {
  SUCHT(0),
  U25(1),
  PREGNANCY(2),
  PARENTING(3),
  CURE(4),
  DEBT(5),
  SOCIAL(6),
  SENIORITY(7),
  DISABILITY(8),
  PLANB(9),
  LAW(10),
  OFFENDER(11),
  AIDS(12),
  REHABILITATION(13),
  CHILDREN(14),
  KREUZBUND(15),
  MIGRATION(16),
  EMIGRATION(17),
  HOSPICE(18),
  REGIONAL(19),
  MEN(20);

  private final int value;

  public static Optional<ConsultingType> valueOf(int value) {
    return Arrays.stream(values()).filter(legNo -> legNo.value == value).findFirst();
  }

  public int getValue() {
    return value;
  }

}
