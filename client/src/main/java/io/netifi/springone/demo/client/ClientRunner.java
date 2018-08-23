package io.netifi.springone.demo.client;

import com.google.protobuf.util.JsonFormat;
import io.netifi.proteus.annotations.ProteusClient;
import io.netifi.springone.demo.RecordsRequest;
import io.netifi.springone.demo.RoundResult;
import io.netifi.springone.demo.TournamentServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class ClientRunner implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(ClientRunner.class);

    @ProteusClient(group = "springone.demo.tournament")
    private TournamentServiceClient tournamentService;

    @Override
    public void run(String... args) throws Exception {
        RecordsRequest request = RecordsRequest.newBuilder().setMaxResults(256).build();

        Flux<RoundResult> round1 = tournamentService.tournament(request);

        for (RoundResult record: round1.toIterable()) {
            logger.info(JsonFormat.printer().print(record));
        }

        // Exit
        System.exit(0);
    }
}
