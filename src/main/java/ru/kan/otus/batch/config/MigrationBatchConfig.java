package ru.kan.otus.batch.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.kan.otus.batch.domain.jpa.AuthorsSql;
import ru.kan.otus.batch.domain.jpa.BooksSql;
import ru.kan.otus.batch.domain.jpa.GenresSql;
import ru.kan.otus.batch.domain.mongo.AuthorsMongo;
import ru.kan.otus.batch.domain.mongo.BooksMongo;
import ru.kan.otus.batch.domain.mongo.GenresMongo;
import ru.kan.otus.batch.repositories.AuthorsSqlRepository;
import ru.kan.otus.batch.repositories.BooksSqlRepository;
import ru.kan.otus.batch.repositories.GenresSqlRepository;

import java.util.HashMap;

@RequiredArgsConstructor
@EnableBatchProcessing
@Configuration
public class MigrationBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final GenresSqlRepository genreRepo;
    private final AuthorsSqlRepository authorRepo;
    private final BooksSqlRepository bookRepo;
    private final ModelMapper mapper;

    @Bean
    public Job migrateDataJob() {
        return jobBuilderFactory.get("importDataJob")
                .start(genresImportStep(genreReader(), genreProcessor(), genreWriter()))
                .next(authorImportStep(authorReader(), authorProcessor(), authorWriter()))
                .next(bookImportStep(bookReader(), bookProcessor(), bookWriter()))
                .build();
    }

    @Bean
    public ItemReader<GenresSql> genreReader() {
        return new RepositoryItemReaderBuilder<GenresSql>()
                .name("genreReader")
                .pageSize(20)
                .sorts(new HashMap<String, Sort.Direction>())
                .repository(genreRepo)
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemReader<AuthorsSql> authorReader() {
        return new RepositoryItemReaderBuilder<AuthorsSql>()
                .name("authorReader")
                .pageSize(20)
                .sorts(new HashMap<String, Sort.Direction>())
                .repository(authorRepo)
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemReader<BooksSql> bookReader() {
        return new RepositoryItemReaderBuilder<BooksSql>()
                .name("bookReader")
                .pageSize(20)
                .sorts(new HashMap<String, Sort.Direction>())
                .repository(bookRepo)
                .methodName("findAll")
                .build();
    }

    @Bean
    public ItemProcessor<? super Object, ? extends Object> genreProcessor() {
        return genre -> mapper.map(genre, GenresMongo.class);
    }

    @Bean
    public ItemProcessor<? super Object, ? extends Object> bookProcessor() {
        return book -> mapper.map(book, BooksMongo.class);
    }

    @Bean
    public ItemProcessor<? super Object, ? extends Object> authorProcessor() {
        return author -> mapper.map(author, AuthorsMongo.class);
    }

    @Bean
    public ItemWriter<GenresMongo> genreWriter() {
        return new MongoItemWriterBuilder<GenresMongo>()
                .collection("genres")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public ItemWriter<AuthorsMongo> authorWriter() {
        return new MongoItemWriterBuilder<AuthorsMongo>()
                .collection("authors")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public ItemWriter<BooksMongo> bookWriter() {
        return new MongoItemWriterBuilder<BooksMongo>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step genresImportStep(ItemReader<GenresSql> reader, ItemProcessor<? super Object, ? extends Object> itemProcessor, ItemWriter writer) {
        return stepBuilderFactory.get("genresImportStep")
                .chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step authorImportStep(ItemReader<AuthorsSql> reader, ItemProcessor<? super Object, ? extends Object> itemProcessor, ItemWriter writer) {
        return stepBuilderFactory.get("authorsImportStep")
                .chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step bookImportStep(ItemReader<BooksSql> reader, ItemProcessor<? super Object, ? extends Object> itemProcessor, ItemWriter writer) {
        return stepBuilderFactory.get("booksImportStep")
                .chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}