package io.rsocket.springone.demo;

import io.netty.buffer.ByteBuf;
import io.r2dbc.postgresql.PostgresqlConnection;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.rsocket.springone.demo.Record.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DefaultRecordsService implements RecordsService {
  @Autowired
  private final RecordRepository recordRepository;

  public DefaultRecordsService(@Autowired RecordRepository recordRepository) {
      this.recordRepository = recordRepository;
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return this.recordRepository
            .findAllByThumbnailNotNull(request.getOffset(), request.getMaxResults())
            .map(DbRecord::toRecord);
  }
}
