package io.rsocket.springone.demo.client;

import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.spring.core.annotation.Group;
import io.rsocket.springone.demo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ClientRunner implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(ClientRunner.class);

    @Group("springone.demo.tournament")
    private TournamentServiceClient tournamentService;

    @Override
    public void run(String... args) throws Exception {
        RecordsRequest request = RecordsRequest.newBuilder().setMaxResults(16).build();

        Flux<RoundResult> round1 = tournamentService.tournament(request);

        for (RoundResult record: round1.toIterable()) {
            logger.info(JsonFormat.printer().print(record));
        }

        // Exit
        System.exit(0);
    }
}
