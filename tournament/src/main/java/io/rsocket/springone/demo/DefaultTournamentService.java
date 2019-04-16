package io.rsocket.springone.demo;

import java.math.RoundingMode;

import com.google.common.math.IntMath;
import io.netifi.proteus.spring.core.annotation.Group;
import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import org.springframework.stereotype.Service;

@Service
public class DefaultTournamentService implements TournamentService {
  private static final Logger logger = LogManager.getLogger(DefaultTournamentService.class);
  private static final int WINDOW_SIZE = 2;

  @Group("springone.demo.records")
  private RecordsServiceClient recordsService;

  @Group("springone.demo.ranking")
  private RankingServiceClient rankingService;

  @Override
  public Flux<RoundResult> tournament(RecordsRequest request, ByteBuf metadata) {
    int maxRounds = IntMath.log2(request.getMaxResults(), RoundingMode.UP);
    return tournament(recordsService.records(request), 1, maxRounds);
  }

  private Flux<RoundResult> tournament(Flux<Record> records, int round, int maxRounds) {
    ConnectableFlux<Record> winners = round(records).publish();
    Flux<RoundResult> result = winners
        .map(winner -> RoundResult.newBuilder()
            .setRound(round)
            .setWinner(winner)
            .build());


    Flux<RoundResult> tournamentResult = (round < maxRounds)
      ? Flux.just(result, Flux.defer(() -> tournament(winners, round + 1, maxRounds)))
            .flatMap(flux -> flux.subscribeOn(Schedulers.parallel()))
      : result;

    winners.connect();

    return tournamentResult;
  }

  private Flux<Record> round(Flux<Record> records) {
    return records
        .buffer(WINDOW_SIZE)
        .map(buffer -> RankingRequest.newBuilder().addAllRecords(buffer).build())
        .transform(rankingService::rank);
  }
}
