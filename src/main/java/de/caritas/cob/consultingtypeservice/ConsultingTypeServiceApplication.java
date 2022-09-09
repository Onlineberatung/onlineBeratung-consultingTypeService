package de.caritas.cob.consultingtypeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultingTypeServiceApplication {

  static {
    System.setProperty("os.arch", "i686_64");
  }

  public static void main(String[] args) {
    SpringApplication.run(ConsultingTypeServiceApplication.class, args);
  }

}
