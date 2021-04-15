package de.caritas.cob.consultingtypeservice.testHelper;

import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.ConsultingType;
import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.registration.Registration;
import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.whiteSpot.WhiteSpot;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.caritas.cob.consultingtypeservice.api.manager.consultingtype.ConsultingTypeSettings;

public class TestConstants {

  /**
   * ConsultingType
   */

  public static final String INIT_GROUP_NAME = "init";
  public static final Integer INVALID_CONSULTING_TYPE_VALUE = -1;
  public static final ConsultingType CONSULTING_TYPE_SUCHT = ConsultingType.SUCHT;
  public static final ConsultingType CONSULTING_TYPE_U25 = ConsultingType.U25;
  public static final ConsultingType CONSULTING_TYPE_PREGNANCY = ConsultingType.PREGNANCY;
  public static final ConsultingType CONSULTING_TYPE_AIDS = ConsultingType.AIDS;
  public static final ConsultingType CONSULTING_TYPE_CHILDREN = ConsultingType.CHILDREN;
  public static final ConsultingType CONSULTING_TYPE_CURE = ConsultingType.CURE;
  public static final ConsultingType CONSULTING_TYPE_DEBT = ConsultingType.DEBT;
  public static final ConsultingType CONSULTING_TYPE_DISABILITY = ConsultingType.DISABILITY;
  public static final ConsultingType CONSULTING_TYPE_LAW = ConsultingType.LAW;
  public static final ConsultingType CONSULTING_TYPE_OFFENDER = ConsultingType.OFFENDER;
  public static final ConsultingType CONSULTING_TYPE_PARENTING = ConsultingType.PARENTING;
  public static final ConsultingType CONSULTING_TYPE_PLANB = ConsultingType.PLANB;
  public static final ConsultingType CONSULTING_TYPE_REHABILITATION = ConsultingType.REHABILITATION;
  public static final ConsultingType CONSULTING_TYPE_SENIORITY = ConsultingType.SENIORITY;
  public static final ConsultingType CONSULTING_TYPE_SOCIAL = ConsultingType.SOCIAL;
  public static final ConsultingType CONSULTING_TYPE_KREUZBUND = ConsultingType.KREUZBUND;
  public static final ConsultingType CONSULTING_TYPE_MIGRATION = ConsultingType.MIGRATION;
  public static final ConsultingType CONSULTING_TYPE_EMIGRATION = ConsultingType.EMIGRATION;
  public static final ConsultingType CONSULTING_TYPE_HOSPICE = ConsultingType.HOSPICE;
  public static final ConsultingType CONSULTING_TYPE_REGIONAL = ConsultingType.REGIONAL;
  public static final Long WHITESPOT_AGENCY_ID = 100L;
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_AIDS =
      new ConsultingTypeSettings(CONSULTING_TYPE_AIDS, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_CHILDREN =
      new ConsultingTypeSettings(CONSULTING_TYPE_CHILDREN, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_CURE =
      new ConsultingTypeSettings(CONSULTING_TYPE_CURE, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_DEBT =
      new ConsultingTypeSettings(CONSULTING_TYPE_DEBT, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_DISABILITY =
      new ConsultingTypeSettings(CONSULTING_TYPE_DISABILITY, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_LAW =
      new ConsultingTypeSettings(CONSULTING_TYPE_LAW, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_OFFENDER =
      new ConsultingTypeSettings(CONSULTING_TYPE_OFFENDER, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_PARENTING =
      new ConsultingTypeSettings(CONSULTING_TYPE_PARENTING, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_PLANB =
      new ConsultingTypeSettings(CONSULTING_TYPE_PLANB, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_PREGNANCY =
      new ConsultingTypeSettings(CONSULTING_TYPE_PREGNANCY,
          new WhiteSpot(true, WHITESPOT_AGENCY_ID), new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_REHABILITATION =
      new ConsultingTypeSettings(CONSULTING_TYPE_REHABILITATION,
          new WhiteSpot(true, WHITESPOT_AGENCY_ID), new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_SENIORITY =
      new ConsultingTypeSettings(CONSULTING_TYPE_SENIORITY,
          new WhiteSpot(true, WHITESPOT_AGENCY_ID), new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_SOCIAL =
      new ConsultingTypeSettings(CONSULTING_TYPE_SOCIAL, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_SUCHT =
      new ConsultingTypeSettings(CONSULTING_TYPE_SUCHT, new WhiteSpot(true, WHITESPOT_AGENCY_ID),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_U25 =
      new ConsultingTypeSettings(CONSULTING_TYPE_U25, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_KREUZBUND =
      new ConsultingTypeSettings(CONSULTING_TYPE_KREUZBUND, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_MIGRATION =
      new ConsultingTypeSettings(CONSULTING_TYPE_MIGRATION, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_EMIGRATION =
      new ConsultingTypeSettings(CONSULTING_TYPE_EMIGRATION,
          new WhiteSpot(true, WHITESPOT_AGENCY_ID), new Registration(5));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_HOSPICE =
      new ConsultingTypeSettings(CONSULTING_TYPE_HOSPICE, new WhiteSpot(false, null),
          new Registration(3));
  public static final ConsultingTypeSettings CONSULTING_TYPE_SETTINGS_REGIONAL =
      new ConsultingTypeSettings(CONSULTING_TYPE_REGIONAL, new WhiteSpot(false, null),
          new Registration(3));
  public static final Map<Integer, ConsultingTypeSettings> CONSULTING_TYPE_SETTINGS_MAP =
      new HashMap<Integer, ConsultingTypeSettings>() {
        private static final long serialVersionUID = 1L;
        {
          put(CONSULTING_TYPE_SUCHT.getValue(), CONSULTING_TYPE_SETTINGS_SUCHT);
          put(CONSULTING_TYPE_U25.getValue(), CONSULTING_TYPE_SETTINGS_U25);
          put(CONSULTING_TYPE_PREGNANCY.getValue(), CONSULTING_TYPE_SETTINGS_PREGNANCY);
          put(CONSULTING_TYPE_AIDS.getValue(), CONSULTING_TYPE_SETTINGS_AIDS);
          put(CONSULTING_TYPE_CHILDREN.getValue(), CONSULTING_TYPE_SETTINGS_CHILDREN);
          put(CONSULTING_TYPE_CURE.getValue(), CONSULTING_TYPE_SETTINGS_CURE);
          put(CONSULTING_TYPE_DEBT.getValue(), CONSULTING_TYPE_SETTINGS_DEBT);
          put(CONSULTING_TYPE_DISABILITY.getValue(), CONSULTING_TYPE_SETTINGS_DISABILITY);
          put(CONSULTING_TYPE_LAW.getValue(), CONSULTING_TYPE_SETTINGS_LAW);
          put(CONSULTING_TYPE_OFFENDER.getValue(), CONSULTING_TYPE_SETTINGS_OFFENDER);
          put(CONSULTING_TYPE_PARENTING.getValue(), CONSULTING_TYPE_SETTINGS_PARENTING);
          put(CONSULTING_TYPE_PLANB.getValue(), CONSULTING_TYPE_SETTINGS_PLANB);
          put(CONSULTING_TYPE_REHABILITATION.getValue(), CONSULTING_TYPE_SETTINGS_REHABILITATION);
          put(CONSULTING_TYPE_SENIORITY.getValue(), CONSULTING_TYPE_SETTINGS_SENIORITY);
          put(CONSULTING_TYPE_SOCIAL.getValue(), CONSULTING_TYPE_SETTINGS_SOCIAL);
          put(CONSULTING_TYPE_KREUZBUND.getValue(), CONSULTING_TYPE_SETTINGS_KREUZBUND);
          put(CONSULTING_TYPE_MIGRATION.getValue(), CONSULTING_TYPE_SETTINGS_MIGRATION);
          put(CONSULTING_TYPE_EMIGRATION.getValue(), CONSULTING_TYPE_SETTINGS_EMIGRATION);
          put(CONSULTING_TYPE_HOSPICE.getValue(), CONSULTING_TYPE_SETTINGS_HOSPICE);
          put(CONSULTING_TYPE_REGIONAL.getValue(), CONSULTING_TYPE_SETTINGS_REGIONAL);
        }
      };
  public static final Map<Integer, ConsultingTypeSettings> CONSULTING_TYPE_SETTINGS_MAP_WITH_MISSING_CONSULTING_TYPE_SETTINGS_FOR_U25 =
      new HashMap<Integer, ConsultingTypeSettings>() {
        private static final long serialVersionUID = 1L;

        {
          put(CONSULTING_TYPE_SUCHT.getValue(), CONSULTING_TYPE_SETTINGS_SUCHT);
        }
      };

  public static final Integer PAGE_1 = 1;
  public static final Integer PAGE_2 = 2;
  public static final Integer PAGE_3 = 3;
  public static final Integer PER_PAGE_1 = 1;

}
