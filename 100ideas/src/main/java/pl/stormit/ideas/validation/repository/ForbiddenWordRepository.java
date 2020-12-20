package pl.stormit.ideas.validation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.ideas.validation.domain.ForbiddenWord;

import java.util.List;
import java.util.UUID;

@Repository
public interface ForbiddenWordRepository extends JpaRepository<ForbiddenWord, UUID> {
}