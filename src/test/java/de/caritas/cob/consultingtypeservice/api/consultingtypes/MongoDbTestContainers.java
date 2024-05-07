package de.caritas.cob.consultingtypeservice.api.consultingtypes;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MongoDbTestContainers {

  @Container
  public static MongoDBContainer mongoDbContainer =
      new MongoDBContainer("mongo:6.0").withExposedPorts(27017);
}
