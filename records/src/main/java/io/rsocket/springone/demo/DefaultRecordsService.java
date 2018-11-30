package io.rsocket.springone.demo;

import io.netty.buffer.ByteBuf;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.rsocket.springone.demo.Record.Builder;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class DefaultRecordsService implements RecordsService {
  private final PostgresqlConnectionFactory connectionFactory;

  public DefaultRecordsService(@Autowired PostgresqlConnectionFactory connectionFactory) {
      this.connectionFactory = connectionFactory;
  }

  @Override
  public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
    return connectionFactory.create()
        .flatMapMany(connection ->
          connection
              .createStatement("SELECT * FROM records WHERE thumbnail is not null ORDER BY id OFFSET $1 LIMIT $2")
              .bind(0, request.getOffset())
              .bind(1, request.getMaxResults())
              .execute()
              .flatMap(result -> result.map(this::toRecord)))
        .onBackpressureBuffer();
  }

  private Record toRecord(Row row, RowMetadata rowMetadata) {
    Builder builder = Record.newBuilder();
    Optional.ofNullable(row.get("id", Integer.class))
        .ifPresent(builder::setId);
    Optional.ofNullable(row.get("aliases", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllAliases);
    Optional.ofNullable(row.get("authors", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllAuthors);
    Optional.ofNullable(row.get("description", String.class))
        .ifPresent(builder::setDescription);
    Optional.ofNullable(row.get("background", String.class))
        .ifPresent(builder::setBackground);
    Optional.ofNullable(row.get("thumbnail", String.class))
        .ifPresent(builder::setThumbnail);
    Optional.ofNullable(row.get("name", String.class))
        .ifPresent(builder::setName);
    Optional.ofNullable(row.get("partners", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllPartners);
    Optional.ofNullable(row.get("powers", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllPowers);
    Optional.ofNullable(row.get("comic_count", Integer.class))
        .ifPresent(builder::setComicCount);
    Optional.ofNullable(row.get("event_count", Integer.class))
        .ifPresent(builder::setEventCount);
    Optional.ofNullable(row.get("pageview_count", Integer.class))
        .ifPresent(builder::setPageviewCount);
    Optional.ofNullable(row.get("serie_count", Integer.class))
        .ifPresent(builder::setSerieCount);
    Optional.ofNullable(row.get("story_count", Integer.class))
        .ifPresent(builder::setStoryCount);
    Optional.ofNullable(row.get("secret_identities", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllSecretIdentities);
    Optional.ofNullable(row.get("species", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllSpecies);
    Optional.ofNullable(row.get("supername", String.class))
        .ifPresent(builder::setSuperName);
    Optional.ofNullable(row.get("teams", String[].class))
        .map(Arrays::asList)
        .ifPresent(builder::addAllTeams);
    Optional.ofNullable(row.get("marvel_url", String.class))
        .ifPresent(builder::setMarvelUrl);
    Optional.ofNullable(row.get("wikipedia_url", String.class))
        .ifPresent(builder::setWikipediaUrl);
    return builder.build();
  }
}
