package io.rsocket.springone.demo;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;

@SpringBootApplication
public class RecordsServiceApplication {
  public static void main(String... args) {
    SpringApplication.run(RecordsServiceApplication.class, args);
  }

  //Infrastructure stuff bound to magically go away in spring-boot support release
  @Bean
  PostgresqlConnectionFactory connectionFactory() {
    return new PostgresqlConnectionFactory(
            PostgresqlConnectionConfiguration.builder() //
                                            .host("localhost")
                                            .database("marvel")
                                            .username("captain")
                                            .password("america")
                                            .build());
  }

  @Bean
  RecordRepository customerRepository(R2dbcRepositoryFactory factory) {
    return factory.getRepository(RecordRepository.class);
  }

  @Bean
  R2dbcRepositoryFactory repositoryFactory(DatabaseClient client) {
    RelationalMappingContext context = new RelationalMappingContext();
    context.afterPropertiesSet();

    return new R2dbcRepositoryFactory(client, context);
  }

  @Bean
  DatabaseClient databaseClient(ConnectionFactory factory) {
    return DatabaseClient.builder() //
                         .connectionFactory(factory) //
                         .build();
  }
}
