package io.netifi.springone.demo;

import com.netifi.proteus.springboot.EnableProteus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProteus(group = "springone.demo.tournament")
public class TournamentServiceApplication {
  public static void main(String... args) {
    SpringApplication.run(TournamentServiceApplication.class, args);
  }
}
