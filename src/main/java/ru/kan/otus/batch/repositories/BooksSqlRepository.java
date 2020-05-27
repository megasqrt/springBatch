package ru.kan.otus.batch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kan.otus.batch.domain.jpa.BooksSql;

public interface BooksSqlRepository extends JpaRepository<BooksSql, Long> {
}
