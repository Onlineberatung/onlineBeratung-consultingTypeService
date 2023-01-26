package de.caritas.cob.consultingtypeservice.testHelper;

public class TopicPathConstants {

  public static final String ROOT_PATH = "/topic";
  public static final String ADMIN_ROOT_PATH = "/topicadmin";
  public static final String PUBLIC_ROOT_PATH = "/public";
  public static final String PATH_GET_TOPIC_LIST = ROOT_PATH;
  public static final String ADMIN_PATH_GET_TOPIC_LIST = ADMIN_ROOT_PATH;
  public static final String ADMIN_PATH_GET_TOPIC_BY_ID = ADMIN_ROOT_PATH + "/%s";
  public static final String PATH_PUT_TOPIC_BY_ID = ADMIN_ROOT_PATH + "/%s";
  public static final String PATH_GET_PUBLIC_TOPIC_LIST = ROOT_PATH + PUBLIC_ROOT_PATH;
}
