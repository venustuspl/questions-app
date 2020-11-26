package pl.stormit.ideas.questions.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;
import pl.stormit.ideas.validation.ValidationManager;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionServiceTest {
    private QuestionService questionService;
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);
    private final ValidationManager validationManager = mock(ValidationManager.class);
    private final QuestionStateValidator questionStateValidator = mock(QuestionStateValidator.class);

    @BeforeEach
    void setUp() {
        questionService = new QuestionService(questionRepository, validationManager, questionStateValidator);
    }

    @Test
    void shouldReturnQuestionWithTheSameCreationDateDuringAddingWhenCreationDateIsPresent() {
        //given
        OffsetDateTime time = OffsetDateTime.now();
        Question question = new Question();
        question.setCreationDate(time);
        when(questionRepository.save(any(Question.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Question addedQuestion = questionService.addQuestion(question);

        //then
        assertThat(addedQuestion.getCreationDate()).isEqualTo(time);
    }

    @Test
    void shouldReturnQuestionWithCreationDateDuringAddingWhenCreationDateIsNotPresent() {
        //given
        Question Question = new Question();
        when(questionRepository.save(any(Question.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Question addedQuestion = questionService.addQuestion(Question);

        //then
        assertThat(addedQuestion.getCreationDate())
                .isNotNull()
                .isExactlyInstanceOf(OffsetDateTime.class);
    }

    @Test
    void shouldThrowExceptionDuringUpdatingWhenMoreThanSixtySecondsHavePassedSinceCreation() {
        //given
        Question question = getQuestionWithSecondsSinceCreation(75L);
        when(questionRepository.findById(any())).thenReturn(Optional.of(question));

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionService.updateQuestion(question));

        //then
        assertThat(throwable).hasMessage("The Question is too old to be updated");
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenLessThanSixtySecondsHavePassedSinceCreation() {
        //given
        Question question = getQuestionWithSecondsSinceCreation(55L);
        when(questionRepository.findById(any())).thenReturn(Optional.of(question));

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionService.updateQuestion(question));

        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenSixtySecondsHavePassedSinceCreation() {
        //given
        Question question = getQuestionWithSecondsSinceCreation(60L);
        when(questionRepository.findById(any())).thenReturn(Optional.of(question));

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionService.updateQuestion(question));

        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringDeletingWhenMoreThanSixtySecondsHavePassedSinceCreation() {
        //given
        Question question = getQuestionWithSecondsSinceCreation(75L);
        when(questionRepository.findById(any())).thenReturn(Optional.of(question));

        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionService.deleteQuestion(question));

        //then
        assertThat(throwable)
                .hasMessage("The Question is too old to be deleted");
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenLessThanSixtySecondsHavePassedSinceCreation() {
        //given
        Question question = getQuestionWithSecondsSinceCreation(55L);
        when(questionRepository.findById(any())).thenReturn(Optional.of(question));

        //when
        Throwable throwable = Assertions.catchThrowable(()  -> questionService.deleteQuestion(question));

        //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenSixtySecondsHavePassedSinceCreation() {
        //given
        Question question = getQuestionWithSecondsSinceCreation(60L);
        when(questionRepository.findById(any())).thenReturn(Optional.of(question));

        //when
        Throwable throwable = Assertions.catchThrowable(()  -> questionService.deleteQuestion(question));

                //then
        assertThat(throwable)
                .doesNotThrowAnyException();
    }

    private Question getQuestionWithSecondsSinceCreation(long seconds) {
        Question question = new Question();
        question.setCreationDate(OffsetDateTime.now().minusSeconds(seconds));
        return question;
    }
}