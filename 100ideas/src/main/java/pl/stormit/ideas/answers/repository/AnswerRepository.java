package pl.stormit.ideas.answers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.stormit.ideas.answers.domain.Answer;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, UUID> {
    List<Answer> findAllByQuestionIdOrderByCreationDateDesc(UUID questionId);
}
