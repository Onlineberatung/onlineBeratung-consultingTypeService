package de.caritas.cob.consultingtypeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ConsultingTypeServiceApplication {

  static {
    System.setProperty("os.arch", "i686_64");
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsultingTypeServiceApplication.class, args);
  }

}
