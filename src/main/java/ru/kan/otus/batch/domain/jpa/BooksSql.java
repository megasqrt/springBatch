package ru.kan.otus.batch.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKS")
@NamedEntityGraph(name = "authorAndGenre-eg", attributeNodes = {
        @NamedAttributeNode("author"),
        @NamedAttributeNode("genre")
})
public class BooksSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @OneToOne(targetEntity = AuthorsSql.class)
    @JoinColumn(name = "author_id")
    private AuthorsSql author;

    @OneToOne(targetEntity = GenresSql.class)
    @JoinColumn(name = "genre_id")
    private GenresSql genre;
}
