package ru.kan.otus.batch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kan.otus.batch.domain.jpa.GenresSql;

public interface GenresSqlRepository extends JpaRepository<GenresSql, Long> {
}
