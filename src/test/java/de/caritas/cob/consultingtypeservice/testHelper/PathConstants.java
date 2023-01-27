package de.caritas.cob.consultingtypeservice.testHelper;

public class PathConstants {

  public static final String ROOT_PATH = "/consultingtypes";
  public static final String PATH_GET_BASIC_CONSULTING_TYPE_LIST = ROOT_PATH + "/basic";
  public static final String PATH_GET_FULL_CONSULTING_TYPE_BY_ID = ROOT_PATH + "/%s/full";
  public static final String PATH_GET_BASIC_CONSULTING_TYPE_BY_ID = ROOT_PATH + "/%s/basic";
  public static final String PATH_GET_EXTENDED_CONSULTING_TYPE_BY_ID = ROOT_PATH + "/%s/extended";
  public static final String PATH_GET_FULL_CONSULTING_TYPE_BY_SLUG = ROOT_PATH + "/byslug/%s/full";

  public static final String PATH_GET_FULL_CONSULTING_TYPE_BY_TENANT =
      ROOT_PATH + "/bytenant/%s/full";
  public static final String PATH_GET_CONSULTING_TYPE_GROUPS = ROOT_PATH + "/groups";
}
