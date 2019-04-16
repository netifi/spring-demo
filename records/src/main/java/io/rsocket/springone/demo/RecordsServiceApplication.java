package io.rsocket.springone.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecordsServiceApplication {
  public static void main(String... args) {
    SpringApplication.run(RecordsServiceApplication.class, args);
  }

  @Bean
  @Qualifier("dataset")
  public List<Map> dataSet(ApplicationContext applicationContext) throws IOException {
// Manually-built schema: one with type, others default to "STRING"
    CsvSchema schema = CsvSchema
        .builder()
        .setNullValue("")
        .addColumn(new CsvSchema.Column(0, "aliases", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn(new CsvSchema.Column(1, "authors", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn("description", CsvSchema.ColumnType.STRING)
        .addColumn("background", CsvSchema.ColumnType.STRING)
        .addColumn("thumbnail", CsvSchema.ColumnType.STRING)
        .addColumn("name", CsvSchema.ColumnType.STRING)
        .addColumn(new CsvSchema.Column(6, "partners", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn(new CsvSchema.Column(7, "powers", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn("comic_count", CsvSchema.ColumnType.NUMBER)
        .addColumn("event_count", CsvSchema.ColumnType.NUMBER)
        .addColumn("pageview_count", CsvSchema.ColumnType.NUMBER)
        .addColumn("serie_count", CsvSchema.ColumnType.NUMBER)
        .addColumn("story_count", CsvSchema.ColumnType.NUMBER)
        .addColumn(new CsvSchema.Column(13, "secret_identities", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn(new CsvSchema.Column(14, "species", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn("supername", CsvSchema.ColumnType.STRING)
        .addColumn(new CsvSchema.Column(16, "teams", CsvSchema.ColumnType.ARRAY, ","))
        .addColumn("marvel_url", CsvSchema.ColumnType.STRING)
        .addColumn("wikipedia_url", CsvSchema.ColumnType.STRING)
        .build();

// Read schema from the first line; start with bootstrap instance
// to enable reading of schema from the first line
// NOTE: reads schema and uses it for binding
//    CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().;
    ObjectMapper mapper = new CsvMapper();
    MappingIterator<Map> mappingIterator = mapper.readerFor(HashMap.class)
                                    .with(schema)
                                    .readValues(applicationContext.getResource("records.csv")
                                       .getFile());

    List<Map> maps = mappingIterator.readAll();

    return maps;
  }
}
