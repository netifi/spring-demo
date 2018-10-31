package io.rsocket.springone.demo;

import java.util.Collections;
import java.util.Comparator;

import io.netty.buffer.ByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DefaultRankingService implements RankingService {
  private static final Logger logger = LogManager.getLogger(DefaultRankingService.class);
  private final Comparator<Record> comparator = Comparator.comparingInt(Record::getStoryCount);

  @Override
  public Flux<Record> rank(Publisher<RankingRequest> requestStream, ByteBuf metadata) {
    return Flux.from(requestStream)
               .map(request -> Collections.max(request.getRecordsList(), comparator))
               .log();
  }
}
