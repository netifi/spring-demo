package io.netifi.springone.demo;

import com.google.common.math.IntMath;
import io.netifi.proteus.annotations.ProteusClient;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.RoundingMode;

@Component
public class DefaultTournamentService implements TournamentService {
  private static final Logger logger = LogManager.getLogger(DefaultTournamentService.class);
  private static int WINDOW_SIZE = 2;
  private static int CONCURRENCY = 4;

  @ProteusClient(group = "springone.demo.records")
  private RecordsServiceClient recordsService;

  @ProteusClient(group = "springone.demo.ranking")
  private RankingServiceClient rankingService;

  @Override
  public Flux<RoundResult> tournament(RecordsRequest request, ByteBuf metadata) {
    int maxRounds = IntMath.log2(request.getMaxResults(), RoundingMode.UP);
    return tournament(recordsService.records(request), 1, maxRounds);
  }

  private Flux<RoundResult> tournament(Flux<Record> records, int round, int maxRounds) {
    Flux<Record> winners = round(records);
    Flux<RoundResult> result = winners
        .map(winner -> RoundResult.newBuilder()
            .setRound(round)
            .setWinner(winner)
            .build());
    return (round < maxRounds) ? result
        .mergeWith(tournament(winners, round + 1, maxRounds)) : result;
  }

  private Flux<Record> round(Flux<Record> records) {
    return records.window(WINDOW_SIZE)
        .flatMap(window -> window.collectMap(Record::getId))
        .flatMapSequential(map -> {
          RankingRequest rankingRequest = RankingRequest.newBuilder().addAllRecords(map.values()).build();
          return rankingService
              .rank(rankingRequest)
              .map(response -> map.get(response.getId()));
        }, CONCURRENCY);
  }
}
