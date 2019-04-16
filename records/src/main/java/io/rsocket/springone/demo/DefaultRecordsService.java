package io.rsocket.springone.demo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.netty.buffer.ByteBuf;
import io.rsocket.springone.demo.Record.Builder;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DefaultRecordsService implements RecordsService {

    private final List<Map> dataSet;

    public DefaultRecordsService(@Qualifier("dataset") List<Map> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Flux<Record> records(RecordsRequest request, ByteBuf metadata) {
        return Flux
            .<Integer, Integer>generate(
                () -> request.getOffset() >= dataSet.size() ? 0 : request.getOffset(),
                (index, sink) -> {
                    sink.next(index);
                    return ++index >= dataSet.size() ? 0 : index;
                }
            )
            .map(i -> toRecord(dataSet.get(i), i))
            .take(request.getMaxResults());
    }

    @SuppressWarnings("unchecked")
    private Record toRecord(Map row, int id) {
        Builder builder = Record.newBuilder();

        builder.setId(id);
        Optional.ofNullable(row.get("aliases"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllAliases);
        Optional.ofNullable(row.get("authors"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllAuthors);
        Optional.ofNullable(row.get("description"))
                .map(o -> (String) o)
                .ifPresent(builder::setDescription);
        Optional.ofNullable(row.get("background"))
                .map(o -> (String) o)
                .ifPresent(builder::setBackground);
        Optional.ofNullable(row.get("thumbnail"))
                .map(o -> (String) o)
                .ifPresent(builder::setThumbnail);
        Optional.ofNullable(row.get("name"))
                .map(o -> (String) o)
                .ifPresent(builder::setName);
        Optional.ofNullable(row.get("partners"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllPartners);
        Optional.ofNullable(row.get("powers"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllPowers);
        Optional.ofNullable(row.get("comic_count"))
                .map(o -> Integer.valueOf(o.toString()))
                .ifPresent(builder::setComicCount);
        Optional.ofNullable(row.get("event_count"))
                .map(o -> Integer.valueOf(o.toString()))
                .ifPresent(builder::setEventCount);
        Optional.ofNullable(row.get("pageview_count"))
                .map(o -> Integer.valueOf(o.toString()))
                .ifPresent(builder::setPageviewCount);
        Optional.ofNullable(row.get("serie_count"))
                .map(o -> Integer.valueOf(o.toString()))
                .ifPresent(builder::setSerieCount);
        Optional.ofNullable(row.get("story_count"))
                .map(o -> Integer.valueOf(o.toString()))
                .ifPresent(builder::setStoryCount);
        Optional.ofNullable(row.get("secret_identities"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllSecretIdentities);
        Optional.ofNullable(row.get("species"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllSpecies);
        Optional.ofNullable(row.get("supername"))
                .map(o -> (String) o)
                .ifPresent(builder::setSuperName);
        Optional.ofNullable(row.get("teams"))
                .map(o -> (Collection<String>) o)
                .ifPresent(builder::addAllTeams);
        Optional.ofNullable(row.get("marvel_url"))
                .map(o -> (String) o)
                .ifPresent(builder::setMarvelUrl);
        Optional.ofNullable(row.get("wikipedia_url"))
                .map(o -> (String) o)
                .ifPresent(builder::setWikipediaUrl);

        return builder.build();
    }
}
