package io.netifi.springone.demo;

import com.netifi.proteus.springboot.EnableProteus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProteus(group = "springone.demo.ranking")
public class RankingServiceApplication {
  public static void main(String... args) {
    SpringApplication.run(RankingServiceApplication.class, args);
  }
}
