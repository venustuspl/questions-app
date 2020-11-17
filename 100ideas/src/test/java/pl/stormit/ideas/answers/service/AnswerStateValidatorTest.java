package pl.stormit.ideas.answers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnswerStateValidatorTest {
    private AnswerStateValidator answerStateValidator;
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);

    @BeforeEach
    void setUp() {
        answerStateValidator = new AnswerStateValidator(questionRepository);
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenAnswerHasId() {
        //given
        Answer answer = getAnswerWithId();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> answerStateValidator.validateForAdding(answer))
                .withMessage("The Answer to add cannot contain an ID");
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenAnswerHasNoQuestion() {
        //given
        Answer answer = new Answer();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> answerStateValidator.validateForAdding(answer))
                .withMessage("The Answer to add must contain the Question object");
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenAnswerHasQuestionWithoutId() {
        //given
        Answer answer = new Answer();
        answer.setQuestion(new Question());

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> answerStateValidator.validateForAdding(answer))
                .withMessage("The Answer to add must contain the Question object with ID");
    }

    @Test
    void shouldThrowExceptionDuringAddingWhenAnswerHasQuestionThatIsNotInDB() {
        //given
        Answer answer = getAnswerWithQuestion();
        when(questionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        //when
        //then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> answerStateValidator.validateForAdding(answer))
                .withMessageContaining("The Question object with id ");
    }

    @Test
    void shouldNotThrowExceptionDuringAddingWhenAnswerHasNoIdAndHasQuestionThatIsInDB() {
        //given
        Answer answer = getAnswerWithQuestion();
        when(questionRepository.findById(any(UUID.class))).thenReturn(Optional.of(answer.getQuestion()));

        //when
        //then
        assertThatCode(() -> answerStateValidator.validateForAdding(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenAnswerHasId() {
        //given
        Answer answer = getAnswerWithId();

        //when
        //then
        assertThatCode(() -> answerStateValidator.validateForUpdating(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringUpdatingWhenAnswerHasNoId() {
        //given
        Answer answer = new Answer();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> answerStateValidator.validateForUpdating(answer))
                .withMessage("The Answer to update must contain an ID");
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenAnswerHasId() {
        //given
        Answer answer = getAnswerWithId();

        //when
        //then
        assertThatCode(() -> answerStateValidator.validateForDeleting(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringDeletingWhenAnswerHasNoId() {
        //given
        Answer answer = new Answer();

        //when
        //then
        assertThatIllegalStateException()
                .isThrownBy(() -> answerStateValidator.validateForDeleting(answer))
                .withMessage("The Answer to delete must contain an ID");
    }

    private Answer getAnswerWithId() {
        Answer answer = new Answer();
        answer.setId(UUID.randomUUID());
        return answer;
    }

    private Answer getAnswerWithQuestion() {
        Answer answer = new Answer();
        Question question = new Question();
        question.setId(UUID.randomUUID());
        answer.setQuestion(question);
        return answer;
    }
}