package pl.stormit.ideas.questions.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;
import pl.stormit.ideas.validation.ValidationManager;
import pl.stormit.ideas.validation.ValidationResult;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionStateValidator {
    private final ValidationManager validationManager;
    private final QuestionRepository questionRepository;

    public QuestionStateValidator(ValidationManager validationManager, QuestionRepository questionRepository) {
        this.validationManager = validationManager;
        this.questionRepository = questionRepository;
    }

    public void validateForAdding(Question question) {
        if (question.getId() != null) {
            throw new IllegalStateException("The Question cannot contain an ID");
        }

        ValidationResult validationResult = validationManager.validate(question);

        if (!validationResult.isValid()) {
            throw new IllegalArgumentException(buildValidationErrorMessage(validationResult.getError()));
        }
    }

    public void validateForUpdating(Question questionToUpdate) {
        validateIdPresence(questionToUpdate.getId(), "The Question to update must contain an ID");

        ValidationResult validationResult = validationManager.validate(questionToUpdate);

        if (!validationResult.isValid()) {
            throw new IllegalArgumentException(buildValidationErrorMessage(validationResult.getError()));
        }
    }

    public void validateForDeleting(Question question) {
        validateIdPresence(question.getId(), "The Question to delete must contain an ID");
    }

    private void validateIdPresence(UUID id, String message) {
        if (id == null) {
            throw new IllegalStateException(message);
        }
    }

    public String buildValidationErrorMessage(List<String> errors) {
        return errors.stream().collect(Collectors.joining(",", "Question is not valid because of: ", " ."));
    }
}
