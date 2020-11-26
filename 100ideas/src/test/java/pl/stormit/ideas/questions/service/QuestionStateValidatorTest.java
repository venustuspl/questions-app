
package pl.stormit.ideas.questions.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;
import pl.stormit.ideas.validation.ValidationManager;
import pl.stormit.ideas.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionStateValidatorTest {

    private final ValidationManager validationManager = mock(ValidationManager.class);
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);
    private QuestionStateValidator questionStateValidator;

    @BeforeEach
    void setUp() {
        questionStateValidator = new QuestionStateValidator(validationManager, questionRepository);
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenQuestionHasId() {
        //given
        Question question = getQuestionWithId();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> questionStateValidator.validateForAdding(question))
                .withMessage("The Question cannot contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenQuestionHasId() {
        //given
        Question question = getQuestionWithId();
        ValidationResult validationResult = new ValidationResult(true, Arrays.asList(""));
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        //then
        assertThatCode(() -> questionStateValidator.validateForUpdating(question)).doesNotThrowAnyException();

    }

    @Test
    void shouldThrowExceptionDuringUpdatingWhenQuestionHasNoId() {
        //given
        Question question = new Question();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> questionStateValidator.validateForUpdating(question))
                .withMessage("The Question to update must contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenQuestionHasId() {
        //given
        Question question = getQuestionWithId();

        //when
        //then
        assertThatCode(() -> questionStateValidator.validateForDeleting(question))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringDeletingWhenQuestionHasNoId() {
        //given
        Question question = new Question();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> questionStateValidator.validateForDeleting(question))
                .withMessage("The Question to delete must contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringValidatingForAddingWhenNameValidationPassed() {
        //given
        Question question = new Question();
        ValidationResult validationResult = new ValidationResult(true, new ArrayList<>());
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        //then
        assertThatCode(() -> questionStateValidator.validateForAdding(question))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringValidatingForAddingWhenNameValidationFailed() {
        //given
        Question question = new Question();
        ValidationResult validationResult = new ValidationResult(false, Arrays.asList("length", "words"));
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> questionStateValidator.validateForAdding(question))
                .withMessage("Question is not valid because of: length,words .");
    }

    @Test
    void shouldNotThrowExceptionDuringValidatingForUpdatingWhenNameValidationPassed() {
        //given
        Question question = getQuestionWithId();
        ValidationResult validationResult = new ValidationResult(true, new ArrayList<>());
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        //then
        assertThatCode(() -> questionStateValidator.validateForUpdating(question))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringValidatingForUpdatingWhenNameValidationFailed() {
        //given
        Question answer = getQuestionWithId();
        ValidationResult validationResult = new ValidationResult(false, Arrays.asList("length", "words"));
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> questionStateValidator.validateForUpdating(answer))
                .withMessage("Question is not valid because of: length,words .");
    }


    private Question getQuestionWithId() {
        Question question = new Question();
        question.setId(UUID.randomUUID());
        question.setName("Test name");
        return question;
    }

}

