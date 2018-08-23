package io.netifi.springone.demo.client;

import com.netifi.proteus.springboot.EnableProteus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProteus(group = "springone.demo.clients")
public class ClientApplication {

  public static void main(String... args) {
    SpringApplication.run(ClientApplication.class, args);
  }
}
