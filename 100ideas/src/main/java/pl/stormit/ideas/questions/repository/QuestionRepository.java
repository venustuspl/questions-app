package pl.stormit.ideas.questions.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.ideas.questions.domain.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends CrudRepository<Question, UUID> {
    List<Question> findAllById(UUID questionId);

    List<Question> findAllByName(String name);

    List<Question> findAll();

    List<Question> findAllByCategoryIdOrderByCreationDateDesc(UUID categoryId);

    long countByName(String name);
}