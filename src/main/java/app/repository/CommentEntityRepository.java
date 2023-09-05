package app.repository;

import app.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CommentEntityRepository extends JpaRepository<CommentEntity, Integer> {
    Optional<CommentEntity> findByCreationDate(LocalDate date);
}
