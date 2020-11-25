package pl.stormit.ideas.answers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.validation.ValidationManager;
import pl.stormit.ideas.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnswerValidatorTest {
    private AnswerValidator answerValidator;
    private final AnswerStateValidator answerStateValidator = mock(AnswerStateValidator.class);
    private final ValidationManager validationManager = mock(ValidationManager.class);

    @BeforeEach
    void setUp() {
        answerValidator = new AnswerValidator(answerStateValidator, validationManager);
    }

    @Test
    void shouldNotThrowExceptionDuringValidatingForAddingWhenBodyValidationPassed() {
        //given
        Answer answer = new Answer();
        ValidationResult validationResult = new ValidationResult(true, new ArrayList<>());
        when(validationManager.validate(any(Answer.class))).thenReturn(validationResult);

        //when
        //then
        assertThatCode(() -> answerValidator.validateForAdding(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringValidatingForAddingWhenBodyValidationFailed() {
        //given
        Answer answer = new Answer();
        ValidationResult validationResult = new ValidationResult(false, Arrays.asList("length", "words"));
        when(validationManager.validate(any(Answer.class))).thenReturn(validationResult);

        //when
        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> answerValidator.validateForAdding(answer))
                .withMessage("Body validation failed: length, words");
    }

    @Test
    void shouldNotThrowExceptionDuringValidatingForUpdatingWhenBodyValidationPassed() {
        //given
        Answer answer = new Answer();
        ValidationResult validationResult = new ValidationResult(true, new ArrayList<>());
        when(validationManager.validate(any(Answer.class))).thenReturn(validationResult);

        //when
        //then
        assertThatCode(() -> answerValidator.validateForUpdating(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringValidatingForUpdatingWhenBodyValidationFailed() {
        //given
        Answer answer = new Answer();
        ValidationResult validationResult = new ValidationResult(false, Arrays.asList("length", "words"));
        when(validationManager.validate(any(Answer.class))).thenReturn(validationResult);

        //when
        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> answerValidator.validateForUpdating(answer))
                .withMessage("Body validation failed: length, words");
    }
}