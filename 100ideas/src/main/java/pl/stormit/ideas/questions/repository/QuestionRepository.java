package pl.stormit.ideas.questions.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.ideas.questions.domain.Question;

import java.util.UUID;

@Repository
public interface QuestionRepository extends CrudRepository<Question, UUID> {

}