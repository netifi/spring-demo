package io.rsocket.springone.demo;

import com.google.protobuf.util.JsonFormat;
import io.netty.buffer.ByteBuf;
import io.r2dbc.postgresql.PostgresqlConnection;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;

@Component
public class DefaultRecordsService implements RecordsService {
  private final        Mono<PostgresqlConnection> dbConnection;

  public DefaultRecordsService() {

	  PostgresqlConnectionFactory connectionFactory =
			  new PostgresqlConnectionFactory(
					  PostgresqlConnectionConfiguration.builder()
					                                   .host("localhost")
					                                   .username(System.getProperty("user.name"))
					                                   .password("")
					                                   .database("marvel")
					                                   .build()
			  );

	  this.dbConnection = connectionFactory.create();
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return this.dbConnection
		    .flatMapMany(connection -> connection.createStatement(
		    "SELECT * FROM records_txt ORDER BY id OFFSET "+request.getOffset()+
				    " LIMIT "+request.getMaxResults()).execute()
    )
		    .flatMap(result -> result.map((row, rowMetadata) -> {
			    try {
				    Data.Builder data = Data.newBuilder();
				    JsonFormat.parser()
				              .merge(row.get("data", String.class), data);

				    return Record.newBuilder()
				                 .setId(row.get("id", Integer.class))
				                 .setData(data)
				                 .build();
			    }
			    catch (Throwable t) {
				    throw Exceptions.propagate(t);
			    }
		    }));
  }
}
