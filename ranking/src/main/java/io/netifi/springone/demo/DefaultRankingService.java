package io.netifi.springone.demo;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;

@Component
public class DefaultRankingService implements RankingService {
  private static final Logger logger = LogManager.getLogger(DefaultRankingService.class);
  private final Comparator<Record> comparator =
      Comparator.comparingInt(record -> record.getData().getRanking().getStoryCount());

  @Override
  public Mono<RankingResponse> rank(RankingRequest request, ByteBuf metadata) {
    Record record = Collections.max(request.getRecordsList(), comparator);
    return Mono.just(RankingResponse.newBuilder()
        .setId(record.getId())
        .build());
  }
}
