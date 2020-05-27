package ru.kan.otus.batch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kan.otus.batch.domain.mongo.BooksMongo;

public interface BooksMongoRepository extends MongoRepository<BooksMongo, String> {

}
