package pl.stormit.ideas.answers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.ideas.answers.domain.Answer;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, UUID> {
    List<Answer> findAll();
}
