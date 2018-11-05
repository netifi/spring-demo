package io.rsocket.springone.demo;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.function.TupleUtils;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static reactor.function.TupleUtils.function;

@Component
public class DefaultRecordsService implements RecordsService {
  @Autowired
  private final RecordRepository recordRepository;

  private final Random r = new Random();

  private final Mono<Integer> total;

  public DefaultRecordsService(@Autowired RecordRepository recordRepository) {
      this.recordRepository = recordRepository;
	  this.total = recordRepository.count()
	                               .map(Long::intValue)
	                               .cache();
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return total
                .flatMapMany(c -> Flux
                    .<Tuple2<Long, Long>>create(s -> {
                        AtomicLong index = new AtomicLong();
                        s.onRequest(l -> s.next(Tuples.of(index.getAndAdd(l), l)));
                    })
                    .takeWhile(t2 -> t2.getT1() + t2.getT2() < c)
                    .concatMap(function(recordRepository::findAllByThumbnailNotNull), 1)
                )
                .map(DbRecord::toRecord)
                .log();

//    return this.recordRepository
//            .findAllByThumbnailNotNull(request.getOffset(), request.getMaxResults())
//            .map(DbRecord::toRecord);
  }

  int offset(RecordsRequest req, int count) {
    int max = count - req.getMaxResults();
    return r.nextInt(((max - req.getOffset()) + 1) + req.getOffset());
  }
}
