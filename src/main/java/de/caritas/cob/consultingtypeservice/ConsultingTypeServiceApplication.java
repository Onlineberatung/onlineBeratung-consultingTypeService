package de.caritas.cob.consultingtypeservice;

import de.caritas.cob.consultingtypeservice.api.exception.UnexpectedErrorException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ConsultingTypeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsultingTypeServiceApplication.class, args);
  }

  /**
   * Exit the application with error code 1
   */
  public static void exitService() {
    throw new UnexpectedErrorException();
  }

}
