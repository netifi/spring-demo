package io.netifi.springone.demo;

import com.google.protobuf.util.JsonFormat;
import io.netty.buffer.ByteBuf;
import io.reactivex.exceptions.Exceptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.DatabaseType;
import org.springframework.stereotype.Component;
import reactor.adapter.rxjava.RxJava2Adapter;
import reactor.core.publisher.Flux;
import reactor.util.concurrent.Queues;

import java.util.concurrent.TimeUnit;

@Component
public class DefaultRecordsService implements RecordsService {
  private static final Logger logger = LogManager.getLogger(DefaultRecordsService.class);
  private final Database db;

  public DefaultRecordsService() {
    this.db = Database
        .nonBlocking()
        // the jdbc url of the connections to be placed in the pool
        .url("jdbc:postgresql:rdegnan")
        // an unused connection will be closed after thirty minutes
        .maxIdleTime(30, TimeUnit.MINUTES)
        // connections are checked for healthiness on checkout if the connection
        // has been idle for at least 5 seconds
        .healthCheck(DatabaseType.POSTGRES)
        .idleTimeBeforeHealthCheck(5, TimeUnit.SECONDS)
        // if a connection fails creation then retry after 30 seconds
        .connectionRetryInterval(30, TimeUnit.SECONDS)
        // the maximum number of connections in the pool
        .maxPoolSize(Runtime.getRuntime().availableProcessors())
        .build();
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return RxJava2Adapter.flowableToFlux(db
        .select("SELECT * FROM records WHERE (data -> 'images' ->> 'thumbnail') is not null\n" +
                "ORDER BY id OFFSET " + request.getOffset() + " LIMIT " + request.getMaxResults())
        .fetchSize(Queues.XS_BUFFER_SIZE)
        .get(result -> {
          try {
            Data.Builder data = Data.newBuilder();
            JsonFormat.parser().merge(result.getString("data"), data);

            return Record.newBuilder()
                .setId(result.getInt("id"))
                .setData(data)
                .build();
          } catch (Throwable t) {
            throw Exceptions.propagate(t);
          }
        }));
  }
}
