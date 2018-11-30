package io.rsocket.springone.demo;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecordsServiceApplication {
  public static void main(String... args) {
    SpringApplication.run(RecordsServiceApplication.class, args);
  }

  //Infrastructure stuff bound to magically go away in spring-boot support release
  @Bean
  PostgresqlConnectionFactory connectionFactory() {
    return new PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration
                .builder() //
                .host("localhost")
                .database("marvel")
                .username("captain")
                .password("america")
                .build()
    );
  }
}
