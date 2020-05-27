package ru.kan.otus.batch.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Books")
public class BooksMongo {

    @Id
    private String id;

    private String title;
    private AuthorsMongo author;
    private GenresMongo genre;
}
