package de.caritas.cob.consultingtypeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ConsultingTypeServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsultingTypeServiceApplication.class, args);
  }

}
