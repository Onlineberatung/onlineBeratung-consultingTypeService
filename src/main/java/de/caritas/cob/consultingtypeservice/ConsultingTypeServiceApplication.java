package de.caritas.cob.consultingtypeservice;

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
  public static void exitServiceWithErrorStatus() {
    System.err.println("Service exited with errors");
    System.exit(1);
  }

}
