
package pl.stormit.ideas.questions.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;
import pl.stormit.ideas.validation.ValidationManager;
import pl.stormit.ideas.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForAdding(question));

        //then
        assertThat(throwable).hasMessage("The Question cannot contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenQuestionHasId() {
        //given
        Question question = getQuestionWithId();
        ValidationResult validationResult = new ValidationResult(true, Arrays.asList(""));
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForUpdating(question));

        //then
        assertThat(throwable).doesNotThrowAnyException();

    }

    @Test
    void shouldThrowExceptionDuringUpdatingWhenQuestionHasNoId() {
        //given
        Question question = new Question();

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForUpdating(question));

        //then
        assertThat(throwable).hasMessage("The Question to update must contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenQuestionHasId() {
        //given
        Question question = getQuestionWithId();

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForDeleting(question));

        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringDeletingWhenQuestionHasNoId() {
        //given
        Question question = new Question();

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForDeleting(question));

        //then
        assertThat(throwable)
                .hasMessage("The Question to delete must contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringValidatingForAddingWhenNameValidationPassed() {
        //given
        Question question = new Question();
        ValidationResult validationResult = new ValidationResult(true, new ArrayList<>());
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForAdding(question));

        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringValidatingForAddingWhenNameValidationFailed() {
        //given
        Question question = new Question();
        ValidationResult validationResult = new ValidationResult(false, Arrays.asList("length", "words"));
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForAdding(question));

        //then
        assertThat(throwable).hasMessage("Question is not valid because of: length,words .");
    }

    @Test
    void shouldNotThrowExceptionDuringValidatingForUpdatingWhenNameValidationPassed() {
        //given
        Question question = getQuestionWithId();
        ValidationResult validationResult = new ValidationResult(true, new ArrayList<>());
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForUpdating(question));

        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringValidatingForUpdatingWhenNameValidationFailed() {
        //given
        Question answer = getQuestionWithId();
        ValidationResult validationResult = new ValidationResult(false, Arrays.asList("length", "words"));
        when(validationManager.validate(any(Question.class))).thenReturn(validationResult);

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionStateValidator.validateForUpdating(answer));

        //then
        assertThat(throwable)
                .hasMessage("Question is not valid because of: length,words .");
    }


    private Question getQuestionWithId() {
        Question question = new Question();
        question.setId(UUID.randomUUID());
        question.setName("Test name");
        return question;
    }

}

