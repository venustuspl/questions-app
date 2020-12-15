package pl.stormit.ideas.answers.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.answers.service.validator.AnswerValidator;
import pl.stormit.ideas.questions.domain.Question;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnswerServiceTest {
    private AnswerService answerService;
    private final AnswerRepository answerRepository = mock(AnswerRepository.class);
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);
    private final AnswerValidator answerValidator = mock(AnswerValidator.class);

    @BeforeEach
    void setUp() {
        answerService = new AnswerService(answerRepository, questionRepository, answerValidator);
    }

    @Test
    void shouldReturnAnswerWithTheSameCreationDateDuringAddingWhenCreationDateIsPresent() {
        //given
        OffsetDateTime time = OffsetDateTime.now();
        Answer answer = new Answer();
        answer.setCreationDate(time);
        when(answerRepository.save(any(Answer.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Answer addedAnswer = answerService.addAnswer(answer);

        //then
        assertThat(addedAnswer.getCreationDate()).isEqualTo(time);
    }

    @Test
    void shouldReturnAnswerWithCreationDateDuringAddingWhenCreationDateIsNotPresent() {
        //given
        Answer answer = new Answer();
        when(answerRepository.save(any(Answer.class))).then(AdditionalAnswers.returnsFirstArg());

        //when
        Answer addedAnswer = answerService.addAnswer(answer);

        //then
        assertThat(addedAnswer.getCreationDate())
                .isNotNull()
                .isExactlyInstanceOf(OffsetDateTime.class);
    }

    @Test
    void shouldThrowExceptionDuringUpdatingWhenMoreThanSixtySecondsHavePassedSinceCreation() {
        //given
        Answer answer = getAnswerWithSecondsSinceCreation(75L);
        when(answerRepository.findById(any())).thenReturn(Optional.of(answer));

        //when
        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> answerService.updateAnswer(answer))
                .withMessage("The Answer is too old to be updated");
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenLessThanSixtySecondsHavePassedSinceCreation() {
        //given
        Answer answer = getAnswerWithSecondsSinceCreation(55L);
        when(answerRepository.findById(any())).thenReturn(Optional.of(answer));

        //when
        //then
        assertThatCode(() -> answerService.updateAnswer(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowExceptionDuringUpdatingWhenSixtySecondsHavePassedSinceCreation() {
        //given
        Answer answer = getAnswerWithSecondsSinceCreation(60L);
        when(answerRepository.findById(any())).thenReturn(Optional.of(answer));

        //when
        //then
        assertThatCode(() -> answerService.updateAnswer(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionDuringDeletingWhenMoreThanSixtySecondsHavePassedSinceCreation() {
        //given
        Answer answer = getAnswerWithSecondsSinceCreation(75L);
        when(answerRepository.findById(any())).thenReturn(Optional.of(answer));

        //when
        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> answerService.deleteAnswer(answer))
                .withMessage("The Answer is too old to be deleted");
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenLessThanSixtySecondsHavePassedSinceCreation() {
        //given
        Answer answer = getAnswerWithSecondsSinceCreation(55L);
        when(answerRepository.findById(any())).thenReturn(Optional.of(answer));

        //when
        //then
        assertThatCode(() -> answerService.deleteAnswer(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldNotThrowExceptionDuringDeletingWhenSixtySecondsHavePassedSinceCreation() {
        //given
        Answer answer = getAnswerWithSecondsSinceCreation(60L);
        when(answerRepository.findById(any())).thenReturn(Optional.of(answer));

        //when
        //then
        assertThatCode(() -> answerService.deleteAnswer(answer))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldReturnAnswerWhenAnswerIdIsPassed() {
        //given
        when(answerRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Answer()));

        //when
        Answer answer = answerService.getAnswerById(UUID.randomUUID());

        //then
        assertThat(answer).isNotNull();
    }

    @Test
    void shouldReturnExceptionWhenPassedAnswerIdIsNotPresentInDB() {
        //given
        UUID answerId = UUID.randomUUID();
        when(answerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        //when
        Throwable throwable = Assertions.catchThrowable(() -> answerService.getAnswerById(answerId));

        //then
        assertThat(throwable)
                .hasMessage("The Answer object with id " + answerId.toString() + " does not exist in DB");
    }

    @Test
    void shouldReturnAnswerListWhenQuestionIdIsPassed() {
        //given
        Question question = new Question();
        question.setId(UUID.randomUUID());
        when(questionRepository.findById(any(UUID.class))).thenReturn(Optional.of(question));
        when(questionRepository.findAllByCategoryIdOrderByCreationDateDesc(any(UUID.class)))
                .thenReturn(Collections.emptyList());
        //when
        List<Answer> answers = answerService.getAllAnswersByQuestionId(UUID.randomUUID());

        //then
        assertThat(answers).isNotNull();
    }

    @Test
    void shouldReturnExceptionWhenPassedQuestionIdIsNotPresentInDB() {
        //given
        Question question = new Question();
        UUID questionId = UUID.randomUUID();
        question.setId(questionId);
        when(questionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        //when
        Throwable throwable = Assertions.catchThrowable(() ->  answerService.getAllAnswersByQuestionId(questionId));

        //then
        assertThat(throwable)
                .hasMessage("The Question object with id " + questionId + " does not exist in DB");
    }

    private Answer getAnswerWithSecondsSinceCreation(long seconds) {
        Answer answer = new Answer();
        answer.setCreationDate(OffsetDateTime.now().minusSeconds(seconds));
        return answer;
    }
}