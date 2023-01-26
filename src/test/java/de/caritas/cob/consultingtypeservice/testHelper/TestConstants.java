package de.caritas.cob.consultingtypeservice.testHelper;

public class TestConstants {

  private static final String RESOURCES_PATH = "src/test/resources/";
  public static final String SRC_TEST_RESOURCES_CONSULTING_TYPE_SETTINGS =
      RESOURCES_PATH + "consulting-type-settings";
  public static final String SRC_TEST_RESOURCES_BROKEN_CONSULTING_TYPE_SETTINGS =
      RESOURCES_PATH + "broken-consulting-type-settings";
  public static final String FILE_INVALID_CONSULTING_TYPE =
      "/invalid-consulting-type-settings/invalid-consulting-type.json";
  public static final String FILE_VALID_COMPLETE_CONSULTING_TYPE =
      RESOURCES_PATH + "consulting-type-settings/consulting-type-0.json";
  public static final String FILE_VALID_COMPLETE_CONSULTING_TYPE_2 =
      RESOURCES_PATH + "consulting-type-settings/consulting-type-1.json";
  public static final String FILE_BROKEN_CONSULTING_TYPE =
      "/broken-consulting-type-settings/broken-consulting-type.json";
  public static final String FILE_NULL_VALUE_CONSULTING_TYPE =
      "/consulting-type-settings/null-value-consulting-type-settings.json";
  public static final String FILE_CONSULTING_TYPE_JSON_SCHEMA = "/schemas/consulting-type.json";
  public static final String BROKEN_FILE_PATH = "c/:/test?\0";
  public static final String FILE_CONSULTING_TYPE_GROUP_RESPONSE_JSON =
      RESOURCES_PATH + "/responses/consulting-type-groups-response.json";
}
