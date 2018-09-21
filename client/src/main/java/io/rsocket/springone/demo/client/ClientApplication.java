package io.rsocket.springone.demo.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientApplication {

  public static void main(String... args) {
    SpringApplication.run(ClientApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(ClientRunner clientRunner) {
    return clientRunner::run;
  }
}
