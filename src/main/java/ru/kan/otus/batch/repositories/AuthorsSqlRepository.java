package ru.kan.otus.batch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kan.otus.batch.domain.jpa.AuthorsSql;

public interface AuthorsSqlRepository extends JpaRepository<AuthorsSql, Long> {
}
