package app.repository;

import app.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {
    Optional<PostEntity> findByTitle(String name);
    List<PostEntity> findAllByCreationDate(LocalDate date);
}
