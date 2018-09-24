package io.rsocket.springone.demo;

import io.netty.buffer.ByteBuf;
import io.r2dbc.postgresql.PostgresqlConnection;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.rsocket.springone.demo.Record.Builder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DefaultRecordsService implements RecordsService {
  private final Mono<PostgresqlConnection> dbConnection;

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
            "SELECT * FROM records WHERE thumbnail is not null" +
                " ORDER BY id OFFSET " + request.getOffset() +
                " LIMIT " + request.getMaxResults()).execute())
        .flatMap(result -> result.map((row, rowMetadata) -> {
          Builder builder = Record.newBuilder();

          final Integer id = row.get("id", Integer.class);
          if (id != null) {
            builder.setId(id);
          }

          final String name = row.get("name", String.class);
          if (name != null) {
            builder.setName(name);
          }

          final String superName = row.get("supername", String.class);
          if (superName != null) {
            builder.setSuperName(superName);
          }

          final String description = row.get("description", String.class);
          if (description != null) {
            builder.setDescription(description);
          }

          final String background = row.get("background", String.class);
          if (background != null) {
            builder.setBackground(background);
          }

          final String thumbnail = row.get("thumbnail", String.class);
          if (thumbnail != null) {
            builder.setThumbnail(thumbnail);
          }

          final Integer comicCount = row.get("comiccount", Integer.class);
          if (comicCount != null) {
            builder.setComicCount(comicCount);
          }

          final Integer eventCount = row.get("eventcount", Integer.class);
          if (eventCount != null) {
            builder.setEventCount(eventCount);
          }

          final Integer pageviewCount = row.get("pageviewcount", Integer.class);
          if (pageviewCount != null) {
            builder.setPageviewCount(pageviewCount);
          }

          final Integer serieCount = row.get("seriecount", Integer.class);
          if (serieCount != null) {
            builder.setSerieCount(serieCount);
          }

          final Integer storyCount = row.get("storycount", Integer.class);
          if (storyCount != null) {
            builder.setStoryCount(storyCount);
          }

          final String marvelUrl = row.get("marvelurl", String.class);
          if (marvelUrl != null) {
            builder.setMarvelUrl(marvelUrl);
          }

          final String wikipediaUrl = row.get("wikipediaurl", String.class);
          if (wikipediaUrl != null) {
            builder.setWikipediaUrl(wikipediaUrl);
          }

          return builder.build();
        }));
  }
}
