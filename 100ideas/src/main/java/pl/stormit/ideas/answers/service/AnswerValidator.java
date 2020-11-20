package pl.stormit.ideas.answers.service;

import org.springframework.stereotype.Service;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.validation.ValidationManager;
import pl.stormit.ideas.validation.ValidationResult;

@Service
public class AnswerValidator {
    private final AnswerStateValidator answerStateValidator;
    private final ValidationManager validationManager;

    public AnswerValidator(AnswerStateValidator answerStateValidator, ValidationManager validationManager) {
        this.answerStateValidator = answerStateValidator;
        this.validationManager = validationManager;
    }

    public void validateForAdding(Answer answer) {
        answerStateValidator.validateForAdding(answer);
        validateBody(answer);
    }

    public void validateForUpdating(Answer answer) {
        answerStateValidator.validateForUpdating(answer);
        validateBody(answer);
    }

    public void validateForDeleting(Answer answer) {
        answerStateValidator.validateForDeleting(answer);
    }

    private void validateBody(Answer answer) {
        ValidationResult validationResult = validationManager.validate(answer);
        if (!validationResult.isValid()) {
            String errors = String.join(", ", validationResult.getError());
            throw new IllegalArgumentException("Body validation failed: " + errors);
        }
    }
}
