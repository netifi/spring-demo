package io.rsocket.springone.demo;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultRecordsService implements RecordsService {
  @Autowired
  private final RecordRepository recordRepository;

  private final Random r = new Random();

  public DefaultRecordsService(@Autowired RecordRepository recordRepository) {
      this.recordRepository = recordRepository;
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return recordRepository.count()
                    .map(Long::intValue)
                    .flatMapMany(c ->
                            this.recordRepository.findAllByThumbnailNotNull(offset(request, c), request.getMaxResults())
                                                 .map(DbRecord::toRecord));
//    return this.recordRepository
//            .findAllByThumbnailNotNull(request.getOffset(), request.getMaxResults())
//            .map(DbRecord::toRecord);
  }

  int offset(RecordsRequest req, int count) {
    int max = count - req.getMaxResults();
    return r.nextInt(((max - req.getOffset()) + 1) + req.getOffset());
  }
}
