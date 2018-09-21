package io.rsocket.springone.demo;

import com.google.common.math.IntMath;
import io.netifi.proteus.spring.core.annotation.Group;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.math.RoundingMode;

@Component
public class DefaultTournamentService implements TournamentService {
  private static final Logger logger = LogManager.getLogger(DefaultTournamentService.class);
  private static int WINDOW_SIZE = 2;
  private static int CONCURRENCY = 4;

  @Group("springone.demo.records")
  private RecordsServiceClient recordsService;

  @Group("springone.demo.ranking")
  private RankingServiceClient rankingService;

  @Override
  public Flux<RoundResult> tournament(RecordsRequest request, ByteBuf metadata) {
    int maxRounds = IntMath.log2(request.getMaxResults(), RoundingMode.UP);
    return tournament(recordsService.records(request), maxRounds);
  }

  private Flux<RoundResult> tournament(Flux<Record> records, int round) {
    ConnectableFlux<Record> winners = round(records).publish();
    Flux<RoundResult> roundResult = winners
        .map(winner -> RoundResult.newBuilder()
            .setRound(round)
            .setWinner(winner)
            .build());

    Flux<RoundResult> tournamentResult = (round > 1)
        ? roundResult.mergeWith(tournament(winners, round - 1))
        : roundResult;
    winners.connect();

    return tournamentResult;
  }

  private Flux<Record> round(Flux<Record> records) {
    return records
        .window(WINDOW_SIZE)
        .flatMap(window ->
            window
                .collectMap(Record::getId)
                .flatMap(map -> {
                  RankingRequest rankingRequest = RankingRequest.newBuilder().addAllRecords(map.values()).build();
                  return rankingService
                      .rank(rankingRequest)
                      .map(response -> {
                        logger.info("{} -> {}", map.keySet(), response.getId());
                        return map.get(response.getId());
                      });
                }), CONCURRENCY);
  }
}
