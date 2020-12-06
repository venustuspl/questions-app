package pl.stormit.ideas.validation.repository;

import org.springframework.data.repository.CrudRepository;
import pl.stormit.ideas.validation.domain.ForbiddenWord;

import java.util.UUID;

public interface ForbiddenWordRepository extends CrudRepository<ForbiddenWord, UUID> {
}
