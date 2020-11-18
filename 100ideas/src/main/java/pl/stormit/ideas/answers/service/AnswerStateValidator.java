package pl.stormit.ideas.answers.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnswerStateValidator {
    private final QuestionRepository questionRepository;

    public AnswerStateValidator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void validateForAdding(Answer answer) {
        if (answer.getId() != null) {
            throw new IllegalStateException("The Answer to add cannot contain an ID");
        }
        if (answer.getQuestion() == null) {
            throw new IllegalStateException("The Answer to add must contain the Question object");
        }
        UUID questionId = answer.getQuestion().getId();
        validateIdPresence(questionId, "The Answer to add must contain the Question object with ID");
        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new NoSuchElementException("The Question object with id " + questionId + " does not exist in DB");
        }
    }

    public void validateForUpdating(Answer answer) {
        validateIdPresence(answer.getId(), "The Answer to update must contain an ID");
    }

    public void validateForDeleting(Answer answer) {
        validateIdPresence(answer.getId(), "The Answer to delete must contain an ID");
    }

    private void validateIdPresence(UUID id, String message) {
        if (id == null) {
            throw new IllegalStateException(message);
        }
    }
}
