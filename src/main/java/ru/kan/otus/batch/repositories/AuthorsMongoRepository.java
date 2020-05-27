package ru.kan.otus.batch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kan.otus.batch.domain.mongo.AuthorsMongo;

public interface AuthorsMongoRepository extends MongoRepository<AuthorsMongo, String> {

}
