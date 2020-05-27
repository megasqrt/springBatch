package ru.kan.otus.batch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kan.otus.batch.domain.mongo.GenresMongo;

public interface GenresMongoRepository extends MongoRepository<GenresMongo, String> {

}
