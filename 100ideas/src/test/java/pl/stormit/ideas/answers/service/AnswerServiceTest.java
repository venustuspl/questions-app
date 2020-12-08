package pl.stormit.ideas.answers.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.repository.AnswerRepository;
import pl.stormit.ideas.answers.service.validator.AnswerValidator;
import pl.stormit.ideas.questions.repository.QuestionRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    private Answer getAnswerWithSecondsSinceCreation(long seconds) {
        Answer answer = new Answer();
        answer.setCreationDate(OffsetDateTime.now().minusSeconds(seconds));
        return answer;
    }
}